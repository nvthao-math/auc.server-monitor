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
public class Attributes {

    private String type;
    private String index;

    // constructor
    public Attributes() {
        super();
    }

    public Attributes(String type) {
        this.type = type;
    }

    public Attributes(String type, String index) {
        this.type = type;
        this.index = index;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the index
     */
    public String getIndex() {
        return index;
    }

    /**
     * @param index the index to set
     */
    public void setIndex(String index) {
        this.index = index;
    }

}
