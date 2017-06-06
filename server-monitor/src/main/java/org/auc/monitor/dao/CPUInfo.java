/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.monitor.dao;

/**
 *
 * @author thaonguyen
 */
public class CPUInfo {

    private Integer cpuUsage;

    // constructor
    public CPUInfo() {
        super();
    }

    public CPUInfo(Integer cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    /**
     * @return the cpuUsage
     */
    public Integer getCpuUsage() {
        return cpuUsage;
    }

    /**
     * @param cpuUsage the cpuUsage to set
     */
    public void setCpuUsage(Integer cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

}
