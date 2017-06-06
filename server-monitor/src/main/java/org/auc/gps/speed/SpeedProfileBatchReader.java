/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.gps.speed;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import org.auc.gps.config.LogConfig;
import org.auc.gps.speed.model.SpeedModel;
import org.auc.core.file.utils.Logger;
import org.auc.core.file.utils.ReaderExecutor;
import org.auc.core.hardware.utils.HardWareMonitor;
import org.auc.core.model.ReaderOffsetModel;
import org.auc.core.utils.NumberUtils;
import org.auc.gps.speed.model.SpeedSchema;
import org.auc.gps.storage.elastic.action.IndexingService;
import org.auc.gps.storage.redis.KeyManagement;
import org.auc.monitor.cluster.configs.RedisConfigs;

/**
 *
 * @author thaonguyen
 */
public class SpeedProfileBatchReader {

    private static final String TAG = SpeedProfileBatchReader.class.getSimpleName();
    private static int TOTAL_RECORDS = 0;
    private static boolean KEY_FLAG = true;

    public static void main(String[] args) throws IOException {
        long t1;
        t1 = System.currentTimeMillis();
        List<SpeedModel> speedProfiles = parseSpeedProfileLogs();
        System.out.println("result: " + speedProfiles.size());
        Logger.info(TAG, "Time: " + (System.currentTimeMillis() - t1) + " (ms)");
        System.out.println("Total records: " + TOTAL_RECORDS);
        System.out.println("end.");
    }

    public static List<SpeedModel> parseSpeedProfileLogs() {
        long t1 = System.currentTimeMillis();
        List<SpeedModel> result = new ArrayList<>();
        // release key in redis
        if (KEY_FLAG) {
            KeyManagement.keyRelease(RedisConfigs.INFO, LogConfig.SPEED_LOG);
            KEY_FLAG = false;
        }
        ReaderOffsetModel offsetModel = ReaderExecutor.readGetQueue(LogConfig.SPEED_LOG, false);
        Logger.info(TAG, "Start parse speed records, time load " + offsetModel.dataSize() + " records: " + (System.currentTimeMillis() - t1) + " (ms)");
        Queue<String> speedProfiles = (Queue<String>) offsetModel.getData();
        TOTAL_RECORDS += speedProfiles.size();
        int count = 0;
        while (speedProfiles.size() > 0) {
            String speedRecord = speedProfiles.poll();
            try {
                String[] parts = speedRecord.split(",");
                double speed = Double.parseDouble(parts[SpeedSchema.SPEED]);
//                if (speed > 0) {
                SpeedModel model = new SpeedModel();
                model.setId(parts[SpeedSchema.ID]);
                model.setArcId(parts[SpeedSchema.ARC_ID]);
                model.setSubClass(parts[SpeedSchema.SUB_CLASS]);
                model.setLength(parts[SpeedSchema.LENGTH]);
                model.setSpeedMax(parts[SpeedSchema.SPEED_MAX]);
                model.setSpeed(speed);
                String time = new StringBuilder().append(parts[SpeedSchema.YEAR]).append("-")
                        .append(NumberUtils.leadingZeroFill(Integer.parseInt(parts[SpeedSchema.MONTH]), 2)).append("-")
                        .append(NumberUtils.leadingZeroFill(Integer.parseInt(parts[SpeedSchema.DAY]), 2)).append("-")
                        .append(NumberUtils.leadingZeroFill(Integer.parseInt(parts[SpeedSchema.HOUR]), 2))
                        .toString();
                model.setTime(time);
                model.setDayOfWeek(parts[SpeedSchema.DAY_OF_WEEK]);
                model.setDayNameOfWeek(parts[SpeedSchema.DAY_NAME_OF_WEEK]);
                result.add(model);
                count++;
                if (count % SpeedSchema.BATCH == 0) {
                    Logger.info(TAG, "Count: " + Integer.toString(count));
                    Logger.info(TAG, HardWareMonitor.getInfo());
                    // job here
//                        UniqueUser.addList(result);
                    // synch to elastic
                    IndexingService.indexingDataElastic(result);
                    result.clear();
                    if (!HardWareMonitor.isMemorySafe()) {
                        System.gc();
                    }
                }
            } catch (Exception ex) {
                Logger.error(TAG, ex);
                Logger.error(TAG, speedRecord);
            }
        }
        // job again for remain data       
        if (!result.isEmpty()) {
            // synch to elastic
            IndexingService.indexingDataElastic(result);
            result.clear();
        }
        Logger.info(TAG, "Time to parse: " + (System.currentTimeMillis() - t1) + " (ms)");
        // check to be continue read speed-profile logs
        if (offsetModel.isIsContinue()) {
            parseSpeedProfileLogs();
        }
        return result;
    }

}
