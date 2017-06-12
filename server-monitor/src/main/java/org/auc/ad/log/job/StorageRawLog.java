/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.ad.log.job;

/**
 *
 * @author bigdata
 */
public abstract class StorageRawLog {

    protected static final String TAB = "\t";

    public StorageRawLog() {
        super();
    }

    protected abstract void parse(String line);

}
