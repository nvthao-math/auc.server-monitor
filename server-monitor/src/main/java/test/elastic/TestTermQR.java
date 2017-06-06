/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.elastic;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

/**
 *
 * @author thaonv
 */
public class TestTermQR {

    static Settings settings = ImmutableSettings.settingsBuilder()
            .put("cluster.name", "devops-production")
            .put("client.transport.sniff", false) // allow client to sniff the rest of the cluster
            .put("client.transport.ignore_cluster_name", false) // ignore the check of cluster name by setting this
            .put("client.transport.ping_timeout", 10000) // in milli-seconds
            .build();
    public static final Client client = new TransportClient(settings)
            .addTransportAddress(new InetSocketTransportAddress("10.199.220.24", 9702));

    public static void main(String[] args) {
        long t1 = System.currentTimeMillis();
        String index = "test_long_terms";
        long records = numberOfRecords(index);
        System.out.println("Time: " + (System.currentTimeMillis() - t1) + " ms");
        System.out.println(records);
//        deleteIndex(index);
    }

//    public static void topSubclass(String index) {
//        Client client = ESClientPool.getClient();
////        FilterBuilder subclassTermFilter = boolFilter()
////                .must(termFilter("subClass", "motorway"));
//        SearchResponse response = client.prepareSearch(index)
//                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
////                .setQuery(subclassTermFilter)
//                .execute()
//                .actionGet();
//    }
    public static long numberOfRecords(String index) {
//        Client client = ESClientPool.getClient();
        SearchResponse response = client.prepareSearch(index)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .execute()
                .actionGet();
        long numberOfrec = response.getHits().totalHits();
        return numberOfrec;
    }

}
