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
public enum Type {

    LONG("long"),
    DATE("date"),
    INT("integer"),
    STRING("string"),
    DOUBLE("double"),
    NOT("not");

    private String type;

    // constructor
    private Type(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }

}
