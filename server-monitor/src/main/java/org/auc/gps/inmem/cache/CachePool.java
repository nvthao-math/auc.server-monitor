/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.gps.inmem.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.util.concurrent.TimeUnit;
import org.auc.core.file.utils.Logger;
import org.auc.core.utils.BashExecutor;

/**
 *
 * @author inet
 */
public class CachePool {

    public static final String TAG = CachePool.class.getSimpleName();
    public static final String COMMAND_PREFIX = "wc -l ";

    // cache pool
    public static LoadingCache<String, Integer> FILE_MONITOR_POOL
            = CacheBuilder.newBuilder()
                    .maximumSize(1000)
                    .expireAfterAccess(10, TimeUnit.MINUTES)
                    .build(new CacheLoader<String, Integer>() {
                        @Override
                        public Integer load(String fileName) throws Exception {
                            //make the expensive call
                            return getLine(fileName);
                        }
                    });

    // List of function
    public static synchronized int getLine(String fileName) {
        int numberLine = 0;
        try {
            String command = COMMAND_PREFIX + fileName;
            numberLine = (new BashExecutor<Integer>(command) {
                @Override
                protected Integer build() {
                    String str = this.resultSet.toString();
                    String[] parts = str.split(" ");
                    return Integer.parseInt(parts[0]);
                }
            }).run();
        } catch (Exception ex) {
            Logger.error(TAG, ex);
        }
        return numberLine;
    }

}
