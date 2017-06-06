/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.elastic;

import org.auc.gps.storage.elastic.action.ESClientPool;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;

/**
 *
 * @author cpu10869-local
 */
public class EsQuery {

    public static void main(String[] args) {
        long t1 = System.currentTimeMillis();
        String index = "speed_profiles_details";
        long records = numberOfRecords(index);
        System.out.println("Time: " + (System.currentTimeMillis() - t1) + " ms");
        System.out.println(records);
    }

    public static long numberOfRecords(String index) {
        Client client = ESClientPool.getClient();
        SearchResponse response = client.prepareSearch(index)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .execute()
                .actionGet();
        long numberOfrec = response.getHits().totalHits();
        return numberOfrec;
    }

}
