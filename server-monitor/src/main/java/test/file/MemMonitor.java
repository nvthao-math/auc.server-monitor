/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.file;

/**
 *
 * @author thaonguyen
 */
public class MemMonitor {

    public static void main(String[] args) {
        System.out.println("Max: " + (Runtime.getRuntime().maxMemory() / (1024 * 1024)) + " MB");
        System.out.println("Usage: " + ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1024)) + " MB");
        System.out.println("Free: " + (Runtime.getRuntime().freeMemory() / (1024 * 1024)) + " MB");
        ////
        System.out.println("==================");
        int mb = 1024 * 1024;
        //Getting the runtime reference from system
        Runtime runtime = Runtime.getRuntime();
        System.out.println("##### Heap utilization statistics [MB] #####");
        //Print used memory
        System.out.println("Used Memory:"
                + (runtime.totalMemory() - runtime.freeMemory()) / mb);
        //Print free memory
        System.out.println("Free Memory:"
                + runtime.freeMemory() / mb);
        //Print total available memory
        System.out.println("Total Memory:" + runtime.totalMemory() / mb);
        //Print Maximum available memory
        System.out.println("Max Memory:" + runtime.maxMemory() / mb);
    }

}
