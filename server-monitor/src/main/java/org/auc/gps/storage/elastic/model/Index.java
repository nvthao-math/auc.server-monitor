/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.gps.storage.elastic.model;

/**
 *
 * @author cpu10869-local
 */
public enum Index {

    NOT_ANALYZED("not_analyzed"),
    ANALYZED("analyzed");

    private String index;

    // constructor
    private Index(String index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return this.index;
    }

}
