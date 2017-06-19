/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.gps.spark.speed;

import com.spark.config.SparkClient;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.spark.api.java.JavaRDD;
import org.auc.core.file.utils.Logger;
import org.auc.core.utils.EUtils;
import org.auc.core.utils.NumberUtils;
import org.auc.gps.config.LogConfig;
import org.auc.gps.speed.model.SpeedModel;
import org.auc.gps.speed.model.SpeedSchema;
import org.auc.gps.storage.elastic.action.IndexingService;

/**
 *
 * @author thaonv
 */
public class SpeedProfileSparkReader extends SparkClient {

    private static final String TAG = SpeedProfileSparkReader.class.getSimpleName();

    public static void main(String[] args) throws IOException {
        long t1;
        t1 = System.currentTimeMillis();
        JavaRDD<SpeedModel> rdd = _jsc.textFile(LogConfig.SPEED_LOG).map(line -> parseLog(line)); // JavaPairRDD<SpeedModel, Long> rdd = 
        rdd.foreachPartition((Iterator<SpeedModel> tuple) -> toElasticSearch(tuple));
        System.out.println("---------------------------------");
        System.out.println(rdd.count());
        System.out.println("Time consuming: " + (System.currentTimeMillis() - t1) + " (ms)");
    }

    public static void toElasticSearch(Iterator<SpeedModel> dataIterator) {
        List<SpeedModel> bag = new ArrayList<>();
        int count = 0;
        while (dataIterator.hasNext()) {
            count++;
            SpeedModel model = dataIterator.next();
            bag.add(model);
            if (count % SpeedSchema.BATCH == 0) {
                System.out.println("size: " + bag.size());
                IndexingService.indexingDataElastic(bag);
                bag.clear();
            }
        }
        if (!bag.isEmpty()) {
            IndexingService.indexingDataElastic(bag);
        }
    }

    public static SpeedModel parseLog(String record) {
        SpeedModel model = new SpeedModel();
        try {
            String[] fields = record.split(EUtils.COMMA);
            double speed = Double.parseDouble(fields[SpeedSchema.SPEED]);
            model.setId(fields[SpeedSchema.USER_ID]);
            model.setArcId(fields[SpeedSchema.ARC_ID]);
            model.setSubClass(fields[SpeedSchema.SUB_CLASS]);
            model.setLength(fields[SpeedSchema.LENGTH]);
            model.setSpeedMax(fields[SpeedSchema.SPEED_MAX]);
            model.setSpeed(speed);
            String time = new StringBuilder().append(fields[SpeedSchema.YEAR]).append("-")
                    .append(NumberUtils.leadingZeroFill(Integer.parseInt(fields[SpeedSchema.MONTH]), 2)).append("-")
                    .append(NumberUtils.leadingZeroFill(Integer.parseInt(fields[SpeedSchema.DAY]), 2)).append("-")
                    .append(NumberUtils.leadingZeroFill(Integer.parseInt(fields[SpeedSchema.HOUR]), 2))
                    .toString();
            model.setTime(time);
            model.setDayOfWeek(fields[SpeedSchema.DAY_OF_WEEK]);
            model.setDayNameOfWeek(fields[SpeedSchema.DAY_NAME_OF_WEEK]);
        } catch (Exception ex) {
            Logger.error(TAG, ex);
        }
        return model;
    }

}
