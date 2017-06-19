/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.gps.spark.speed;

import com.spark.config.SparkClient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.storage.StorageLevel;
import org.auc.core.file.utils.Logger;
import org.auc.core.utils.EUtils;
import org.auc.core.utils.NumberUtils;
import org.auc.gps.config.LogConfig;
import org.auc.gps.speed.model.SpeedSchema;
import scala.Tuple2;

/**
 *
 * @author bigdata
 */
public class SpeedForRoadSegment extends SparkClient {

    private static final String TAG = SpeedForRoadSegment.class.getSimpleName();

    public static void main(String[] args) {
        long t1 = System.currentTimeMillis();
        JavaPairRDD<String, Tuple2<Double, Long>> dataRDD = _jsc.textFile(LogConfig.SPEED_LOG)
                .flatMapToPair(line -> parseLog(line))
                .persist(StorageLevel.MEMORY_AND_DISK());
        // average speed by arcID
        JavaPairRDD speedArcIdRDD = dataRDD
                .flatMapToPair((Tuple2<String, Tuple2<Double, Long>> tuple) -> speedArcParse(tuple))
                .groupByKey()
                .flatMapToPair((Tuple2<String, Iterable<Tuple2<Double, Long>>> tuple) -> avgSpeed(tuple))
                .groupByKey()
                .filter((Tuple2< String, Iterable<Tuple2<String, Double>>> tuple) -> isSpeedProfile(tuple));
        speedArcIdRDD.foreach(tuple -> System.out.println(tuple));
        // average speed day by day (Mon to Sun)
        JavaPairRDD speedDayRDD = dataRDD
                .flatMapToPair((Tuple2<String, Tuple2<Double, Long>> tuple) -> speedDayParse(tuple))
                .groupByKey()
                .flatMapToPair((Tuple2<String, Iterable<Tuple2<Double, Long>>> tuple) -> avgSpeedDay(tuple))
                .groupByKey();
        // average speed by subclass
        JavaPairRDD speedSubClassRDD = dataRDD
                .flatMapToPair((Tuple2<String, Tuple2<Double, Long>> tuple) -> subClassParse(tuple))
                .groupByKey()
                .flatMapToPair((Tuple2<String, Iterable<Tuple2<Double, Long>>> tuple) -> avgSubClass(tuple))
                .groupByKey()
                .flatMapToPair((Tuple2<String, Iterable<Tuple2<String, Double>>> tuple) -> gapsFilling(tuple));
        //

//        JavaPairRDD speedMaxRDD = dataRDD.flatMapToPair((Tuple2<String, Tuple2<Double, Long>> tuple) -> speedMaxParse(tuple))
//                .groupByKey();
//        speedMaxRDD.foreach(tuple -> System.out.println(tuple));
        System.out.println("Time consuming: " + (System.currentTimeMillis() - t1) + " (ms)");

    }

    private static List<Tuple2<String, Iterable<Tuple2<String, Double>>>> gapsFilling(Tuple2<String, Iterable<Tuple2<String, Double>>> tuple) {
        List<Tuple2<String, Iterable<Tuple2<String, Double>>>> result = new ArrayList<>();
        try {
            String[] parts = tuple._1().split(EUtils.TAB_DELIMITER);
            String arcId = parts[0];
            double speedMax = Double.parseDouble(parts[1]);
            Map<String, Double> speedMap = new HashMap<>();
            Iterator<Tuple2<String, Double>> iterator = tuple._2().iterator();
            while (iterator.hasNext()) {
                Tuple2<String, Double> val = iterator.next();
                speedMap.put(val._1(), val._2());
            }
            

        } catch (Exception ex) {
            Logger.error(TAG, ex);
        }
        return result;
    }

    private static boolean isSpeedProfile(Tuple2<String, Iterable<Tuple2<String, Double>>> tuple) {
        int count = 0;
        try {
            Iterator<Tuple2<String, Double>> iterator = tuple._2().iterator();
            while (iterator.hasNext()) {
                Tuple2<String, Double> val = iterator.next();
                int hour = Integer.parseInt(val._1());
                if (SpeedSchema.TIME_ACTIVE_LOWER <= hour && hour <= SpeedSchema.TIME_ACTIVE_UPPER) {
                    count++;
                }
            }
        } catch (Exception ex) {
            Logger.error(TAG, ex);
        }
        return count > 10;
    }

    private static List<Tuple2<String, Tuple2<String, Double>>> avgSubClass(Tuple2<String, Iterable<Tuple2<Double, Long>>> tuple) {
        List<Tuple2<String, Tuple2<String, Double>>> result = new ArrayList<>();
        try {
            String[] parts = tuple._1().split(EUtils.TAB_DELIMITER);
            String subClass = parts[0];
            String dateName = parts[1];
            String hour = parts[2];
            String key = new StringBuilder()
                    .append(subClass)
                    .append(EUtils.TAB_DELIMITER)
                    .append(dateName)
                    .toString();
            Iterator<Tuple2<Double, Long>> iterator = tuple._2().iterator();
            double speed = 0.0;
            long freq = 0;
            while (iterator.hasNext()) {
                Tuple2<Double, Long> val = iterator.next();
                speed += val._1();
                freq += val._2();
            }
            double avgSpeed = speed / (double) freq;
            result.add(new Tuple2(key, new Tuple2(hour, avgSpeed)));
        } catch (Exception ex) {
            Logger.error(TAG, ex);
        }
        return result;
    }

    private static List<Tuple2<String, Tuple2<Double, Long>>> subClassParse(Tuple2<String, Tuple2<Double, Long>> tuple) {
        List<Tuple2<String, Tuple2<Double, Long>>> result = new ArrayList<>();
        try {
            String[] parts = tuple._1().split(EUtils.TAB_DELIMITER);
            String subclass = parts[2];
            String dateName = parts[3];
            String hour = parts[4];
            String key = new StringBuilder()
                    .append(subclass)
                    .append(EUtils.TAB_DELIMITER)
                    .append(dateName)
                    .append(EUtils.TAB_DELIMITER)
                    .append(hour)
                    .toString();
            result.add(new Tuple2(key, new Tuple2(tuple._2()._1(), tuple._2()._2())));
        } catch (Exception ex) {
            Logger.error(TAG, ex);
        }
        return result;
    }

    private static List<Tuple2<String, Tuple2<String, Double>>> avgSpeedDay(Tuple2<String, Iterable<Tuple2<Double, Long>>> tuple) {
        List<Tuple2<String, Tuple2<String, Double>>> result = new ArrayList<>();
        try {
            String[] parts = tuple._1().split(EUtils.TAB_DELIMITER);
            String arcId = parts[0];
            String dateName = parts[1];
            String hour = parts[2];
            String speedMax = parts[3];
            String key = new StringBuilder()
                    .append(arcId)
                    .append(EUtils.TAB_DELIMITER)
                    .append(dateName)
                    .append(EUtils.TAB_DELIMITER)
                    .append(speedMax)
                    .toString();
            Iterator<Tuple2<Double, Long>> iterator = tuple._2().iterator();
            double speed = 0.0;
            long freq = 0;
            while (iterator.hasNext()) {
                Tuple2<Double, Long> val = iterator.next();
                speed += val._1();
                freq += val._2();
            }
            double avgSpeed = speed / (double) freq;
            result.add(new Tuple2(key, new Tuple2(hour, avgSpeed)));
        } catch (Exception ex) {
            Logger.error(TAG, ex);
        }
        return result;
    }

    private static List<Tuple2<String, Tuple2<Double, Long>>> speedDayParse(Tuple2<String, Tuple2<Double, Long>> tuple) {
        List<Tuple2<String, Tuple2<Double, Long>>> result = new ArrayList<>();
        try {
            String[] parts = tuple._1().split(EUtils.TAB_DELIMITER);
            String arcId = parts[1];
            String dateName = parts[3];
            String hour = parts[4];
            String speedMax = parts[5];
            String key = new StringBuilder()
                    .append(arcId)
                    .append(EUtils.TAB_DELIMITER)
                    .append(dateName)
                    .append(EUtils.TAB_DELIMITER)
                    .append(hour)
                    .append(EUtils.TAB_DELIMITER)
                    .append(speedMax)
                    .toString();
            result.add(new Tuple2(key, new Tuple2(tuple._2()._1(), tuple._2()._2())));
        } catch (Exception ex) {
            Logger.error(TAG, ex);
        }
        return result;
    }

//    private static List<Tuple2<String, Double>> speedMaxParse(Tuple2<String, Tuple2<Double, Long>> tuple) {
//        List<Tuple2<String, Double>> result = new ArrayList<>();
//        try {
//            String[] parts = tuple._1().split(EUtils.TAB_DELIMITER);
//            String arcId = parts[1];
//            String speedMax = parts[5];
//            result.add(new Tuple2(arcId, speedMax));
//        } catch (Exception ex) {
//            Logger.error(TAG, ex);
//        }
//        return result;
//    }
    private static List<Tuple2<String, Tuple2<String, Double>>> avgSpeed(Tuple2<String, Iterable<Tuple2<Double, Long>>> tuple) {
        List<Tuple2<String, Tuple2<String, Double>>> result = new ArrayList<>();
        try {
            String[] parts = tuple._1().split(EUtils.TAB_DELIMITER, -1);
            String arcId = parts[0];
            String hour = parts[1];
            String speedMax = parts[2];
            Iterator<Tuple2<Double, Long>> iterator = tuple._2().iterator();
            double speed = 0.0;
            long freq = 0l;
            while (iterator.hasNext()) {
                Tuple2<Double, Long> val = iterator.next();
                speed += val._1();
                freq += val._2();
            }
            double avgSpeed = speed / freq;
            String key = new StringBuilder()
                    .append(arcId)
                    .append(EUtils.TAB_DELIMITER)
                    .append(speedMax)
                    .toString();
            result.add(new Tuple2(key, new Tuple2(hour, avgSpeed)));
        } catch (Exception ex) {
            Logger.error(TAG, ex);
        }
        return result;
    }

    private static List<Tuple2<String, Tuple2<Double, Long>>> speedArcParse(Tuple2<String, Tuple2<Double, Long>> tuple) {
        List<Tuple2<String, Tuple2<Double, Long>>> result = new ArrayList<>();
        try {
            String[] parts = tuple._1().split(EUtils.TAB_DELIMITER, -1);
            String arcId = parts[1];
            String hourOfDay = parts[4];
            String speedMax = parts[5];
            String key = new StringBuilder()
                    .append(arcId)
                    .append(EUtils.TAB_DELIMITER)
                    .append(hourOfDay)
                    .append(EUtils.TAB_DELIMITER)
                    .append(speedMax)
                    .toString();
            result.add(new Tuple2(key, new Tuple2(tuple._2()._1(), tuple._2()._2())));
        } catch (Exception ex) {
            Logger.error(TAG, ex);
        }
        return result;
    }

    private static List<Tuple2<String, Tuple2<Double, Long>>> parseLog(String line) {
        List<Tuple2<String, Tuple2<Double, Long>>> result = new ArrayList<>();
        try {
            String[] fields = line.split(EUtils.COMMA);
            double speed = Double.parseDouble(fields[SpeedSchema.SPEED]);
            if (speed >= SpeedSchema.SPEED_LOWER && speed <= SpeedSchema.SPEED_UPPER) {
                String userId = fields[SpeedSchema.USER_ID];
                String arcId = fields[SpeedSchema.ARC_ID];
                String subClass = fields[SpeedSchema.SUB_CLASS];
                String dateName = fields[SpeedSchema.DAY_NAME_OF_WEEK];
                String hourOfDay = NumberUtils.leadingZeroFill(Integer.parseInt(fields[SpeedSchema.HOUR]), 2);
                double speedMax = Double.parseDouble(fields[SpeedSchema.SPEED_MAX]);
                String key = new StringBuilder()
                        .append(userId)
                        .append(EUtils.TAB_DELIMITER)
                        .append(arcId)
                        .append(EUtils.TAB_DELIMITER)
                        .append(subClass)
                        .append(EUtils.TAB_DELIMITER)
                        .append(dateName)
                        .append(EUtils.TAB_DELIMITER)
                        .append(hourOfDay)
                        .append(EUtils.TAB_DELIMITER)
                        .append(speedMax)
                        .toString();
                result.add(new Tuple2(key, new Tuple2(speed, 1l)));
            }
        } catch (Exception ex) {
            Logger.error(TAG, ex);
        }
        return result;
    }

}
