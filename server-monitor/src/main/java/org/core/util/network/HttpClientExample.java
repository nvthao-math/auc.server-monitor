/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.core.util.network;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPOutputStream;
import org.apache.http.HttpHost;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

/**
 *
 * @author thaonv
 */
public class HttpClientExample {

    // Response Code : 400 means that the request was malformed, the data stream sent by the client to the server didn't follow the rules.
    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String url = "https://hooks.slack.com/services/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";

    public static void main(String[] args) throws Exception {
        sendPost();
    }

    // HTTP POST request
    private static void sendPost() throws Exception {
        //
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        String json = "{\"text\":\"response code here ... \"}";
        StringEntity entity = new StringEntity(json);
//        httpPost.setEntity(entity);
        httpPost.setEntity(entity);
        // add header
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setHeader("User-Agent", USER_AGENT);
        httpPost.setHeader("Content-Encoding", "gzip");
        HttpResponse response = client.execute(httpPost);
        //
        System.out.println(String.format("\nSending 'POST' request to URL : %s", url));
        System.out.println(String.format("Post parameters : %s", httpPost.getEntity()));
        System.out.println(String.format("Response Code : %s", response.getStatusLine().getStatusCode()));
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        System.out.println("--------------------------------");
        StringBuilder result = new StringBuilder();
        String line = null;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        System.out.println(result.toString());
    }

}
