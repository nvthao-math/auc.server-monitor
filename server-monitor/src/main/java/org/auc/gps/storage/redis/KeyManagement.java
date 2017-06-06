/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.gps.storage.redis;

import java.util.concurrent.ConcurrentHashMap;
import org.auc.core.file.utils.Logger;
import org.auc.core.model.RedisInfo;
import org.auc.core.redis.utils.RedisExecutor;

/**
 *
 * @author inet
 */
public class KeyManagement {

    private static final String TAG = KeyManagement.class.getSimpleName();
    private static ConcurrentHashMap<String, Boolean> FLAGS = new ConcurrentHashMap<>();

    public static void keyRelease(RedisInfo redisInfo, String... prefixAndKey) {
        StringBuilder key = new StringBuilder();
        for (String val : prefixAndKey) {
            key.append(val);
        }
        try {
            (new RedisExecutor<Boolean>(redisInfo) {
                @Override
                public Boolean builds() {
                    jedis.del(key.toString());
                    return true;
                }
            }).run();
        } catch (Exception ex) {
            Logger.error(TAG, ex);
        }
    }

    public static void keyReleaseOnly(RedisInfo redisInfo, String key) {
        Boolean flag = FLAGS.get(key);
        if (null == flag) {
            keyRelease(redisInfo, key);
            FLAGS.put(key, false);
        } else {
            Logger.info(TAG, "Deleted this key:" + key + " already.");
        }
    }

}
