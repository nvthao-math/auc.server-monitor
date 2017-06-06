/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.gps.speed;

import java.util.List;

/**
 *
 * @author thaonv
 */
public class TaskConfig {

    public static TaskConfig TASK;
    private String jobName;
    private String fromTime;
    private String endTime;
    private List<String> synchTo;
    private boolean realtime;

    // constructor
    public TaskConfig() {
        super();
    }

    public static void initialize(TaskConfig task) {
        TASK = task;
    }

    public static String jobName() {
        return TASK.getJobName();
    }

    public static String fromTime() {
        return TASK.getFromTime();
    }

    public static String endTime() {
        return TASK.getEndTime();
    }

    public static List<String> synchTo() {
        return TASK.getSynchTo();
    }

    public static boolean realtime() {
        return TASK.isRealtime();
    }

    /**
     * @return the jobName
     */
    public String getJobName() {
        return jobName;
    }

    /**
     * @param jobName the jobName to set
     */
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    /**
     * @return the fromTime
     */
    public String getFromTime() {
        return fromTime;
    }

    /**
     * @param fromTime the fromTime to set
     */
    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    /**
     * @return the endTime
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * @return the synchTo
     */
    public List<String> getSynchTo() {
        return synchTo;
    }

    /**
     * @param synchTo the synchTo to set
     */
    public void setSynchTo(List<String> synchTo) {
        this.synchTo = synchTo;
    }

    /**
     * @return the realtime
     */
    public boolean isRealtime() {
        return realtime;
    }

    /**
     * @param realtime the realtime to set
     */
    public void setRealtime(boolean realtime) {
        this.realtime = realtime;
    }

}
