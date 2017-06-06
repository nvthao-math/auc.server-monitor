/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.core.redis.utils;

import org.auc.core.model.RedisInfo;
import org.auc.core.file.utils.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

/**
 *
 * @author thaonguyen
 * @param <T>
 */
public abstract class RedisExecutor<T> {

    private static final String TAG = RedisExecutor.class.getSimpleName();
    private RedisInfo redisInfo;
    protected Jedis jedis;

    // constructor
    public RedisExecutor(RedisInfo redisInfo) {
        this.redisInfo = redisInfo;
    }

    /**
     * @return the redisInfo
     */
    public RedisInfo getRedisInfo() {
        return redisInfo;
    }

    /**
     * @param redisInfo the redisInfo to set
     */
    public void setRedisInfo(RedisInfo redisInfo) {
        this.redisInfo = redisInfo;
    }

    public T run() {
        T result = null;
        JedisPool pool = new JedisPool(PoolConfig.getPoolConfig(), redisInfo.getHost(), redisInfo.getPort());
        //get a jedis connection jedis connection pool
        this.jedis = pool.getResource();
        try {
            result = builds();
        } catch (JedisException ex) {
            //if something wrong happen, return it back to the pool
            if (null != jedis) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }
            Logger.error(TAG, ex);
        } finally {
            ///it's important to return the Jedis instance to the pool once you've finished using it
            if (null != jedis) {
                pool.returnResource(jedis);
            }
            pool.destroy();
        }
        return result;
    }

    public abstract T builds();

}
