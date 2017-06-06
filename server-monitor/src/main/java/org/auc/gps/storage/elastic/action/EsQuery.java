/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.gps.storage.elastic.action;

import org.auc.core.file.utils.Logger;
import test.elastic.*;
import org.auc.gps.storage.elastic.action.ESClientPool;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import static org.elasticsearch.index.query.FilterBuilders.boolFilter;
import static org.elasticsearch.index.query.FilterBuilders.termFilter;
import org.elasticsearch.index.query.QueryBuilders;

/**
 *
 * @author cpu10869-local
 */
public class EsQuery {

    static Settings settings = ImmutableSettings.settingsBuilder()
            .put("cluster.name", "devops-production")
            .put("client.transport.sniff", false) // allow client to sniff the rest of the cluster
            .put("client.transport.ignore_cluster_name", false) // ignore the check of cluster name by setting this
            .put("client.transport.ping_timeout", 10000) // in milli-seconds
            .build();
    public static final Client client = new TransportClient(settings)
            .addTransportAddress(new InetSocketTransportAddress("10.199.220.133", 9702));

    public static void main(String[] args) {
        long t1 = System.currentTimeMillis();
        String index = "speed_profiles_details_23_10sv";
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

    public static void deleteIndex(String index) {
//        Client client = ESClientPool.getClient();
        DeleteIndexResponse delete = client.admin().indices()
                .delete(new DeleteIndexRequest(index))
                .actionGet();
        if (!delete.isAcknowledged()) {
            Logger.error("Index {} wasn't deleted", index);
        }
    }

}
