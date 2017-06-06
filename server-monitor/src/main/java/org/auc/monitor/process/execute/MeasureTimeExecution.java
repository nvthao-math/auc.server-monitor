/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.monitor.process.execute;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.auc.core.utils.CommonUtils;
import org.auc.monitor.dao.ProcessDao;

/**
 *
 * @author thaonv
 */
public class MeasureTimeExecution {

    private static final String TAG = MeasureTimeExecution.class.getSimpleName();
    private static final int POOL_SIZE = 3;

    public static void main(String[] args) {
        Queue<ProcessDao> queue = getQueue();
        List<ProcessDao> runningPool = getPoolRunning();
        Long timeConsuming = 0l;
        while (!queue.isEmpty()) {
            System.out.println(CommonUtils.toJson(runningPool));
            List<ProcessDao> successProcess = getSuccess(runningPool, timeConsuming);
            timeConsuming += successProcess.get(0).getMeanTime();
            processTranfer(queue, runningPool);
            System.out.println("while: " + timeConsuming);
        }
        long maxTime = getMaxTimeProcess(runningPool);
        System.out.println("max: " + maxTime);
        timeConsuming += maxTime;
        System.out.println("TIME of list process for execution: " + timeConsuming + " (ms)");
    }

    public static long getMaxTimeProcess(List<ProcessDao> runningPool) {
        long max = runningPool.get(0).getMeanTime();
        for (ProcessDao val : runningPool) {
            if (val.getMeanTime() > max) {
                max = val.getMeanTime();
            }
        }
        return max;
    }

    public static void processTranfer(Queue<ProcessDao> queue, List<ProcessDao> runningPool) {
        while (true) {
            if (runningPool.size() != POOL_SIZE) {
                ProcessDao process = queue.poll();
                runningPool.add(process);
            } else {
                break;
            }
        }
    }

    public static List<ProcessDao> getSuccess(List<ProcessDao> pool, Long timeConsuming) {
        List<ProcessDao> result = new ArrayList<>();
        long min = pool.get(0).getMeanTime();
        for (int i = 1; i < pool.size(); i++) {
            long val = pool.get(i).getMeanTime();
            if (val <= min) {
                min = val;
            }
        }
        // get list success of process
        for (ProcessDao process : pool) {
            if (process.getMeanTime() == min) {
                result.add(process);
            }
        }
        // time update
        timeConsuming += min;
        pool.removeAll(result);
        for (ProcessDao process : pool) {
            process.decreaseMeanTime(min);
        }
        return result;
    }

    public static List<ProcessDao> getPoolRunning() {
        List<ProcessDao> result = new ArrayList<>();
        ProcessDao task1 = new ProcessDao("t0", 5l);
        result.add(task1);
        ProcessDao task2 = new ProcessDao("ta", 3l);
        result.add(task2);
        ProcessDao task3 = new ProcessDao("tb", 1l);
        result.add(task3);
        return result;
    }

    public static Queue<ProcessDao> getQueue() {
        Queue<ProcessDao> result = new LinkedList<>();
        ProcessDao task1 = new ProcessDao("t1", 3l);
        result.add(task1);
        ProcessDao task2 = new ProcessDao("t2", 1l);
        result.add(task2);
        ProcessDao task3 = new ProcessDao("t3", 3l);
        result.add(task3);
        ProcessDao task4 = new ProcessDao("t4", 2l);
        result.add(task4);
        return result;
    }

}
