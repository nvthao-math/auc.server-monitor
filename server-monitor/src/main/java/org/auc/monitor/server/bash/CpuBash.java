/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.monitor.server.bash;

/**
 *
 * @author thaonv
 */
public class CpuBash {

    public static final String COMMAND = "echo $[100-$(vmstat 1 2|tail -1|awk '{print $15}')]";

}
