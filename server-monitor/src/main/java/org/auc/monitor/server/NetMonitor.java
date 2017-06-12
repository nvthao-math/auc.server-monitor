/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.monitor.server;

import org.auc.core.utils.BashExecutor;
import org.auc.core.utils.EUtils;
import org.auc.monitor.server.bash.NetBash;
import org.auc.monitor.server.dao.NetInfo;

/**
 *
 * @author thaonv
 */
public class NetMonitor {

    private static Long RX_PREV;
    private static Long TX_PREV;

    public static void main(String[] args) {
        while (true) {
            long t1 = System.currentTimeMillis();
            NetInfo netInfo = getInfo();
            System.out.println(EUtils.toJson(netInfo));
            try {
                Thread.sleep(1000l);
            } catch (Exception ex) {
            }
        }
    }

    public static NetInfo getInfo() {
        NetInfo netInfo = (new BashExecutor<NetInfo>(NetBash.COMMAND) {
            @Override
            protected NetInfo build() {
                String[] parts = this.resultSet.toString().split(":");
                Long rx = Long.valueOf(parts[0].trim());
                Long tx = Long.valueOf(parts[1].trim());
                if (RX_PREV == null || TX_PREV == null) {
                    RX_PREV = rx;
                    TX_PREV = tx;
                    try {
                        Thread.sleep(1000l);
                    } catch (Exception ignore) {
                    }
                    getInfo();
                }
                NetInfo netInfo = new NetInfo((rx - RX_PREV), (tx - TX_PREV));
                RX_PREV = rx;
                TX_PREV = tx;
                return netInfo;
            }
        }).run();
        return netInfo;
    }

}
