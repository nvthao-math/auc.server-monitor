/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import org.auc.core.redis.utils.RedisExecutor;
import org.auc.monitor.server.cluster.configs.RedisConfigs;

/**
 *
 * @author inet
 */
public class RedisThread implements Runnable {

    private int loop;

    public RedisThread(int loop) {
        this.loop = loop;
    }

    @Override
    public void run() {
        for (int i = 1; i < this.loop; i++) {
            String key = "test_imp" + i;
            String value = "value_" + i;
            (new RedisExecutor<Boolean>(RedisConfigs.INFO) {
                @Override
                public Boolean builds() {
                    jedis.set(key, value);
                    System.out.println(Thread.currentThread().getName() + ": " + key + "," + value);
                    return true;
                }
            }).run();
        }
    }

}
