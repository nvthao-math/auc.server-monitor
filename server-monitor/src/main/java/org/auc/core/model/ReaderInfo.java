/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.core.model;

/**
 *
 * @author thaonguyen
 */
public class ReaderInfo {

    private String fileName;
    private int offset;

    // constructor
    public ReaderInfo() {
        super();
    }

    public ReaderInfo(String fileName, int offset) {
        this.fileName = fileName;
        this.offset = offset;
    }

    public ReaderInfo(String fileName, String offset) {
        this.fileName = fileName;
        this.offset = Integer.parseInt(offset);
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
     * @return the offset
     */
    public int getOffset() {
        return offset;
    }

    /**
     * @param offset the offset to set
     */
    public void setOffset(int offset) {
        this.offset = offset;
    }

}
