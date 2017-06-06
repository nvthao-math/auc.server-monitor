/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.core.file.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import org.auc.monitor.cluster.configs.RedisConfigs;
import org.auc.core.hardware.utils.HardWareMonitor;
import org.auc.core.model.FileMonitorModel;
import org.auc.core.model.ReaderInfo;
import org.auc.core.model.ReaderOffsetModel;
import org.auc.core.redis.utils.RedisExecutor;
import org.auc.core.utils.CommonUtils;

/**
 *
 * @author thaonguyen
 */
public class ReaderExecutor {

    private static final String TAG = ReaderExecutor.class.getSimpleName();

    /**
     * use this method so as to read file off-line(batch reading)
     *
     * @param fileName - path of file
     * @param header - true: read header, false: remove header if file contain
     * @return - list String of file read
     *
     * example usage: List<String> result = ReaderExecutor.read("/data/sp.csv");
     */
    public static List<String> read(String fileName, boolean header) {
        List<String> result = new ArrayList<>();
        InputStream is = null;
        BufferedReader br = null;
        try {
            is = new FileInputStream(fileName);
            br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = br.readLine()) != null) {
                result.add(line);
            }
            if (header == false) {
                result.remove(0);
            }
        } catch (Exception ex) {
            Logger.error(TAG, ex);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException ex) {
                Logger.error(TAG, ex);
            }
        }
        return result;
    }

    /**
     *
     * @param fileName
     * @param header
     * @return
     */
    public static ReaderOffsetModel readGetQueue(String fileName, boolean header) {
        ReaderOffsetModel result = null;
        Queue<String> data = new LinkedList<>();
        InputStream is = null;
        BufferedReader br = null;
        // get file info on Redis
        FileMonitorModel model = (new RedisExecutor<FileMonitorModel>(RedisConfigs.INFO) {
            @Override
            public FileMonitorModel builds() {
                FileMonitorModel model = new FileMonitorModel(fileName);
                Map<String, String> fileModel = jedis.hgetAll(fileName);
                if (fileModel == null || fileModel.isEmpty()) {
                    return model;
                }
                model = CommonUtils.mapToInstance(fileModel, FileMonitorModel.class);
                return model;
            }
        }).run();
        try {
            is = new FileInputStream(fileName);
            is.skip(model.getOffset());
            br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            int count = 0;
            int markedOffset = 0;
            int stringLen = 0;
            while ((line = br.readLine()) != null) {
                data.add(line);
                markedOffset += (line.getBytes().length + 1);
                stringLen += (line.length() + 1);
                count++;
                if (count % 10000 == 0) {
                    if (!HardWareMonitor.isMemorySafe(0.6)) {
                        // check header is read
                        if (model.getOffset() == 0 && header == false) {
                            data.poll();
                        }
                        // file info
                        model.increaseOffset(markedOffset);
                        model.increaseLineRead(count);
                        model.increaseNumOfCharacter(stringLen);
                        (new RedisExecutor<Boolean>(RedisConfigs.INFO) {
                            @Override
                            public Boolean builds() {
                                Map<String, String> mapPersit = CommonUtils.instanceToMap(model);
                                System.out.println(mapPersit);
                                jedis.hmset(fileName, mapPersit);
                                jedis.expire(fileName, 40000);
                                return true;
                            }
                        }).run();
                        // return result
                        result = new ReaderOffsetModel(data, true);
                        Logger.info(TAG, "Memory info: " + HardWareMonitor.getInfo());
                        Logger.info(TAG, "Number of data: " + result.dataSize() + " and is continue: " + result.isIsContinue());
                        return result;
                    }
                }
            }
            if (header == false) {
                data.poll();
            }
            // cache file's information redis
            model.increaseOffset(markedOffset);
            model.increaseLineRead(count);
            model.increaseNumOfCharacter(stringLen);
            (new RedisExecutor<Boolean>(RedisConfigs.INFO) {
                @Override
                public Boolean builds() {
                    Map<String, String> mapPersit = CommonUtils.instanceToMap(model);
                    jedis.hmset(fileName, mapPersit);
                    return true;
                }
            }).run();
            result = new ReaderOffsetModel(data, false);
        } catch (Exception ex) {
            Logger.error(TAG, ex);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException ex) {
                Logger.error(TAG, ex);
            }
        }
        return result;
    }

    /**
     * use this method so as to read file real-time, cache file's info on redis
     *
     * @param info - info of file need to be read include: path, offset
     * @return - list of String read
     *
     * usage example:
     *
     * String fileName = "data/sp.csv";
     *
     * int offset = 0; ReaderInfo info = new ReaderInfo(fileName, offset);
     *
     * List<String> fio = ReaderExecutor.read(info);
     */
    public static List<String> read(ReaderInfo readerInfo) {
        List<String> result = new ArrayList<>();
        InputStream is = null;
        BufferedReader br = null;
        try {
            is = new FileInputStream(readerInfo.getFileName());
            int available = is.available();
            String toread = Integer.toString(available - readerInfo.getOffset());
            is.skip(readerInfo.getOffset());
            br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = br.readLine()) != null) {
                result.add(line);
            }
            // cache file info to redis
            (new RedisExecutor<Boolean>(RedisConfigs.INFO) {
                @Override
                public Boolean builds() {
                    String key = readerInfo.getFileName();
                    String offset = jedis.get(key);
                    if (null == offset) {
                        jedis.set(key, toread);
                    } else {
                        jedis.incrBy(key, Integer.parseInt(toread));
                    }
                    return true;
                }
            }).run();
        } catch (Exception ex) {
            Logger.error(TAG, ex);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException ex) {
                Logger.error(TAG, ex);
            }
        }
        return result;
    }

}
