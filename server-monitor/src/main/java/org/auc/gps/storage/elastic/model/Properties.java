/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.gps.storage.elastic.model;

import java.util.Map;

/**
 *
 * @author cpu10869-local
 */
public class Properties extends FieldMap {

    private Map<String, Attributes> properties;

    public Properties() {
        super();
    }

    public Properties(Map<String, Attributes> properties) {
        this.properties = properties;
    }

    /**
     * @return the properties
     */
    public Map<String, Attributes> getProperties() {
        return properties;
    }

    /**
     * @param properties the properties to set
     */
    public void setProperties(Map<String, Attributes> properties) {
        this.properties = properties;
    }

}
