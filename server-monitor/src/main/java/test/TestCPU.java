/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import org.auc.core.utils.BashExecutor;
import org.auc.monitor.server.CPUMonitor;
import org.auc.monitor.server.bash.CpuBash;
import org.auc.monitor.server.dao.CPUInfo;

/**
 *
 * @author thaonv
 */
public class TestCPU {

    static String COMMAND = "awk -v a=\"$(awk '/cpu /{print $2+$4,$2+$4+$5}' /proc/stat; sleep 1)\" '/cpu /{split(a,b,\" \"); print 100*($2+$4-b[1])/($2+$4+$5-b[2])}'  /proc/stat";

    public static void main(String[] args) {
        while (true) {
            long t2 = System.currentTimeMillis();
            CPUInfo cpuInfo = CPUMonitor.getCpuUsage();
            System.out.println("1 -- get CPU time: " + (System.currentTimeMillis() - t2) + " (ms)");
            //
            //
            long t1 = System.currentTimeMillis();
            Double cpuUsage = (new BashExecutor<Double>(COMMAND) {
                @Override
                protected Double build() {
                    return Double.valueOf(this.resultSet.toString().trim());
                }
            }).run();
            System.out.println("2 -- get CPU time: " + (System.currentTimeMillis() - t1) + " (ms)");
            System.out.println("-------------------------------------------------");
            System.out.println("1 - result: " + cpuInfo.getCpuUsage());
            System.out.println("2 - result: " + cpuUsage);
        }
    }

}
