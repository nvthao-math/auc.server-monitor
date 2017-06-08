/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.monitor;

import java.util.TimerTask;
import org.auc.monitor.bash.TaskBash;
import org.auc.monitor.dao.TaskInfo;
import org.auc.core.utils.BashExecutor;
import org.auc.core.utils.EUtils;
import org.auc.core.file.utils.Logger;
import org.auc.core.utils.NumberUtils;

/**
 *
 * @author thaonguyen
 */
public class TaskMonitor extends TimerTask {

    public static final String TAG = TaskMonitor.class.getSimpleName();

    public static TaskInfo getTaskInfo() {
        TaskInfo taskInfo = new TaskInfo();
        try {
            Integer nProcess = (new BashExecutor<Integer>(TaskBash.PROCESS) {
                @Override
                protected Integer build() {
                    String[] parts = this.resultSet.toString().split("\t");
                    return parts.length - 1;
                }
            }).run();
            taskInfo.setProcess(nProcess);
            Integer nThreads = (new BashExecutor<Integer>(TaskBash.THREADS) {
                @Override
                protected Integer build() {
                    int threads = 0;
                    String[] parts = this.resultSet.toString().split("\t");
                    for (int i = 1; i < parts.length; i++) {
                        threads += NumberUtils.safeParseInteger(parts[i]);
                    }
                    return threads;
                }
            }).run();
            taskInfo.setThreads(nThreads);
        } catch (Exception ex) {
            Logger.error(TAG, ex);
        }
        return taskInfo;
    }

    @Override
    public void run() {
        TaskInfo taskInfo = getTaskInfo();
        System.out.println(EUtils.toJson(taskInfo));
    }

}
