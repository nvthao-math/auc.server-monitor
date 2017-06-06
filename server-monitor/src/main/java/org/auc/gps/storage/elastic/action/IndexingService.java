/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.gps.storage.elastic.action;

import java.util.List;
import org.auc.core.data.structure.ConcurrentSet;
import org.auc.core.file.utils.Logger;
import org.auc.core.utils.CommonUtils;
import org.auc.gps.speed.model.SpeedModel;
import org.auc.gps.storage.elastic.model.IndexType;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;

/**
 *
 * @author cpu10869-local
 */
public class IndexingService {

    private static final String TAG = IndexingService.class.getSimpleName();
    private static final int SHARDS = 8;
    
    private static final int REPLICAS = 2;
    private static ConcurrentSet<String> INDICES = new ConcurrentSet<>();
    private static final int BATCH = 10000;
    private static final int RETRY = 2;
    private static final long RETRY_TIME = 100l; // mili-second

    public static void indexingDataElastic(List<SpeedModel> models) {
        try {
            long t1 = System.currentTimeMillis();
            String index = "gps";// + TimeUtils.toString(new Date(), TimeUtils.yyyy_MM_dd_HH);
            int count = 0;
            Client client = ESClientPool.getClient();
            BulkRequestBuilder bulkRequest = client.prepareBulk();
            if (!INDICES.contains(index)) {
                createNewIndex(client, index);
                INDICES.add(index);
            }
            for (SpeedModel model : models) {
                bulkRequest.add(client.prepareIndex(index, IndexType.SPEED.toString()).setSource(CommonUtils.toJson(model)));
                count++;
                if (count % BATCH == 0 || count == models.size()) {
                    bulkRequest.setRefresh(true);
                    BulkResponse bulkResponse = bulkRequest.execute().actionGet();
                    System.out.println("Number of action: " + bulkRequest.numberOfActions());
                    if (bulkResponse.hasFailures()) {
                        for (int i = 1; i <= RETRY; i++) {
                            Thread.sleep(RETRY_TIME);
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
                        .addMapping(IndexType.SPEED.toString(), ESMapping.getMappingSpeedProfiles()).get();
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

}
