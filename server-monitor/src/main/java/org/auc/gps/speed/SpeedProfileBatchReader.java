/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.gps.speed;

import java.io.File;
import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.auc.core.file.utils.Logger;
import org.auc.core.utils.TimeUtils;
import org.auc.gps.config.LogConfig;
import org.auc.gps.job.Executor;
import org.auc.gps.speed.pool.SpeedThreadReader;

/**
 *
 * @author storm
 */
public class SpeedProfileBatchReader extends Executor {

    private static final String TAG = SpeedProfileBatchReader.class.getSimpleName();

    @Override
    public void start() {
        try {
            long t1 = System.currentTimeMillis();
            //
            Calendar fromDate = Calendar.getInstance();
            Calendar endDate = Calendar.getInstance();
            fromDate.setTime(TimeUtils.toTime(JobConfig.fromTime()));
            endDate.setTime(TimeUtils.toTime(JobConfig.endTime()));
            String time = null;
            while (!fromDate.after(endDate)) {
                time = TimeUtils.toString(fromDate.getTime(), TimeUtils.yyyy_MM_dd_HH);
                System.out.println("Time: " + time);
                parseSpeedLog(time);
                // increase time
                fromDate.add(Calendar.HOUR_OF_DAY, 1);
            }
            long duration = System.currentTimeMillis() - t1;
            Logger.info(TAG, "Time consuming: " + duration + " (ms)");
        } catch (Exception ex) {
            Logger.error(TAG, ex);
        }
    }

    public static void parseSpeedLog(String time) {
        Logger.info(TAG, "begin parse speed log at time: " + time);
        try {
            // get path
            String path = LogConfig.LOG_BASE + TimeUtils.asPath(time);
            ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(8);
            System.out.println(path);
            File folder = new File(path);
            File[] listOfFiles = folder.listFiles();
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    SpeedThreadReader reader = new SpeedThreadReader(file.getAbsolutePath());
                    executor.submit(reader);
                }
            }
            //shut down the executor service now
            executor.shutdown();
            // wait all thread finish
            try {
                executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS); // will block until the last thread finishes executing
            } catch (InterruptedException ex) {
                Logger.error(TAG, ex);
            }
        } catch (Exception ex) {
            Logger.error(TAG, ex);
        }
        Logger.info(TAG, "End parse speed log time: " + time);
    }

}
