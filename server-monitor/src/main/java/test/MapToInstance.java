/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.HashMap;
import java.util.Map;
import org.auc.monitor.cluster.configs.RedisConfigs;
import org.auc.core.file.utils.Logger;
import org.auc.core.model.FileMonitorModel;
import org.auc.core.redis.utils.RedisExecutor;
import org.auc.core.utils.EUtils;
import org.auc.gps.config.LogConfig;

/**
 *
 * @author inet
 */
public class MapToInstance {

    public static final String TAG = MapToInstance.class.getSimpleName();

    public static void main(String[] args) {
//            private String fileName;
//    private int lineOnDisk;
//    private int lineRead;
//    private int offset;
//    private int numOfCharacter;
//        Map<String, String> map = new HashMap<>();
//        map.put("fileName", "xyz");
//        map.put("lineOnDisk", "123");
//        map.put("lineRead", "234");
//        map.put("offset", "321");
//        map.put("numOfCharacter", "2121");
//        //
////        String str = CommonUtils.toJson(map);
////        FileMonitorModel model = CommonUtils.getGson().fromJson(str, FileMonitorModel.class);
//        FileMonitorModel model = CommonUtils.mapToInstance(map, FileMonitorModel.class);
//        System.out.println("JSON: " + CommonUtils.toJson(model));
//        //
//        Map<String, String> convMap = CommonUtils.instanceToMap(model);
//        System.out.println(convMap);
        //
        //
        FileMonitorModel model = new FileMonitorModel(LogConfig.SPEED_LOG);
        System.out.println(model.getLineOnDisk());
        try {
            Map<String, String> mapPersit = EUtils.instanceToMap(model);
            System.out.println(mapPersit);
            boolean is =  Double.class.isInstance(mapPersit.get("lineOnDisk"));
            System.out.println(is);
            System.out.println(mapPersit.get("lineOnDisk"));
        } catch (Exception ex) {
            Logger.error(TAG, ex);
        }
        //
//        (new RedisExecutor<Boolean>(RedisConfigs.INFO) {
//            @Override
//            public Boolean builds() {
//                try {
//                    Map<String, String> mapPersit = CommonUtils.instanceToMap(model);
//                    System.out.println(mapPersit);
//                    System.out.println(mapPersit.get("lineOnDisk"));
//                    jedis.hmset("xyz", mapPersit);
//                } catch (Exception ex) {
//                    Logger.error(TAG, ex);
//                }
//                return true;
//            }
//        }).run();
    }

}
