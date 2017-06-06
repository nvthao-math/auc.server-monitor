/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.gen.stats;

/**
 *
 * @author thaonv
 */
public enum CallType {

    AUDIO("0"), VIDEO("1"), ALL("2");
    private String type;

    // constructor
    private CallType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }

}
