/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.core.utils;

/**
 *
 * @author thaonguyen
 */
public class ShutdownHook {

    // constructor
    public ShutdownHook() {
        super();
    }

    public static <T extends Thread> void addHook(T hook) {
        Runtime.getRuntime().addShutdownHook(hook);
    }

}
