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
public enum Field {

    ALL("_all"),
    ID("_id"),
    SOURCE("_source");

    private String fieldName;

    // constructor
    private Field(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public String toString() {
        return this.fieldName;
    }

}
