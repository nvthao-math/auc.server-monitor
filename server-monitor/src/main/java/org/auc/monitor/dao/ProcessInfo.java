/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.monitor.dao;

/**
 *
 * @author thaonv
 */
public class ProcessInfo {

    private double cpu;
    private double mem;
    private String time;
    private String cmd;

    // constructor
    public ProcessInfo() {
        super();
    }

    public ProcessInfo(double cpu, double mem, String time, String cmd) {
        this.cpu = cpu;
        this.mem = mem;
        this.time = time;
        this.cmd = cmd;
    }

    /**
     * @return the cpu
     */
    public double getCpu() {
        return cpu;
    }

    /**
     * @param cpu the cpu to set
     */
    public void setCpu(double cpu) {
        this.cpu = cpu;
    }

    /**
     * @return the mem
     */
    public double getMem() {
        return mem;
    }

    /**
     * @param mem the mem to set
     */
    public void setMem(double mem) {
        this.mem = mem;
    }

    /**
     * @return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * @return the cmd
     */
    public String getCmd() {
        return cmd;
    }

    /**
     * @param cmd the cmd to set
     */
    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

}
