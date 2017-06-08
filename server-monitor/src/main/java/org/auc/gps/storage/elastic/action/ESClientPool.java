/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.gps.storage.elastic.action;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import org.auc.core.file.utils.Logger;
import org.auc.core.utils.EUtils;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.cluster.node.info.NodeInfo;
import org.elasticsearch.action.admin.cluster.node.info.NodesInfoRequest;
import org.elasticsearch.action.admin.cluster.node.info.NodesInfoResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.discovery.MasterNotDiscoveredException;
import org.elasticsearch.http.HttpInfo;

/**
 *
 * @author cpu10869-local
 */
public class ESClientPool {

    private static String TAG = ESClientPool.class.getSimpleName();
    private static Map<Integer, Client> POOL = new HashMap<>();
    private static long SLOT_COUNT = 0l;

    static {
        initializeClient();
    }

    public static Client getClient() {
        SLOT_COUNT++;
        int slot = (int) (SLOT_COUNT % 3);
        System.out.println(slot);
        return POOL.get(slot);
    }

    public static Map<Integer, Client> initializeClient() {
        try {
            Settings settings = ImmutableSettings.settingsBuilder()
                    .put("cluster.name", "devops-production")
                    .put("client.transport.sniff", false) // allow client to sniff the rest of the cluster
                    .put("client.transport.ignore_cluster_name", false) // ignore the check of cluster name by setting this
                    .put("client.transport.ping_timeout", 10000) // in milli-seconds
                    .build();
            // on startup
            Client client01 = new TransportClient(settings)
                    .addTransportAddress(new InetSocketTransportAddress("localhost", 9300));
            POOL.put(0, client01);
            Client client02 = new TransportClient(settings)
                    .addTransportAddress(new InetSocketTransportAddress("localhost", 9501));
            POOL.put(1, client02);
            Client client03 = new TransportClient(settings)
                    .addTransportAddress(new InetSocketTransportAddress("localhost", 9702));
            POOL.put(2, client03);
        } catch (Exception ex) {
            Logger.error(TAG, ex);
        }
        return POOL;
    }

    public static void main(String[] args) {
        Settings settings = ImmutableSettings.settingsBuilder()
                .put("cluster.name", "devops-production")
                .put("client.transport.sniff", false) // allow client to sniff the rest of the cluster
                .put("client.transport.ignore_cluster_name", false) // ignore the check of cluster name by setting this
                .put("client.transport.ping_timeout", 10000) // in milli-seconds
                .build();
        Client client03 = new TransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress("10.199.220.133", 9702));
        //
        ClusterHealthResponse hr = null;
        try {
            hr = client03.admin().cluster().
                    prepareHealth().
                    setWaitForGreenStatus().
                    setTimeout(TimeValue.timeValueMillis(250)).
                    execute().
                    actionGet();
        } catch (MasterNotDiscoveredException e) {
            // No cluster status since we don't have a cluster
        }
        //
        if (hr != null) {
            System.out.println("Data nodes found:"
                    + hr.getNumberOfDataNodes());
            System.out.println("Timeout? :"
                    + hr.isTimedOut());
            System.out.println("Status:"
                    + hr.getStatus().name());
        }
        //
        NodesInfoResponse nir = client03.admin().cluster().nodesInfo(new NodesInfoRequest().http(true)).actionGet();
        NodeInfo[] nis = nir.getNodes();
//        System.out.println(CommonUtils.toJson(nis));
//        if (nis == null || nis.length == 0) {
////            throw new ServletException("Elasticsearch node is not available");
//        }
        NodeInfo ni = nir.getAt(0);
        HttpInfo hi = ni.getHttp();
        if (hi == null) {
//            throw new ServletException("HTTP Connector is not available for Elasticsearch node " + ni.getNode().getName());
        }

        TransportAddress ta = hi.getAddress().publishAddress();
        if (ta == null || !(ta instanceof InetSocketTransportAddress)) {
//            throw new ServletException("HTTP Connector is not available for Elasticsearch node " + ni.getNode().getName());
        }

        InetSocketTransportAddress a = (InetSocketTransportAddress) ta;

        String url = "http://" + a.address().getHostString() + ":" + a.address().getPort();
    }

}
