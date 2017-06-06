/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.monitor.bash;

/**
 *
 * @author thaonv
 */
public class DiskBash {

    public static final String COMMAND = "df -h --total | tail -1 | awk '{print $5}'";

}
