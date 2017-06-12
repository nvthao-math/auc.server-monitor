/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.monitor.server.dao;

/**
 *
 * @author thaonguyen
 */
public class DiskInfo {

    private double diskUsage;

    // constructor
    public DiskInfo() {
        super();
    }

    public DiskInfo(double diskUsage) {
        this.diskUsage = diskUsage;
    }

    /**
     * @return the diskUsage
     */
    public double getDiskUsage() {
        return diskUsage;
    }

    /**
     * @param diskUsage the diskUsage to set
     */
    public void setDiskUsage(double diskUsage) {
        this.diskUsage = diskUsage;
    }

    public double getPercentDiskAvailable() {
        return (100 - this.diskUsage);
    }

}
