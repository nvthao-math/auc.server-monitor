/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.core.model;

import java.util.Collection;

/**
 *
 * @author thaonguyen
 */
public class ReaderOffsetModel<T extends Collection> {

    private boolean isContinue;
    private Collection collection;

    public ReaderOffsetModel(T collection, boolean isContinue) {
        this.collection = collection;
        this.isContinue = isContinue;
    }

    /**
     * @return the isContinue
     */
    public boolean isIsContinue() {
        return isContinue;
    }

    /**
     * @param isContinue the isContinue to set
     */
    public void setIsContinue(boolean isContinue) {
        this.isContinue = isContinue;
    }

    /**
     * @return the collection
     */
    public Collection getData() {
        return collection;
    }

    /**
     * @param collection the collection to set
     */
    public void setData(Collection collection) {
        this.collection = collection;
    }

    public int dataSize() {
        return this.collection.size();
    }

}
