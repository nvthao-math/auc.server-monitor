/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.gps.job;

import java.lang.reflect.Constructor;
import org.auc.core.file.utils.Logger;
import org.auc.core.utils.EUtils;
import org.auc.gps.speed.JobConfig;

/**
 *
 * @author thaonv
 */
public class JobExecutor {

    private static final String TAG = JobExecutor.class.getSimpleName();

    public static void main(String[] args) {
        long t1 = System.currentTimeMillis();
        String config = "jobName=read-speed-profiles,fromTime=2017-03-23-00,endTime=2017-03-23-23,synchTo=elastic_csv_parquet,realtime=false";
        args = new String[]{"main-class:org.auc.gps.speed.SpeedProfileBatchReader", "parameter:" + config};
        jobStart(args);
        long duration = System.currentTimeMillis() - t1;
        Logger.info(TAG, "Time to run job: " + duration + " (ms), job details: " + EUtils.toJson(args));
    }

    public static void jobStart(String[] args) {
        try {
            String clazzPath = null;
            String parameter = null;
            for (int i = 0; i < args.length; i++) {
                if (args[i].contains("main-class")) {
                    clazzPath = args[i].split(EUtils.COLON)[1];
                } else {
                    parameter = args[i].split(EUtils.COLON)[1];
                    System.out.println(parameter);
                    JobConfig.initialize(parameter);
                }
            }
            System.out.println(clazzPath);
            System.out.println(EUtils.toJson(JobConfig.TASK));
            // start job
            Class jobClazz = Class.forName(clazzPath);
            System.out.println("Class details: " + jobClazz.toString());
            Constructor constructor = jobClazz.getConstructor();
            Executor jobInstance = (Executor) constructor.newInstance();
            jobInstance.start();
        } catch (Exception ex) {
            Logger.error(TAG, ex);
        }
    }

}
