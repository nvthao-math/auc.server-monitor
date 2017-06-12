/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.monitor.server.dao;

/**
 *
 * @author thaonv
 */
public class NetInfo {

    private Long rx;
    private Long tx;

    // constructor
    public NetInfo() {
        super();
    }

    public NetInfo(Long rx, Long tx) {
        this.rx = rx;
        this.tx = tx;
    }

    /**
     * @return the rx
     */
    public Long getRx() {
        return rx;
    }

    /**
     * @param rx the rx to set
     */
    public void setRx(Long rx) {
        this.rx = rx;
    }

    /**
     * @return the tx
     */
    public Long getTx() {
        return tx;
    }

    /**
     * @param tx the tx to set
     */
    public void setTx(Long tx) {
        this.tx = tx;
    }

}
