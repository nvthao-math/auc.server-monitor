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
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.collect.ImmutableList;
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
        TransportClient client = new TransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress("localhost", 9300))
                .addTransportAddress(new InetSocketTransportAddress("localhost", 9501))
                .addTransportAddress(new InetSocketTransportAddress("localhost", 9702));
        //
        ImmutableList<DiscoveryNode> nodes = client.connectedNodes();
        for (DiscoveryNode node : nodes) {
            System.out.println(node.name());
            System.out.println(node.getHostName() + ", " + node.getHostAddress());
            System.out.println(node.masterNode());
            System.out.println(node.isMasterNode());
            System.out.println("=================");
        }
        client.close();
    }

}
