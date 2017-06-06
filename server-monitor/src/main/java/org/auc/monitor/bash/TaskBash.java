/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.monitor.bash;

/**
 *
 * @author thaonguyen
 */
public class TaskBash {

    // ps -eo nlwp | tail -n +2 | awk '{ num_threads += $1 } END { echo num_threads }'
    public static final String THREADS = "ps -eo nlwp";

    // ps aux | wc -l
    public static final String PROCESS = "ps aux";

}
