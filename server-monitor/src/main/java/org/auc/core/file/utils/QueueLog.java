/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.core.file.utils;

import org.auc.core.model.WriterInfo;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author thaonguyen
 */
public class QueueLog extends Thread {

    public static final String TAG = QueueLog.class.getSimpleName();
    public static ConcurrentLinkedQueue<WriterInfo> LOG_QUEUE = new ConcurrentLinkedQueue<>();
    public static int PERSIST_SIZE = 600;
    public static int BATCH_SIZE = 200;
    public static int TIME_INGEST = 4 * 1000; // 4 seconds
    public static Timer TIMER = new Timer(true);

    static {
        TIMER.schedule(new TimerTask() {
            @Override
            public void run() {
                persistData();
            }
        }, 0, TIME_INGEST);
    }

    // constructor
    public QueueLog() {
        super();
    }

    public synchronized void add(WriterInfo info) {
        LOG_QUEUE.add(info);
        if (LOG_QUEUE.size() > PERSIST_SIZE) {
            persistData();
        }
    }

    public static synchronized void persistData() {
        try {
            int count = 1;
            int sizeQueue = LOG_QUEUE.size();
            Map<String, StringBuilder> batch = new HashMap<>();
            while (!LOG_QUEUE.isEmpty()) {
                WriterInfo info = LOG_QUEUE.poll();
                String fileName = info.getFileName();
                String content = info.getContent();
                StringBuilder sb = batch.get(fileName);
                if (null == sb) {
                    sb = new StringBuilder();
                    batch.put(fileName, sb);
                }
                sb.append(content);
                if ((count % BATCH_SIZE) == 0 || count >= sizeQueue) {
                    for (String key : batch.keySet()) {
                        (new WriterExecutor(key, true) {
                            @Override
                            public void builds() throws IOException {
                                // write to file
                                this.bufferedWriter.write(batch.get(key).toString());
                            }
                        }).run();
                    }
                    batch.clear();
                }
                count++;
            }
            System.gc();
        } catch (Exception ex) {
            Logger.error(TAG, ex);
        }
    }

    @Override
    public void run() {
        persistData();
    }

}
