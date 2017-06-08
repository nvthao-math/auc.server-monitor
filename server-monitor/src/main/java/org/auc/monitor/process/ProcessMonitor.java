/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.monitor.process;

import org.auc.core.file.utils.Logger;
import org.auc.core.utils.BashExecutor;
import org.auc.core.utils.EUtils;
import org.auc.monitor.dao.ProcessInfo;

/**
 *
 * @author thaonv
 */
public class ProcessMonitor {

    private static final String TAG = ProcessMonitor.class.getSimpleName();

    public static void main(String[] args) {
        String pCommand = "ps -p 11727 -o %cpu,%mem,time,cmd | tail -1 | awk '{print $1,\"\\t\",$2,\"\\t\",$3,\"\\t\",$4}'";
        (new BashExecutor<Boolean>(pCommand) {
            @Override
            protected Boolean build() {
                try {
                    String[] info = this.resultSet.toString().split(TAB_DELIMITER, -1);
                    ProcessInfo process = new ProcessInfo(Double.parseDouble(info[0].trim()), Double.parseDouble(info[1].trim()), info[2].trim(), info[3].trim());
                    System.out.println(EUtils.toJson(process));
                } catch (Exception ex) {
                    Logger.error(TAG, ex);
                }
                return true;
            }
        }).run();
    }

}
