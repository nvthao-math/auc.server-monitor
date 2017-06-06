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
public class NetBash {

    public static final String COMMAND = "IF=`ls -1 /sys/class/net/ | head -1`; "
            + "RX=`cat /sys/class/net/${IF}/statistics/rx_bytes`; "
            + "TX=`cat /sys/class/net/${IF}/statistics/tx_bytes`; echo $RX:$TX";

}
