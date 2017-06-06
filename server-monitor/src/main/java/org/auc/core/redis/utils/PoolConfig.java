/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.core.redis.utils;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.auc.core.file.utils.Logger;
import org.auc.monitor.cluster.configs.ConfigInfo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import redis.clients.jedis.JedisPoolConfig;

/**
 *
 * @author inet
 */
public class PoolConfig {

    private static final String TAG = PoolConfig.class.getSimpleName();
    private static PoolConfig INSTANCE = null;
    private static JedisPoolConfig POOL_CONFIG = null;
    private int maxTotal; // Maximum active connections to Redis instance
    private int maxIdle; // Required || Number of connections to Redis that just sit there and do nothing, setting MaxIdle == MaxTotal, there will be no eviction of resources from your pool 
    private int minIdle; // Minimum number of idle connections to Redis
    private boolean testOnBorrow; // true - Sends a PING request when you ask for the resource
    private boolean testOnReturn; // true - Sends a PING when you return a resource to the pool
    private boolean testWhileIdle; // true - Sends periodic PINGS from idle resources in the pool
    private long minEvictableIdleTimeMillis; // minimum connection checking time
    private long timeBetweenEvictionRunsMillis; // Idle connection checking period
    private int numTestsPerEvictionRun; // Maximum number of connections to test in each idle check
    private boolean blockWhenExhausted; // If pool is exhausted, then it is quite likely instances are not returned back.
    private long maxWaitMillis; // Required || to wait for a pooled component to become available when the pool is exhausted, configuring it for some good max value so that timeout don't occur

    // constructor
    public PoolConfig() {
        super();
    }

    public static JedisPoolConfig getPoolConfig() {
        if (POOL_CONFIG == null) { // GenericObjectPool
            POOL_CONFIG = new JedisPoolConfig();
            PoolConfig config = PoolConfig.getPoolConfigInfo();
            POOL_CONFIG.setMaxTotal(config.getMaxTotal());
            POOL_CONFIG.setMaxIdle(config.getMaxIdle());
            POOL_CONFIG.setMinIdle(config.getMinIdle());
            POOL_CONFIG.setTestOnBorrow(config.isTestOnBorrow());
            POOL_CONFIG.setTestOnReturn(config.isTestOnReturn());
            POOL_CONFIG.setTestWhileIdle(config.isTestWhileIdle());
            POOL_CONFIG.setMinEvictableIdleTimeMillis(config.getMinEvictableIdleTimeMillis());
            POOL_CONFIG.setTimeBetweenEvictionRunsMillis(config.getTimeBetweenEvictionRunsMillis());
            POOL_CONFIG.setNumTestsPerEvictionRun(config.getNumTestsPerEvictionRun());
            POOL_CONFIG.setBlockWhenExhausted(config.isBlockWhenExhausted());
            POOL_CONFIG.setMaxWaitMillis(config.getMaxWaitMillis());
            return POOL_CONFIG;
        }
        return POOL_CONFIG;
    }

    public static PoolConfig getPoolConfigInfo() {
        if (INSTANCE == null) {
            try {
                INSTANCE = new PoolConfig();
                File clusterXml = new File(ConfigInfo.REDIS_POOL.toString());
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = (Document) dBuilder.parse(clusterXml);
                doc.getDocumentElement().normalize();
                NodeList nList = doc.getElementsByTagName("pool-config");
                // parameter config
                int maxTotal = 0;
                int maxIdle = 0;
                int minIdle = 0;
                boolean testOnBorrow = true;
                boolean testOnReturn = true;
                boolean testWhileIdle = true;
                long minEvictableIdleTimeMillis = 0l;
                long timeBetweenEvictionRunsMillis = 0l;
                int numTestsPerEvictionRun = 0;
                boolean blockWhenExhausted = true;
                long setMaxWaitMillis = 0l;
                for (int temp = 0; temp < nList.getLength(); temp++) {
                    Node nNode = nList.item(temp);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        maxTotal = Integer.parseInt(eElement.getElementsByTagName("maxTotal").item(0).getTextContent());
                        maxIdle = Integer.parseInt(eElement.getElementsByTagName("maxIdle").item(0).getTextContent());
                        minIdle = Integer.parseInt(eElement.getElementsByTagName("minIdle").item(0).getTextContent());
                        testOnBorrow = Boolean.parseBoolean(eElement.getElementsByTagName("testOnBorrow").item(0).getTextContent());
                        testOnReturn = Boolean.parseBoolean(eElement.getElementsByTagName("testOnReturn").item(0).getTextContent());
                        testWhileIdle = Boolean.parseBoolean(eElement.getElementsByTagName("testWhileIdle").item(0).getTextContent());
                        minEvictableIdleTimeMillis = Long.parseLong(eElement.getElementsByTagName("minEvictableIdleTimeMillis").item(0).getTextContent());
                        timeBetweenEvictionRunsMillis = Long.parseLong(eElement.getElementsByTagName("timeBetweenEvictionRunsMillis").item(0).getTextContent());
                        numTestsPerEvictionRun = Integer.parseInt(eElement.getElementsByTagName("numTestsPerEvictionRun").item(0).getTextContent());
                        blockWhenExhausted = Boolean.parseBoolean(eElement.getElementsByTagName("blockWhenExhausted").item(0).getTextContent());
                        setMaxWaitMillis = Long.parseLong(eElement.getElementsByTagName("maxWaitMillis").item(0).getTextContent());
                    }
                }
                INSTANCE.setMaxTotal(maxTotal);
                INSTANCE.setMaxIdle(maxIdle);
                INSTANCE.setMinIdle(minIdle);
                INSTANCE.setTestOnBorrow(testOnBorrow);
                INSTANCE.setTestOnReturn(testOnReturn);
                INSTANCE.setTestWhileIdle(testWhileIdle);
                INSTANCE.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
                INSTANCE.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
                INSTANCE.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
                INSTANCE.setBlockWhenExhausted(blockWhenExhausted);
                INSTANCE.setMaxWaitMillis(setMaxWaitMillis);
                return INSTANCE;
            } catch (Exception ex) {
                Logger.error(TAG, ex);
            }
        }
        return INSTANCE;
    }

    /**
     * @return the maxTotal
     */
    public int getMaxTotal() {
        return maxTotal;
    }

    /**
     * @param maxTotal the maxTotal to set
     */
    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    /**
     * @return the maxIdle
     */
    public int getMaxIdle() {
        return maxIdle;
    }

    /**
     * @param maxIdle the maxIdle to set
     */
    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    /**
     * @return the minIdle
     */
    public int getMinIdle() {
        return minIdle;
    }

    /**
     * @param minIdle the minIdle to set
     */
    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    /**
     * @return the testOnBorrow
     */
    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    /**
     * @param testOnBorrow the testOnBorrow to set
     */
    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    /**
     * @return the testOnReturn
     */
    public boolean isTestOnReturn() {
        return testOnReturn;
    }

    /**
     * @param testOnReturn the testOnReturn to set
     */
    public void setTestOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    /**
     * @return the testWhileIdle
     */
    public boolean isTestWhileIdle() {
        return testWhileIdle;
    }

    /**
     * @param testWhileIdle the testWhileIdle to set
     */
    public void setTestWhileIdle(boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }

    /**
     * @return the minEvictableIdleTimeMillis
     */
    public long getMinEvictableIdleTimeMillis() {
        return minEvictableIdleTimeMillis;
    }

    /**
     * @param minEvictableIdleTimeMillis the minEvictableIdleTimeMillis to set
     */
    public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }

    /**
     * @return the timeBetweenEvictionRunsMillis
     */
    public long getTimeBetweenEvictionRunsMillis() {
        return timeBetweenEvictionRunsMillis;
    }

    /**
     * @param timeBetweenEvictionRunsMillis the timeBetweenEvictionRunsMillis to
     * set
     */
    public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }

    /**
     * @return the numTestsPerEvictionRun
     */
    public int getNumTestsPerEvictionRun() {
        return numTestsPerEvictionRun;
    }

    /**
     * @param numTestsPerEvictionRun the numTestsPerEvictionRun to set
     */
    public void setNumTestsPerEvictionRun(int numTestsPerEvictionRun) {
        this.numTestsPerEvictionRun = numTestsPerEvictionRun;
    }

    /**
     * @return the blockWhenExhausted
     */
    public boolean isBlockWhenExhausted() {
        return blockWhenExhausted;
    }

    /**
     * @param blockWhenExhausted the blockWhenExhausted to set
     */
    public void setBlockWhenExhausted(boolean blockWhenExhausted) {
        this.blockWhenExhausted = blockWhenExhausted;
    }

    /**
     * @return the maxWaitMillis
     */
    public long getMaxWaitMillis() {
        return maxWaitMillis;
    }

    /**
     * @param maxWaitMillis the maxWaitMillis to set
     */
    public void setMaxWaitMillis(long maxWaitMillis) {
        this.maxWaitMillis = maxWaitMillis;
    }

}
