/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.Set;
import org.auc.core.redis.utils.RedisExecutor;
import org.auc.monitor.server.cluster.configs.RedisConfigs;

/**
 *
 * @author thaonguyen
 */
public class TestRedis {

    public static void main(String[] args) {
        (new RedisExecutor<Boolean>(RedisConfigs.INFO) {
            @Override
            public Boolean builds() {
                // set keys
//                for (int i = 0; i < 1000000; i++) {
//                    String key = "sp_" + i;
//                    jedis.set(key, Integer.toString(i));
//                }

                // get keys 
                String cursor = "0";
//                Set<String> keys = jedis.keys("sp*");
//                for (String val : keys) {
//                    System.out.println(val + ", " + jedis.get(val));
//                }
                return true;
            }
        }).run();
    }

}
