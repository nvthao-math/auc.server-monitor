/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.monitor;

import java.util.TimerTask;
import org.auc.core.utils.BashExecutor;
import org.auc.core.utils.EUtils;
import org.auc.monitor.bash.DiskBash;
import org.auc.monitor.dao.DiskInfo;

/**
 *
 * @author thaonv
 */
public class DiskMonitor extends TimerTask {

    // constructor
    public DiskMonitor() {
        super();
    }

    public static DiskInfo getInfo() {
        Double diskUsage = (new BashExecutor<Double>(DiskBash.COMMAND) {
            @Override
            protected Double build() {
                return Double.valueOf(this.resultSet.toString().replace("%", "").trim());
            }
        }).run();
        return new DiskInfo(diskUsage);
    }

    @Override
    public void run() {
        DiskInfo info = getInfo();
        System.out.println(EUtils.toJson(info));
    }

}
