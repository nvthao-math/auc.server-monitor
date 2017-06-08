/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.elastic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.auc.core.data.structure.ConcurrentSet;
import org.auc.core.file.utils.Logger;
import org.auc.core.file.utils.WriterExecutor;
import org.auc.core.utils.EUtils;
import org.auc.gps.storage.elastic.action.ESClientPool;
import org.auc.gps.storage.elastic.model.Attributes;
import org.auc.gps.storage.elastic.model.Field;
import org.auc.gps.storage.elastic.model.Index;
import org.auc.gps.storage.elastic.model.IndexType;
import org.auc.gps.storage.elastic.model.Mapping;
import org.auc.gps.storage.elastic.model.Type;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;

/**
 *
 * @author thaonv
 */
public class TestTermSize {

    private static final String TAG = TestTermSize.class.getSimpleName();
    public static final long SIZE = 10; // 32766
    private static final int SHARDS = 1;
    private static final int REPLICAS = 1;
    private static ConcurrentSet<String> INDICES = new ConcurrentSet<>();

    public static void main(String[] args) {
        String str = generateTerms();
        String message = "";
        for (int i = 1; i <= 10; i++) {
            message += str;
        }
        final String out = message;
        message = "?\\D7lLucene46FieldInfos\\00\\00\\00";
        (new WriterExecutor("/home/cpu10869-local/workspace/tmp/message.log", true) {
            @Override
            public void builds() throws Exception {
                this.bufferedWriter.write(out);
            }
        }).run();
        System.out.println(message);
        String str1 = getMapping();
        System.out.println(str1);
        TermsTest ins01 = new TermsTest(SIZE, message);
        //
        List<TermsTest> models = new ArrayList<>();
        models.add(ins01);
        //
        indexingDataElastic(models);
    }

    public static void indexingDataElastic(List<TermsTest> models) {
        try {
            long t1 = System.currentTimeMillis();
            String index = "test_long_terms";// + TimeUtils.toString(new Date(), TimeUtils.yyyy_MM_dd_HH);
            int count = 0;
            Client client = ESClientPool.getClient();
            BulkRequestBuilder bulkRequest = client.prepareBulk();
            if (!INDICES.contains(index)) {
                createNewIndex(client, index);
                INDICES.add(index);
            }
            for (TermsTest model : models) {
                bulkRequest.add(client.prepareIndex(index, "?\\D7lLucene46FieldInfos\\00\\00\\00").setSource(EUtils.toJson(model)));
                count++;
                if (count % 10000 == 0 || count == models.size()) {
                    bulkRequest.setRefresh(true);
                    BulkResponse bulkResponse = bulkRequest.execute().actionGet();
                    System.out.println("Number of action: " + bulkRequest.numberOfActions());
                    if (bulkResponse.hasFailures()) {
                        for (int i = 1; i <= 10; i++) {
                            Thread.sleep(4000l);
                            bulkRequest.setRefresh(true);
                            BulkResponse tryBulk = bulkRequest.execute().actionGet();
                            if (!tryBulk.hasFailures()) {
                                Logger.info(TAG, "Indexing data success: " + count + ", per total: " + models.size() + ", retry time: " + i);
                                break;
                            }
                        }
                    } else {
                        Logger.info(TAG, "Indexing data success: " + count + ", per total: " + models.size());
                    }
                    // clear batch
                    bulkRequest = client.prepareBulk();
                }
            }
            Logger.info(TAG, "Time for indexing " + models.size() + " records: " + (System.currentTimeMillis() - t1) + " (ms)");
        } catch (Exception ex) {
            Logger.error(TAG, ex);
        }
    }

    public static String generateTerms() {
        String sb = "";
        for (int i = 1; i <= SIZE; i++) {
            sb += "A";
        }
        return sb;
    }

    public static synchronized boolean createNewIndex(Client client, String index) {
        boolean status = false;
        boolean exists = client.admin().indices()
                .prepareExists(index)
                .execute().actionGet().isExists();
        if (!exists) {
            try {
                Settings settings = ImmutableSettings.settingsBuilder()
                        .put("index.number_of_shards", SHARDS)
                        .put("index.number_of_replicas", REPLICAS)
                        .build();
                // client perform create new index
                CreateIndexResponse response = client.admin()
                        .indices()
                        .prepareCreate(index)
                        .setSettings(settings)
                        .addMapping("abc.def", getMapping()).get();
                status = response.isAcknowledged();
                Logger.info(TAG, "Create index: " + index + ", status: " + status);
            } catch (Exception ex) {
                Logger.error(TAG, ex);
            }
        } else {
            Logger.info(TAG, "Index: " + index + " has been exist in elasticseach.");
        }
        return status;
    }

    public static String getMapping() {
        Map<String, Object> result = new HashMap<>();
        try {
            Map<String, Object> speedProfileMapping = new HashMap<>();
            speedProfileMapping.putAll(Mapping.Fields.get(Field.ALL, false));
            Map<String, Attributes> propertiesMap = new HashMap<>();
            // add properties
            propertiesMap.put("id", new Attributes(Type.LONG.toString()));
            propertiesMap.put("message", new Attributes(Type.STRING.toString(), Index.NOT_ANALYZED.toString()));
            speedProfileMapping.put("properties", propertiesMap);
            // get final schema
            result.put("abc.def", speedProfileMapping);
        } catch (Exception ex) {
            Logger.error(TAG, ex);
        }
        return EUtils.toJson(result);
    }

}
