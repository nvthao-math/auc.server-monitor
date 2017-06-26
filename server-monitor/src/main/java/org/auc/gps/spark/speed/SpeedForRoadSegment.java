/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.gps.spark.speed;

import com.spark.config.SparkClient;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.storage.StorageLevel;
import org.auc.core.file.utils.Logger;
import org.auc.core.ml.regression.LinearRegression;
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
                .filter((Tuple2< String, Iterable<Tuple2<Double, Double>>> tuple) -> isSpeedProfile(tuple));
//        speedArcIdRDD.foreach(tuple -> System.out.println(tuple));
        // average speed day by day (Mon to Sun)
        JavaPairRDD<String, Iterable<Tuple2<Double, Double>>> speedDayRDD = dataRDD
                .flatMapToPair((Tuple2<String, Tuple2<Double, Long>> tuple) -> speedDayParse(tuple))
                .groupByKey()
                .flatMapToPair((Tuple2<String, Iterable<Tuple2<Double, Long>>> tuple) -> avgSpeedDay(tuple))
                .groupByKey()
                .filter((Tuple2< String, Iterable<Tuple2<Double, Double>>> tuple) -> isSpeedProfile(tuple))
                .flatMapToPair((Tuple2<String, Iterable<Tuple2<Double, Double>>> tuple) -> gapsFilling(tuple));
        speedDayRDD.foreach((Tuple2<String, Iterable<Tuple2<Double, Double>>> tuple) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(tuple._1()).append("        ");
//            System.out.println(tuple._1() + "   ");
            Iterator<Tuple2<Double, Double>> iterator = tuple._2().iterator();
            while (iterator.hasNext()) {
                sb.append(iterator.next()).append(", ");
//                System.out.print(iterator.next() + ", ");
            }
            System.out.println(sb.toString());
        });
        // average speed by subclass
        JavaPairRDD speedSubClassRDD = dataRDD
                .flatMapToPair((Tuple2<String, Tuple2<Double, Long>> tuple) -> subClassParse(tuple))
                .groupByKey()
                .flatMapToPair((Tuple2<String, Iterable<Tuple2<Double, Long>>> tuple) -> avgSubClass(tuple))
                .groupByKey()
                .flatMapToPair((Tuple2<String, Iterable<Tuple2<Double, Double>>> tuple) -> gapsFilling(tuple));
        //
        System.out.println("Time consuming: " + (System.currentTimeMillis() - t1) + " (ms)");

    }

    private static List<Tuple2<String, Iterable<Tuple2<Double, Double>>>> gapsFilling(Tuple2<String, Iterable<Tuple2<Double, Double>>> tuple) {
        List<Tuple2<String, Iterable<Tuple2<Double, Double>>>> result = new ArrayList<>();
        try {
            String[] parts = tuple._1().split(EUtils.TAB_DELIMITER, -1);
            String arcId = parts[0];
            Double speedMax = Double.parseDouble(parts[2]);
            Map<Double, Double> speedMap = new TreeMap<>();
            Map<Double, Double> subSpeedMap = new TreeMap<>();
            Iterator<Tuple2<Double, Double>> iterator = tuple._2().iterator();
            while (iterator.hasNext()) {
                Tuple2<Double, Double> val = iterator.next();
                if (SpeedSchema.SPECIAL_TIME.contains(val._1())) {
                    speedMap.put(val._1(), val._2());
                } else {
                    subSpeedMap.put(val._1(), val._2());
                }
            }
            // set speed max
            if (speedMax == 0.0) {
                double spmx = (subSpeedMap.size() > 0) ? Collections.max(subSpeedMap.values()) : SpeedSchema.SPEED_DEFAULT;
                double spmy = (speedMap.size() > 0) ? Collections.max(speedMap.values()) : SpeedSchema.SPEED_DEFAULT;
                speedMax = (spmx >= spmy) ? spmx : spmy;
            }
            // set speedMax to the special time [0h, 6h] and [22h, 23h]
            for (double time : SpeedSchema.SPECIAL_TIME) {
                Double speed = speedMap.get(time);
                if (speed == null) {
                    speedMap.put(time, speedMax);
                }
            }
            //
            if (isInterpolation(subSpeedMap)) {
                // case 01: cubic spline interpolation || two end points (7h, 21h) have speed data 
                // code cubic here

            } else if (isRegression(subSpeedMap)) {
                // case 02: linear regression || partial interval of time continually have speed data
                LinearRegression liRe = new LinearRegression(subSpeedMap);
                liRe._init();
                List<Double> timeMissing = timeMissingSpeed(subSpeedMap);
                Map<Double, Double> mapPred = (Map<Double, Double>) liRe.predict(timeMissing);
                speedMap.putAll(subSpeedMap);
                speedMap.putAll(mapPred);
            } else {
                // case 03: combine linear regression and cubic spline interpolation

            }
            // revert to result format List<Tuple2<String, Iterable<Tuple2<Double, Double>>>>
            Iterable<Tuple2<Double, Double>> regesIterator = EUtils.asIterable(speedMap);
            result.add(new Tuple2(tuple._1(), regesIterator));
        } catch (Exception ex) {
            System.out.println("TUPLE ====== " + tuple);
            Logger.error(TAG, ex);
        }
        return result;
    }

    private static List<Double> timeMissingSpeed(Map<Double, Double> mapSpeed) {
        List<Double> result = new ArrayList<>();
        for (double i = 7; i < 22; i++) {
            Double val = mapSpeed.get(i);
            if (val == null) {
                result.add(i);
            }
        }
        return result;
    }

    private static boolean isInterpolation(Map<Double, Double> subSpeedMap) {
        boolean result = false;
        Double _07hSpeed = subSpeedMap.get(07d);
        Double _21hSpeed = subSpeedMap.get(21d);
        if (_07hSpeed != null && _21hSpeed != null) {
            result = true;
        }
        return result;
    }

    private static boolean isRegression(Map<Double, Double> subSpeedMap) throws Exception {
        boolean result = true;
        try {
            Double _07hSpeed = subSpeedMap.get(07d);
            Double _21hSpeed = subSpeedMap.get(21d);
            if (_07hSpeed == null || _21hSpeed == null) {
                double beginHour = (double) EUtils.firstKey(subSpeedMap);
                double lastHour = (double) EUtils.firstKey(subSpeedMap);
                for (double i = beginHour; i < lastHour; i++) {
                    Double spx = subSpeedMap.get(i);
                    Double spy = subSpeedMap.get(i);
                    if (spy == null || spx == null) {
                        result = false;
                    }
                }
            } else {
                result = false;
            }
        } catch (Exception ex) {
            Logger.error(TAG, ex);
        }
        return result;
    }

    private static boolean isSpeedProfile(Tuple2<String, Iterable<Tuple2<Double, Double>>> tuple) {
        int count = 0;
        try {
            Iterator<Tuple2<Double, Double>> iterator = tuple._2().iterator();
            while (iterator.hasNext()) {
                Tuple2<Double, Double> val = iterator.next();
                double hour = val._1();
                if (SpeedSchema.TIME_ACTIVE_LOWER <= hour && hour <= SpeedSchema.TIME_ACTIVE_UPPER) {
                    count++;
                }
            }
        } catch (Exception ex) {
            Logger.error(TAG, ex);
        }
        return count > 10;
    }

    private static List<Tuple2<String, Tuple2<Double, Double>>> avgSubClass(Tuple2<String, Iterable<Tuple2<Double, Long>>> tuple) {
        List<Tuple2<String, Tuple2<Double, Double>>> result = new ArrayList<>();
        try {
            String[] parts = tuple._1().split(EUtils.TAB_DELIMITER);
            String subClass = parts[0];
            String dateName = parts[1];
            Double hour = Double.parseDouble(parts[2]);
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

    private static List<Tuple2<String, Tuple2<Double, Double>>> avgSpeedDay(Tuple2<String, Iterable<Tuple2<Double, Long>>> tuple) {
        List<Tuple2<String, Tuple2<Double, Double>>> result = new ArrayList<>();
        try {
            String[] parts = tuple._1().split(EUtils.TAB_DELIMITER);
            String arcId = parts[0];
            String dateName = parts[1];
            Double hour = Double.parseDouble(parts[2]);
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
    private static List<Tuple2<String, Tuple2<Double, Double>>> avgSpeed(Tuple2<String, Iterable<Tuple2<Double, Long>>> tuple) {
        List<Tuple2<String, Tuple2<Double, Double>>> result = new ArrayList<>();
        try {
            String[] parts = tuple._1().split(EUtils.TAB_DELIMITER, -1);
            String arcId = parts[0];
            Double hour = Double.parseDouble(parts[1]);
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
