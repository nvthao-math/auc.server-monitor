/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.monitor;

import java.util.Date;
import org.auc.core.file.utils.Logger;
import org.auc.core.file.utils.WriterExecutor;
import org.auc.core.utils.TimeUtils;
import org.auc.gps.job.Executor;
import org.auc.monitor.dao.CPUInfo;
import org.auc.monitor.dao.DiskInfo;
import org.auc.monitor.dao.RamInfo;
import org.auc.monitor.dao.TaskInfo;

/**
 *
 * @author thaonv
 */
public class ServerMonitor extends Executor {

    private static final String TAG = ServerMonitor.class.getSimpleName();
    private static final String TAB_DELIMITER = "\t";
    private static final String PATH = "/home/cpu10869-local/sandbox/server-metrics/metrics.txt";

    public static void main(String[] args) {
        ServerMonitor job = new ServerMonitor();
        job.start();
    }

    @Override
    public void start() {
        while (true) {
            try {
                long t1 = System.currentTimeMillis();
                RamInfo ramInfo = RamMonitor.getMemInfo();
                TaskInfo taskInfo = TaskMonitor.getTaskInfo();
                long t2 = System.currentTimeMillis();
                CPUInfo cpuInfo = CPUMonitor.getCpuUsage();
                System.out.println("get CPU time: " + (System.currentTimeMillis() - t2) + " (ms)");
                DiskInfo diskInfo = DiskMonitor.getInfo();
                //
                String time = TimeUtils.toString(new Date(), TimeUtils.yyyy_MM_dd_HHmmss);
                StringBuilder result = new StringBuilder()
                        .append(time)
                        .append(TAB_DELIMITER)
                        .append(ramInfo.getPercentMemUsed())
                        .append(TAB_DELIMITER)
                        .append(ramInfo.getPercentSwapUsed())
                        .append(TAB_DELIMITER)
                        .append(taskInfo.getProcess())
                        .append(TAB_DELIMITER)
                        .append(taskInfo.getThreads())
                        .append(TAB_DELIMITER)
                        .append(cpuInfo.getCpuUsage())
                        .append(TAB_DELIMITER)
                        .append(diskInfo.getPercentDiskAvailable());
                (new WriterExecutor(PATH, true) {
                    @Override
                    public void builds() throws Exception {
                        this.bufferedWriter.write(result.toString());
                        this.bufferedWriter.write("\n");
                    }
                }).run();
                System.out.println(result);
                System.out.println("Time consuming: " + (System.currentTimeMillis() - t1) + " (ms)");
            } catch (Exception ex) {
                Logger.error(TAG, ex);
            }
        }
    }

}
