/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.elastic;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.auc.core.utils.EUtils;
import org.auc.core.utils.TimeUtils;
import org.auc.gps.storage.elastic.action.ESMapping;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.metadata.MetaDataDeleteIndexService.Response;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

/**
 *
 * @author cpu10869-local
 */
public class TestTransportClient {

    public static void main(String[] args) {
        Settings settings = ImmutableSettings.settingsBuilder()
                .put("cluster.name", "devops-production")
                .put("client.transport.sniff", true) // allow client to sniff the rest of the cluster
                .put("client.transport.ignore_cluster_name", false) // ignore the check of cluster name by setting this
                .put("client.transport.ping_timeout", 10000) // in milli-seconds
                .build();
        // on startup
        Client client = new TransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress("10.199.220.22", 9300))
                .addTransportAddress(new InetSocketTransportAddress("10.199.220.22", 9501))
                .addTransportAddress(new InetSocketTransportAddress("10.199.220.22", 9702));
        //
        String index = "speed_profiles" + TimeUtils.toString(new Date(), TimeUtils.yyyy_MM_dd_HH);
        createNewIndex(client, index);
        // index data
//        Map<String, Object> json = new HashMap<String, Object>();
//        json.put("user", "kimchy");
//        json.put("postDate", new Date());
//        json.put("message", "trying out Elasticsearch");
//        String data = CommonUtils.toJson(json);
//        IndexResponse response = client.prepareIndex("twitter", "tweet", "20")
//                .setSource(data)
//                .execute()
//                .actionGet();
        // on shutdown
        client.close();
    }

    public static String createNewIndex(Client client, String index) {
        Settings settings = ImmutableSettings.settingsBuilder()
                .put("index.number_of_shards", 10)
                .put("index.number_of_replicas", 2)
                .build();
        // client perform create new index
        CreateIndexResponse response = client.admin()
                .indices()
                .prepareCreate(index)
                .setSettings(settings)
                .addMapping("speed_profile", ESMapping.getMappingSpeedProfiles()).get();
        response.isAcknowledged();
        return null;
    }

}
