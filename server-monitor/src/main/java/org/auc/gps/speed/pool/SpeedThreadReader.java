/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.gps.speed.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import org.auc.core.file.utils.Logger;
import org.auc.core.file.utils.ReaderExecutor;
import org.auc.core.hardware.utils.HardWareMonitor;
import org.auc.core.model.ReaderOffsetModel;
import org.auc.core.utils.NumberUtils;
import org.auc.gps.speed.model.SpeedModel;
import org.auc.gps.storage.elastic.action.IndexingService;
import org.auc.gps.storage.redis.KeyManagement;
import org.auc.monitor.server.cluster.configs.RedisConfigs;

/**
 *
 * @author cpu10869-local
 */
public class SpeedThreadReader implements Runnable {

    private static final String TAG = SpeedThreadReader.class.getSimpleName();
    private String fileName;

    // constructor
    public SpeedThreadReader(String fileName) {
        this.fileName = fileName;
    }

    public void parseSpeedProfileLogs() {
        long t1 = System.currentTimeMillis();
        List<SpeedModel> batchModel = new ArrayList<>();
        // release key in redis
        KeyManagement.keyReleaseOnly(RedisConfigs.INFO, this.fileName);
        // read file
        ReaderOffsetModel offsetModel = ReaderExecutor.readGetQueue(this.fileName, true);
        Logger.info(TAG, "Start parse speed records, time load " + offsetModel.dataSize() + " records: " + (System.currentTimeMillis() - t1) + " (ms)");
        Queue<String> speedProfiles = (Queue<String>) offsetModel.getData();
//        TOTAL_RECORDS += speedProfiles.size();
        int count = 0;
        while (speedProfiles.size() > 0) {
            String speedRecord = speedProfiles.poll();
            try {
                String[] parts = speedRecord.split(",");
                double speed = Double.parseDouble(parts[5]);
//                if (speed > 0) {
                SpeedModel model = new SpeedModel();
                model.setId(parts[0]);
                model.setArcId(parts[1]);
                model.setSubClass(parts[2]);
                model.setLength(parts[3]);
                model.setSpeedMax(parts[4]);
                model.setSpeed(speed);
                String time = new StringBuilder().append(parts[6]).append("-")
                        .append(NumberUtils.leadingZeroFill(Integer.parseInt(parts[7]), 2)).append("-")
                        .append(NumberUtils.leadingZeroFill(Integer.parseInt(parts[8]), 2)).append("-")
                        .append(NumberUtils.leadingZeroFill(Integer.parseInt(parts[9]), 2))
                        .toString();
                model.setTime(time);
                model.setDayOfWeek(parts[10]);
                model.setDayNameOfWeek(parts[11]);
                batchModel.add(model);
                count++;
                if (count % 60000 == 0) {
                    Logger.info(TAG, "Count: " + Integer.toString(count));
                    Logger.info(TAG, HardWareMonitor.getInfo());
                    // synch to elastic
                    IndexingService.indexingDataElastic(batchModel);
                    batchModel.clear();
                    if (!HardWareMonitor.isMemorySafe()) {
                        System.gc();
                    }
                }
//                }
            } catch (Exception ex) {
                Logger.error(TAG, ex);
                Logger.error(TAG, speedRecord);
            }
        }
        // job again for remain data       
        if (!batchModel.isEmpty()) {
            // synch to elastic
            IndexingService.indexingDataElastic(batchModel);
            batchModel.clear();
        }
        Logger.info(TAG, "Time to parse: " + (System.currentTimeMillis() - t1) + " (ms)");
        // check to be continue read speed-profile logs
        System.gc();
        if (offsetModel.isIsContinue()) {
            parseSpeedProfileLogs();
        }
    }

    @Override
    public void run() {
        parseSpeedProfileLogs();
    }

}
