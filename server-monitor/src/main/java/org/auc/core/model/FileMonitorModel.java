/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.core.model;

import org.auc.core.file.utils.Logger;
import static org.auc.gps.inmem.cache.CachePool.FILE_MONITOR_POOL;

/**
 *
 * @author inet
 */
public class FileMonitorModel {

    public static final String TAG = FileMonitorModel.class.getSimpleName();
    private String fileName;
    private long lineOnDisk;
    private long lineRead;
    private long offset;
    private long numOfCharacter;

    // constructor
    public FileMonitorModel() {
        super();
    }

    public FileMonitorModel(String fileName) {
        this.fileName = fileName;
        this.lineOnDisk = getNumberOfLineOnDisk();
    }

    public int getNumberOfLineOnDisk() {
        int num = 0;
        try {
            num = FILE_MONITOR_POOL.get(fileName);
        } catch (Exception ex) {
            Logger.error(TAG, ex);
        }
        return num;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the lineOnDisk
     */
    public long getLineOnDisk() {
        return lineOnDisk;
    }

    /**
     * @param lineOnDisk the lineOnDisk to set
     */
    public void setLineOnDisk(int lineOnDisk) {
        this.lineOnDisk = lineOnDisk;
    }

    /**
     * @return the lineRead
     */
    public long getLineRead() {
        return lineRead;
    }

    /**
     * @param lineRead the lineRead to set
     */
    public void setLineRead(int lineRead) {
        this.lineRead = lineRead;
    }

    /**
     * @return the offset
     */
    public long getOffset() {
        return offset;
    }

    /**
     * @param offset the offset to set
     */
    public void setOffset(int offset) {
        this.offset = offset;
    }

    /**
     * @return the numOfCharacter
     */
    public long getNumOfCharacter() {
        return numOfCharacter;
    }

    /**
     * @param numOfCharacter the numOfCharacter to set
     */
    public void setNumOfCharacter(int numOfCharacter) {
        this.numOfCharacter = numOfCharacter;
    }

    public void increaseLineRead(int lineRead) {
        this.lineRead += lineRead;
    }

    public void increaseOffset(int offset) {
        this.offset += offset;
    }

    public void increaseNumOfCharacter(int lens) {
        this.numOfCharacter += lens;
    }

}
