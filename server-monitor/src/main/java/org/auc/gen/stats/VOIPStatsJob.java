///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.vng.zalostats.spark.job.zalo;
//
//import com.google.common.reflect.TypeToken;
//import com.vng.statscenter.app.conf.BaseConfiguration;
//import com.vng.statscenter.service.ZaloIp2GeoClient;
//import com.vng.statscenter.tsdmw.thrift.TResult;
//import com.vng.statscenter.tsdmw.thrift.TTimeSerie;
//import com.vng.zalostats.app.NewTimeOnAppApp;
//import com.vng.zalostats.app.ZaloStatsApp;
//import com.vng.zalostats.common.Utils;
//import com.vng.zalostats.model.entity.Argument;
//import com.vng.zalostats.service.EmailNotifyService;
//import com.vng.zalostats.service.TTimeSerrieMWClient;
//import com.vng.zalostats.service.ZaloNotifyService;
//import com.vng.zalostats.spark.ClusterSettings;
//import com.vng.zalostats.spark.HadoopSourceBuilder;
//import com.vng.zalostats.spark.JobSettings;
//import com.vng.zalostats.spark.job.NewTimeOnAppJob;
//import com.vng.zalostats.spark.result.CallType;
//import com.vng.zalostats.spark.result.NodeConnection;
//import com.vng.zalostats.spark.result.TecoNetModel;
//import com.vng.zalostats.spark.result.VoipStats;
//import com.vng.zalostats.spark.schema.VoipSchema;
//import com.vng.zalostats.spark.statitics.StatEntry;
//import com.vng.zalostats.spark.statitics.StatEntryAccumulatorParam;
//import java.io.PrintWriter;
//import java.io.StringWriter;
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import net.sourceforge.argparse4j.inf.Namespace;
//import org.apache.spark.Accumulator;
//import org.apache.spark.SparkConf;
//import org.apache.spark.api.java.JavaPairRDD;
//import org.apache.spark.api.java.JavaRDD;
//import org.apache.spark.api.java.JavaSparkContext;
//import org.json.JSONObject;
//import scala.Tuple2;
//import scala.Tuple4;
//import scala.Tuple5;
//
///**
// *
// * @author thaonv
// */
//public class VOIPStatsJob extends ZaloStatsApp {
//
//    private static final String TAG = VOIPStatsJob.class.getSimpleName();
//
//    public static void main(String[] args) throws Exception {
//        String path = "/home/cpu10869-local/sandbox/VOIP/collector6577_08_VOIP_2017_04_10_08_33_14_535.log";
////        String path = "/home/cpu10869-local/sandbox/VOIP/test_raw.txt";
////        String path = "/home/cpu10869-local/sandbox/VOIP/test.txt";
//        String master = "local[*]";
//        SparkConf conf = new SparkConf()
//                .setAppName(VOIPStatsJob.class.getName())
//                .setMaster(master)
//                .set("spark.ui.port‌​", "7077")
//                .set("spark.executor.instances", "2")
//                .set("spark.executor.cores", "4")
//                .set("spark.executor.memory", "1g");
//        JavaSparkContext sc = new JavaSparkContext(conf);
//        CallType callType = CallType.AUDIO;
//        Map<String, VoipStats> result = startJob(path, sc, callType, statiticsVal);
//        System.out.println("======================================================");
//        for (String key : result.keySet()) {
//            System.out.println(key + ", " + Utils.toJson(result.get(key)));
//        }
//        System.out.println(result);
//        sc.close();
//    }
//
//    public static Map<String, VoipStats> startJob(String inputFilePath, JavaSparkContext sc, CallType callType) {
//        Map<String, VoipStats> result = null;
//        try {
//            JavaRDD<String> input = sc.textFile(inputFilePath);
//            List<Tuple2<String, Tuple5<Integer, Integer, Integer, Integer, NodeConnection>>> timePhone = input.flatMapToPair(line -> getRecords(line, callType))
//                    .reduceByKey((Tuple5<Integer, Integer, Integer, Integer, NodeConnection> t1, Tuple5<Integer, Integer, Integer, Integer, NodeConnection> t2) -> {
//                        NodeConnection model = new NodeConnection();
//                        Integer numRecords = 0;
//                        Integer sumCallDuration = 0;
//                        Integer rttAvg = 0;
//                        Integer lossAvg = 0;
//                        try {
//                            numRecords = t1._1() + t2._1();
//                            sumCallDuration = t1._2() + t2._2();
//                            rttAvg = t1._3() + t2._3();
//                            lossAvg = t1._4() + t2._4();
//                            // teco computation
//                            model = t1._5().setConnection(t2._5());
//                        } catch (Exception ex) {
//                            StringWriter error = new StringWriter();
//                            ex.printStackTrace(new PrintWriter(error));
//                            System.out.println("startJob() " + error.toString());
//                        }
//                        return new Tuple5<>(numRecords, sumCallDuration, rttAvg, lossAvg, model);
//                    }).collect();
//            // post processing
//            List<Tuple2<String, Tuple4<Integer, Integer, Integer, Integer>>> tecoAccumulation = sc.parallelize(timePhone)
//                    .flatMapToPair((Tuple2<String, Tuple5<Integer, Integer, Integer, Integer, NodeConnection>> record) -> getPostRecords(record))
//                    .reduceByKey((Tuple4<Integer, Integer, Integer, Integer> tx, Tuple4<Integer, Integer, Integer, Integer> ty) -> {
//                        Integer numRecords = 0;
//                        Integer sumCallDuration = 0;
//                        Integer rttAvg = 0;
//                        Integer lossAvg = 0;
//                        try {
//                            numRecords = tx._1() + ty._1();
//                            sumCallDuration = tx._2() + ty._2();
//                            rttAvg = tx._3() + ty._3();
//                            lossAvg = tx._4() + ty._4();
//                        } catch (Exception ex) {
//                            System.out.println(ex);
//                        }
//                        return new Tuple4(numRecords, sumCallDuration, rttAvg, lossAvg);
//                    }).collect();
//            //
////            System.out.println("===========================");
////            for (Tuple2 val : tecoAccumulation) {
////                System.out.println(val);
////            }
//            //
////            System.out.println("===========================");
////            int numOne = 0;
////            for (Tuple2 val : timePhone) {
////                Tuple5<Integer, Integer, Integer, Integer, NodeConnection> tv = (Tuple5) val._2();
////                if (tv._1() > 1 && (tv._5().getTecoCallee() == null || tv._5().getTecoCaller() == null)) {
////                    numOne++;
////                    System.out.println(Utils.toJson(val));
////                }
////            }
////            System.out.println("Num > 2: " + numOne);
//            // RESULT
//            result = getStats(tecoAccumulation);
////            System.out.println("STATS:: " + Utils.toJson(result));
//        } catch (Exception ex) {
//            System.out.println(ex);
//        }
//        return result;
//    }
//
//    public static Map<String, VoipStats> getStats(List<Tuple2<String, Tuple4<Integer, Integer, Integer, Integer>>> data) {
//        Map<String, VoipStats> stats = new HashMap<>();
//        for (Tuple2<String, Tuple4<Integer, Integer, Integer, Integer>> element : data) {
//            Tuple4<Integer, Integer, Integer, Integer> getTuple = (Tuple4<Integer, Integer, Integer, Integer>) element._2();
//            try {
//                long numOfRecords = (long) getTuple._1() / 2;
//                long sumOfTime = (long) getTuple._2();
//                double rttAvg = Utils.setFloatingPointNumber(((double) getTuple._3() / numOfRecords), 3, Double.class);
//                double lossAvg = Utils.setFloatingPointNumber(((double) getTuple._4() / numOfRecords), 3, Double.class);
//                VoipStats value = new VoipStats(numOfRecords, sumOfTime, rttAvg, lossAvg);
//                stats.put(element._1(), value);
//            } catch (Exception ex) {
//                StringWriter error = new StringWriter();
//                ex.printStackTrace(new PrintWriter(error));
//                System.err.println(error.toString());
//            }
//        }
//        return stats;
//    }
//
//    public static List<Tuple2<String, Tuple4<Integer, Integer, Integer, Integer>>> getPostRecords(Tuple2<String, Tuple5<Integer, Integer, Integer, Integer, NodeConnection>> record) {
//        List<Tuple2<String, Tuple4<Integer, Integer, Integer, Integer>>> result = new ArrayList<>();
//        if (record._2()._1() > 1 && record._2()._5().getTecoCaller() != null && record._2()._5().getTecoCallee() != null) {
//            String key = record._2()._5().getTecoCaller() + "-" + record._2()._5().getTecoCallee();
//            result.add(new Tuple2(key, new Tuple4(record._2()._1(), record._2()._2(), record._2()._3(), record._2()._4())));
//        }
//        return result;
//    }
//
//    public static List<Tuple2<String, Tuple5<Integer, Integer, Integer, Integer, NodeConnection>>> getRecords(String line, CallType callType) {
//        List<Tuple2<String, Tuple5<Integer, Integer, Integer, Integer, NodeConnection>>> result = new ArrayList<>();
//        try {
//            String[] parts = line.split("\t");
//            String tecoNetwork = getLocationAndNetworkType(parts[VoipSchema.COUNTRY_NAME], parts[VoipSchema.NETWORK_TYPE], parts[VoipSchema.IP]);
//            tecoNetwork = (null == tecoNetwork || tecoNetwork.isEmpty()) ? "other" : tecoNetwork;
////            System.out.println("======= tecoNetwork ===== " + tecoNetwork);
//            if (parts[VoipSchema.COMMAND].equals("406") || parts[VoipSchema.COMMAND].equals("3071")) {
//                String[] status = parts[VoipSchema.PARAM].split("\\|");
//                boolean durationFlag = false;
//                if (status.length > 4) {
//                    durationFlag = (Integer.valueOf(status[1]) > 0 && Integer.valueOf(status[1]) < 100000);
//                }
//                if (Integer.valueOf(status[0]) >= 0 && Integer.valueOf(status[0]) < 100 && durationFlag) {
////                    long time = Long.valueOf(parts[VoipSchema.TIME]);
////                    String addingKey = Utils.dateString(time, "yyyy:MM:dd_HH:mm:ss:ms", null);
//                    String addingKey = status[2];
//                    StringBuilder key = new StringBuilder();
//                    if (parts[VoipSchema.COMMAND].equals("406")) {
//                        key.append(parts[VoipSchema.SRC_ID])
//                                .append("-")
//                                .append(parts[VoipSchema.DES_ID])
//                                .append("-")
//                                .append(addingKey);
//                    } else {
//                        key.append(parts[VoipSchema.DES_ID])
//                                .append("-")
//                                .append(parts[VoipSchema.SRC_ID])
//                                .append("-")
//                                .append(addingKey);
//                    }
//                    //
////                    if (status.length > 4) {
//                    Map<String, Integer> params = getAvgParams(status[4]);
////                    System.out.println("CallType param: " + callType.toString());
////                    System.out.println("CallType: " + params.get("call-type"));
//                    if (!callType.equals(CallType.ALL)) {
//                        if (callType.toString().equals(String.valueOf(params.get("call-type")))) {
//                            result.add(new Tuple2<>(key.toString(), new Tuple5<>(1, Integer.valueOf(status[1]), params.get("rtt-avg"), params.get("loss-avg"), new NodeConnection(parts[VoipSchema.COMMAND], tecoNetwork))));
//                        }
//                    } else {
//                        result.add(new Tuple2<>(key.toString(), new Tuple5<>(1, Integer.valueOf(status[1]), params.get("rtt-avg"), params.get("loss-avg"), new NodeConnection(parts[VoipSchema.COMMAND], tecoNetwork))));
//                    }
////                    }
//                }
//            }
//        } catch (Exception ex) {
//            System.out.println("getRecords(): " + ex);
//        }
//        return result;
//    }
//
//    public static String getLocationAndNetworkType(String country, String network, String ip) {
//        StringBuilder result = new StringBuilder();
//        try {
//            if ("3".equals(network)) {
//                // consider 3G, Request ASN here
//                String asn = null;
////                TASNResult tasnResult = CLIENT.getASN(ip);
////                if (tasnResult.getError() >= 0) {
////                    asn = tasnResult.getValue().getAsnCode() + "";
////                }
//                //
//                asn = "131124";
//                //
//                //
//                List<String> gType = Utils.mapIsp(asn);
//                for (String val : gType) {
//                    if (val.equals("viettel")) {
//                        result.append("3g-viettel");
//                        break;
//                    } else if (val.equals("mobifone")) {
//                        result.append("3g-mobi");
//                        break;
//                    } else if (val.equals("vnpt")) {
//                        result.append("3g-vina");
//                        break;
//                    }
//                }
//            } else if ("0".equals(network)) {
//                if ("vn".equals(country)) {
//                    result.append("wifi-vn");
//                } else {
//                    result.append("international");
//                }
//            } else {
//                result.append("other");
//            }
//        } catch (Exception ex) {
//            StringWriter error = new StringWriter();
//            ex.printStackTrace(new PrintWriter(error));
//            System.out.println("getLocationAndNetworkType(), " + error.toString());
//        }
//        return result.toString();
//    }
//
//    public static Map<String, Integer> getAvgParams(String data) {
//        Map<String, Integer> result = new HashMap<>();
//        Type type = new TypeToken<List<Integer>>() {
//        }.getType();
//        try {
//            List<Integer> rttList = new ArrayList<>();
//            List<Integer> lossList = new ArrayList<>();
//            JSONObject map = new JSONObject(data);
//            String rttGroup = map.getString("RTT");
//            String lostGroup = map.getString("RxLossPer");
//            Utils.fromJson(rttGroup, type, rttList);
//            Utils.fromJson(lostGroup, type, lossList);
//            Integer callType = null;
//            try {
//                callType = map.getInt("CallType");
//            } catch (Exception ignore) {
//                callType = 3;
//            }
//            result.put("rtt-avg", rttList.get(1));
//            result.put("loss-avg", lossList.get(1));
//            result.put("call-type", callType);
////            System.out.println("toJson() rttGroup: " + Utils.toJson(result));
//        } catch (Exception ex) {
////            throw new Exception(Utils.toJson(data), ex);
//            System.out.println("getAvgParams(), Error DATA: " + data + "\n" + ex);
////            System.out.println(ex);
//        }
//        return result;
//    }
//
//}
