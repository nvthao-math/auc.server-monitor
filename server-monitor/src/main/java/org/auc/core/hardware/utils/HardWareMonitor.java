/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.core.hardware.utils;

import org.auc.core.file.utils.Logger;
import org.auc.core.utils.NumberUtils;

/**
 *
 * @author thaonguyen
 */
public class HardWareMonitor {

    private static final String TAG = HardWareMonitor.class.getSimpleName();
    private static final int MB = 1024 * 1024;

    private double maxMemory;
    private double totalMemory;
    private double usedMemory;
    private double freeMemory;

    // constructor
    public HardWareMonitor() {
        try {
            Runtime runtime = Runtime.getRuntime();
            this.maxMemory = NumberUtils.setFloatingPointNumber((double) (runtime.maxMemory() / MB), 2, Double.class);
            this.totalMemory = NumberUtils.setFloatingPointNumber((double) (runtime.totalMemory() / MB), 2, Double.class);
            this.freeMemory = NumberUtils.setFloatingPointNumber((double) (runtime.freeMemory() / MB), 2, Double.class);
            this.usedMemory = this.totalMemory - this.freeMemory;
        } catch (Exception ex) {
            Logger.error(TAG, ex);
        }
    }

    public static boolean isMemorySafe() {
        HardWareMonitor instance = new HardWareMonitor();
        double percent = instance.getUsedMemory() / instance.getMaxMemory();
        return percent <= 0.8;
    }

    public static boolean isMemorySafe(double limit) {
        HardWareMonitor instance = new HardWareMonitor();
        double percent = instance.getUsedMemory() / instance.getMaxMemory();
        return percent <= limit;
    }

    /**
     * @return the maxMemory
     */
    public double getMaxMemory() {
        return maxMemory;
    }

    /**
     * @param maxMemory the maxMemory to set
     */
    public void setMaxMemory(double maxMemory) {
        this.maxMemory = maxMemory;
    }

    /**
     * @return the totalMemory
     */
    public double getTotalMemory() {
        return totalMemory;
    }

    /**
     * @param totalMemory the totalMemory to set
     */
    public void setTotalMemory(double totalMemory) {
        this.totalMemory = totalMemory;
    }

    /**
     * @return the usedMemory
     */
    public double getUsedMemory() {
        return usedMemory;
    }

    /**
     * @param usedMemory the usedMemory to set
     */
    public void setUsedMemory(double usedMemory) {
        this.usedMemory = usedMemory;
    }

    /**
     * @return the freeMemory
     */
    public double getFreeMemory() {
        return freeMemory;
    }

    /**
     * @param freeMemory the freeMemory to set
     */
    public void setFreeMemory(double freeMemory) {
        this.freeMemory = freeMemory;
    }

    /**
     *
     * @return
     */
    public static String getInfo() {
        HardWareMonitor instance = new HardWareMonitor();
        StringBuilder sb = new StringBuilder();
        sb.append("{")
                .append("maxMemory:").append(instance.getMaxMemory()).append("(MB)").append(",")
                .append("usedMemory:").append(instance.getUsedMemory()).append("(MB)").append(",")
                .append("freeMemory:").append(instance.getFreeMemory()).append("(MB)")
                .append("}");
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{")
                .append("maxMemory:").append(this.getMaxMemory()).append("(MB)").append(",")
                .append("usedMemory:").append(this.getUsedMemory()).append("(MB)").append(",")
                .append("freeMemory:").append(this.getFreeMemory()).append("(MB)")
                .append("}");
        return sb.toString();
    }

}
