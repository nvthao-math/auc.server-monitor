/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import redis.clients.jedis.JedisPoolConfig;

/**
 *
 * @author thaonv
 */
public class Pool {

    public static void main(String[] args) {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(1);
//        poolConfig.setMaxActive(1);
//+		poolConfig.setTimeBetweenEvictionRunsMillis(-1);

    }

}
