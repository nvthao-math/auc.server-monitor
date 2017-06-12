/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.monitor.server.dao;

import java.io.PrintWriter;
import java.io.StringWriter;
import org.auc.core.utils.NumberUtils;

/**
 *
 * @author thaonguyen
 */
//Buffers:          122356 kB
//Cached:          1708180 kB
//SwapCached:         3796 kB
//Active:          5684792 kB
//Inactive:        1779100 kB
//Active(anon):    4932296 kB
//Inactive(anon):  1106080 kB
//Active(file):     752496 kB
//Inactive(file):   673020 kB
//SwapTotal:       8274940 kB
//SwapFree:        8215128 kB
//Dirty:               608 kB
public class RamInfo {

    // all metrics using GB
    private double memTotal; //MemTotal:        8063628 kB
    private double memFree; //MemFree:          270336 kB
    private double memAvailable; //MemAvailable:    1551028 kB
    private double memUsed;
    private double buffers; //Buffers:          122356 kB
    private double cached; //Cached:          1708180 kB
    private double active; //Active:          5684792 kB
    private double inActive; //Inactive:        1779100 kB
    private double activeAnon; //Active(anon):    4932296 kB
    private double inActiveAnon; //Inactive(anon):  1106080 kB
    private double activeFile; //Active(file):     752496 kB
    private double inActiveFile; //Inactive(file):   673020 kB
    private double swapCached; //SwapCached:         3796 kB
    private double swap; //SwapTotal:       8274940 kB
    private double swapFree; //SwapFree:        8215128 kB
    private double dirty; //Dirty:               608 kB

    // constructor
    public RamInfo() {
        super();
    }

    /**
     * @return the memTotal
     */
    public double getMemTotal() {
        return memTotal;
    }

    /**
     * @param memTotal the memTotal to set
     */
    public void setMemTotal(String memTotal) {
        this.memTotal = measureGB(memTotal);
    }

    /**
     * @return the memFree
     */
    public double getMemFree() {
        return memFree;
    }

    /**
     * @param memFree the memFree to set
     */
    public void setMemFree(String memFree) {
        this.memFree = measureGB(memFree);
    }

    /**
     * @return the memAvailable
     */
    public double getMemAvailable() {
        return memAvailable;
    }

    /**
     * @param memAvailable the memAvailable to set
     */
    public void setMemAvailable(String memAvailable) {
        this.memAvailable = measureGB(memAvailable);
    }

    /**
     * @return the memUsed
     */
    public double getMemUsed() {
        try {
            this.memUsed = NumberUtils.setFloatingPointNumber((this.memTotal - (this.memFree + this.cached + this.buffers)), 2, Double.class);
        } catch (Exception ex) {
            StringWriter error = new StringWriter();
            ex.printStackTrace(new PrintWriter(error));
            System.out.println(error.toString());
        }
        return this.memUsed;
    }

    /**
     * @return the buffers
     */
    public double getBuffers() {
        return buffers;
    }

    /**
     * @param buffers the buffers to set
     */
    public void setBuffers(String buffers) {
        this.buffers = measureGB(buffers);
    }

    /**
     * @return the cached
     */
    public double getCached() {
        return cached;
    }

    /**
     * @param cached the cached to set
     */
    public void setCached(String cached) {
        this.cached = measureGB(cached);
    }

    /**
     * @return the active
     */
    public double getActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(String active) {
        this.active = measureGB(active);
    }

    /**
     * @return the inActive
     */
    public double getInActive() {
        return inActive;
    }

    /**
     * @param inActive the inActive to set
     */
    public void setInActive(String inActive) {
        this.inActive = measureGB(inActive);
    }

    /**
     * @return the activeAnon
     */
    public double getActiveAnon() {
        return activeAnon;
    }

    /**
     * @param activeAnon the activeAnon to set
     */
    public void setActiveAnon(String activeAnon) {
        this.activeAnon = measureGB(activeAnon);
    }

    /**
     * @return the inActiveAnon
     */
    public double getInActiveAnon() {
        return inActiveAnon;
    }

    /**
     * @param inActiveAnon the inActiveAnon to set
     */
    public void setInActiveAnon(String inActiveAnon) {
        this.inActiveAnon = measureGB(inActiveAnon);
    }

    /**
     * @return the activeFile
     */
    public double getActiveFile() {
        return activeFile;
    }

    /**
     * @param activeFile the activeFile to set
     */
    public void setActiveFile(String activeFile) {
        this.activeFile = measureGB(activeFile);
    }

    /**
     * @return the swapCached
     */
    public double getSwapCached() {
        return swapCached;
    }

    /**
     * @param swapCached the swapCached to set
     */
    public void setSwapCached(String swapCached) {
        this.swapCached = measureGB(swapCached);
    }

    /**
     * @return the inActiveFile
     */
    public double getInActiveFile() {
        return inActiveFile;
    }

    /**
     * @param inActiveFile the inActiveFile to set
     */
    public void setInActiveFile(String inActiveFile) {
        this.inActiveFile = measureGB(inActiveFile);
    }

    /**
     * @return the swap
     */
    public double getSwap() {
        return swap;
    }

    /**
     * @param swap the swap to set
     */
    public void setSwap(String swap) {
        this.swap = measureGB(swap);
    }

    /**
     * @return the swapFree
     */
    public double getSwapFree() {
        return swapFree;
    }

    /**
     * @param swapFree the swapFree to set
     */
    public void setSwapFree(String swapFree) {
        this.swapFree = measureGB(swapFree);
    }

    /**
     * @return the dirty
     */
    public double getDirty() {
        return dirty;
    }

    /**
     * @param dirty the dirty to set
     */
    public void setDirty(String dirty) {
        this.dirty = measureGB(dirty);
    }

    public double getPercentMemUsed() {
        return (this.getMemUsed() / this.getMemTotal()) * 100;
    }

    public double getPercentSwapUsed() {
        return (1 - (this.swapFree / this.swap)) * 100;
    }

    public double measureGB(String measure) {
        // measure variable in kB
        Double result = 0d;
        try {
            result = NumberUtils.setFloatingPointNumber(NumberUtils.safeParseDouble(measure.replace("kB", "")) / (1024 * 1024), 2, Double.class);
        } catch (Exception ex) {
            StringWriter error = new StringWriter();
            ex.printStackTrace(new PrintWriter(error));
            System.out.println(error.toString());
        }
        return result;
    }

}
