/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.monitor.server;

import java.util.Timer;
import org.auc.core.file.utils.Logger;

/**
 *
 * @author thaonguyen
 */
public class MetricsRendering {

    public static final String TAG = MetricsRendering.class.getSimpleName();
    public static Timer TIMER = new Timer();

    public static void main(String[] args) {
        try {
            RamMonitor ramMonitor = new RamMonitor();
            TaskMonitor taskMonitor = new TaskMonitor();
            CPUMonitor cpuMonitor = new CPUMonitor();
            DiskMonitor diskMonitor = new DiskMonitor();
            TIMER.schedule(ramMonitor, 0, 4 * 1000);
            TIMER.schedule(taskMonitor, 0, 4 * 1000);
            TIMER.schedule(cpuMonitor, 0, 4 * 1000);
            TIMER.schedule(diskMonitor, 0, 4 * 1000);
        } catch (Exception ex) {
            Logger.error(TAG, ex);
        }
    }

}
