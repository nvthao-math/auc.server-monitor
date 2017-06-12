/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.monitor.server.cluster.configs;

/**
 *
 * @author thaonguyen
 */
public enum ConfigInfo {

    CLUSTER("configs/cluster-configs.xml"),
    REDIS("configs/redis-configs.xml"),
    REDIS_POOL("configs/redis-pool-configs.xml");

    public String path;

    ConfigInfo(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return this.path;
    }

}
