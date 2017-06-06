/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.monitor.dao;

/**
 *
 * @author thaonv
 */
public class ProcessDao {

    private String name;
    private long maxTime;
    private long meanTime;
    private long minTime;

    // constructor
    public ProcessDao() {
        super();
    }

    public ProcessDao(String name, long meanTime) {
        this.name = name;
        this.meanTime = meanTime;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the maxTime
     */
    public long getMaxTime() {
        return maxTime;
    }

    /**
     * @param maxTime the maxTime to set
     */
    public void setMaxTime(long maxTime) {
        this.maxTime = maxTime;
    }

    public void decreaseMaxTime(long time) {
        this.maxTime -= time;
    }

    /**
     * @return the meanTime
     */
    public long getMeanTime() {
        return meanTime;
    }

    /**
     * @param meanTime the meanTime to set
     */
    public void setMeanTime(long meanTime) {
        this.meanTime = meanTime;
    }

    public void decreaseMeanTime(long time) {
        this.meanTime -= time;
    }

    /**
     * @return the minTime
     */
    public long getMinTime() {
        return minTime;
    }

    /**
     * @param minTime the minTime to set
     */
    public void setMinTime(long minTime) {
        this.minTime = minTime;
    }

    public void decreaseMinTime(long time) {
        this.minTime -= time;
    }

}
