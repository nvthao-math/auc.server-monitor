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
public class TaskInfo {

    private int process;
    private int threads;

    // constructor
    public TaskInfo() {
        super();
    }

    /**
     * @return the process
     */
    public int getProcess() {
        return process;
    }

    /**
     * @param process the process to set
     */
    public void setProcess(int process) {
        this.process = process;
    }

    /**
     * @return the threads
     */
    public int getThreads() {
        return threads;
    }

    /**
     * @param threads the threads to set
     */
    public void setThreads(int threads) {
        this.threads = threads;
    }

}
