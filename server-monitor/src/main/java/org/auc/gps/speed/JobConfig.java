/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.gps.speed;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.auc.core.utils.EUtils;

/**
 *
 * @author thaonv
 */
public class JobConfig {

    public static JobConfig TASK;
    private String jobName;
    private String fromTime;
    private String endTime;
    private List<String> synchTo;
    private boolean realtime;

    // constructor
    public JobConfig() {
        super();
    }

    public static void initialize(String parameter) {
        TASK = new JobConfig();
        Map<String, String> map = EUtils.string2Map(parameter, EUtils.COMMA, String.class, String.class);
        for (String key : map.keySet()) {
            if ("jobName".equals(key)) {
                TASK.setJobName(map.get(key));
            } else if ("fromTime".equals(key)) {
                TASK.setFromTime(map.get(key));
            } else if ("endTime".equals(key)) {
                TASK.setEndTime(map.get(key));
            } else if ("synchTo".equals(key)) {
                TASK.setSynchTo(Arrays.asList(map.get(key).split(EUtils.UNDER_SCORE)));
            } else if ("realtime".equals(key)) {
                TASK.setRealtime(Boolean.parseBoolean(map.get(key)));
            } else {
                // ignore
            }
        }

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
