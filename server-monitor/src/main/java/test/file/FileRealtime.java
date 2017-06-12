/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.file;

import com.google.gson.Gson;
import java.io.File;
import java.util.List;
import org.auc.monitor.server.cluster.configs.RedisConfigs;
import org.auc.core.model.ReaderInfo;
import org.auc.core.file.utils.Logger;
import org.auc.core.file.utils.ReaderExecutor;
import org.auc.core.redis.utils.RedisExecutor;
import static test.file.WriteEx.TAG;

/**
 *
 * @author thaonguyen
 */
public class FileRealtime {

    public static final String TAG = FileRealtime.class.getSimpleName();
    //    public static final String PATH_TEST = "/home/thaonguyen/sand-box/data/speed-profiles/speed_profile_data_from_20130528_To_20150319.csv";
//    public static final String PATH_TEST = "/home/thaonguyen/sand-box/data/speed-profiles/test_large.csv";
//    public static final String PATH_TEST = "/home/thaonguyen/sand-box/data/speed-profiles/test.csv";
    public static final String PATH_TEST = "/home/thaonguyen/sand-box/data/speed-profiles/test01.csv";
    public static final Gson GSON = new Gson();

    public static void main(String[] args) {
        long t1;
        t1 = System.currentTimeMillis();
        //
        for (int i = 0; i < 6; i++) {
            ReaderInfo info = (new RedisExecutor<ReaderInfo>(RedisConfigs.INFO) {
                @Override
                public ReaderInfo builds() {
                    ReaderInfo readerInfo = null;
                    String offset = jedis.get(PATH_TEST);
                    if (null == offset) {
                        readerInfo = new ReaderInfo(PATH_TEST, 0);
                    } else {
                        readerInfo = new ReaderInfo(PATH_TEST, offset);
                    }
                    return readerInfo;
                }
            }).run();
            List<String> fio = ReaderExecutor.read(info);
            System.out.println("result: " + fio.size());
            System.out.println(GSON.toJson(fio));
            try {
                Thread.sleep(2000l);
            } catch (Exception ex) {
                Logger.error(TAG, ex);
            }
        }
        //
        Logger.info(TAG, "Time: " + (System.currentTimeMillis() - t1) + " (ms)");
        System.gc();
    }

}
