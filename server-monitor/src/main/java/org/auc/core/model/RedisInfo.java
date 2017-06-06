/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.core.model;

/**
 *
 * @author thaonguyen
 */
public class RedisInfo {

    private String host;
    private int port;

    // constructor
    public RedisInfo() {
        super();
    }

    public RedisInfo(String host, String port) {
        this.host = host;
        this.port = Integer.parseInt(port);
    }

    public RedisInfo(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * @param host the host to set
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(String port) {
        this.port = Integer.parseInt(port);
    }

}
