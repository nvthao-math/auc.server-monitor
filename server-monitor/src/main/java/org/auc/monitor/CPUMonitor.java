/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.monitor;

import java.util.TimerTask;
import org.auc.core.utils.BashExecutor;
import org.auc.core.utils.CommonUtils;
import org.auc.monitor.bash.CpuBash;
import org.auc.monitor.dao.CPUInfo;

/**
 *
 * @author thaonv
 */
public class CPUMonitor extends TimerTask {

    private static final String TAG = CPUMonitor.class.getSimpleName();

    // constructor
    public CPUMonitor() {
        super();
    }

    public static CPUInfo getCpuUsage() {
        Integer cpuUsage = (new BashExecutor<Integer>(CpuBash.COMMAND) {
            @Override
            protected Integer build() {
                return Integer.valueOf(this.resultSet.toString().trim());
            }
        }).run();
        return new CPUInfo(cpuUsage);
    }

    @Override
    public void run() {
        CPUInfo cpuUsage = getCpuUsage();
        System.out.println(CommonUtils.toJson(cpuUsage));
    }

}
