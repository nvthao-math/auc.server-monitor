///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package org.auc.gen.stats;
//
//import com.vng.zalostats.common.Utils;
//import java.io.PrintWriter;
//import java.io.StringWriter;
//
///**
// *
// * @author thaonv
// */
//public class VoipStats {
//
//    private long numOfRecords;
//    private long sumOfTime;
//    private double averageTimeOfTune;
//    private double rttAvg;
//    private double lossAvg;
//
//    public VoipStats() {
//        super();
//    }
//
//    public VoipStats(long numOfRecords, long sumOfTime, double rttAvg, double lossAvg) {
//        this.numOfRecords = numOfRecords;
//        this.sumOfTime = sumOfTime;
//        this.averageTimeOfTune = setAverageOfTune(numOfRecords, sumOfTime);
//        this.rttAvg = rttAvg;
//        this.lossAvg = lossAvg;
//    }
//
//    /**
//     * @return the numOfRecords
//     */
//    public long getNumOfRecords() {
//        return numOfRecords;
//    }
//
//    /**
//     * @param numOfRecords the numOfRecords to set
//     */
//    public void setNumOfRecords(long numOfRecords) {
//        this.numOfRecords = numOfRecords;
//    }
//
//    /**
//     * @return the sumOfTime
//     */
//    public long getSumOfTime() {
//        return sumOfTime;
//    }
//
//    /**
//     * @param sumOfTime the sumOfTime to set
//     */
//    public void setSumOfTime(long sumOfTime) {
//        this.sumOfTime = sumOfTime;
//    }
//
//    /**
//     * @return the averageOfTune
//     */
//    public double getAverageOfTune() {
//        return getAverageTimeOfTune();
//    }
//
//    /**
//     * @param averageOfTune the averageOfTune to set
//     */
//    public void setAverageOfTune(double averageOfTune) {
//        this.setAverageTimeOfTune(averageOfTune);
//    }
//
//    public double setAverageOfTune(long numOfRecords, long sumOfTime) {
//        double avgTune = 0.0;
//        try {
//            avgTune = Utils.setFloatingPointNumber(((double) sumOfTime / numOfRecords), 3, Double.class);
//        } catch (Exception ex) {
//            StringWriter error = new StringWriter();
//            ex.printStackTrace(new PrintWriter(error));
//            System.err.println(error.toString());
//        }
//        return avgTune;
//    }
//
//    /**
//     * @return the averageTimeOfTune
//     */
//    public double getAverageTimeOfTune() {
//        return averageTimeOfTune;
//    }
//
//    /**
//     * @param averageTimeOfTune the averageTimeOfTune to set
//     */
//    public void setAverageTimeOfTune(double averageTimeOfTune) {
//        this.averageTimeOfTune = averageTimeOfTune;
//    }
//
//    /**
//     * @return the rttAvg
//     */
//    public double getRttAvg() {
//        return rttAvg;
//    }
//
//    /**
//     * @param rttAvg the rttAvg to set
//     */
//    public void setRttAvg(double rttAvg) {
//        this.rttAvg = rttAvg;
//    }
//
//    /**
//     * @return the lossAvg
//     */
//    public double getLossAvg() {
//        return lossAvg;
//    }
//
//    /**
//     * @param lossAvg the lossAvg to set
//     */
//    public void setLossAvg(double lossAvg) {
//        this.lossAvg = lossAvg;
//    }
//
//}
