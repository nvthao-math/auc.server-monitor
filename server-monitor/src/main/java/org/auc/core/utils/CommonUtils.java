/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.core.utils;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.auc.core.file.utils.Logger;
import test.file.Staff;

/**
 *
 * @author thaonguyen
 */
public class CommonUtils {

    public static final String TAG = CommonUtils.class.getSimpleName();
    public static Gson GSON = new Gson();
    public static Type TYPE_STRING_STRING = new TypeToken<Map<String, String>>() {
    }.getType();

    public static Gson getGson() {
        return GSON;
    }

    public static String toJson(Object obj) {
        return GSON.toJson(obj);
    }

    public static void main(String[] args) {

//        System.out.println("=====================");
//        String staffJson = "[{\"name\":\"mkyong\"}, {\"name\":\"laplap\"}]";
//        List<Staff> staffList = new ArrayList<>();
//        Type staffType = new TypeToken<List<Staff>>() {
//        }.getType();
//        fromJson(staffJson, staffType, staffList);
////        System.out.println(staffList);
//        for (Staff staff : staffList) {
////            Staff stt = (Staff) staff;
//            System.out.println(staff.getName());
//        }
        //
//        Map<String, Staff> mapStt = new HashMap<>();
//        mapStt.put("01", new Staff("mkyong"));
//        mapStt.put("02", new Staff("john"));
//        mapStt.put("03", new Staff("lanh"));
//        System.out.println(CommonUtils.toJson(mapStt));
////        //
//        String jsonMap = "{\"01\":{\"name\":\"mkyong\"},\"02\":{\"name\":\"john\"},\"03\":{\"name\":\"lanh\"}}";
//        Map<String, Staff> map = new HashMap<>();
//        Type type = new TypeToken<Map<String, Staff>>() {
//        }.getType();
//        fromJson(jsonMap, type, map);
//        System.out.println(GSON.toJson(map));
    }

    public static <T> T fromJson(String json, Class<T> tclass) {
        T result = null;
        try {
            result = GSON.fromJson(json, tclass);
        } catch (Exception ex) {
            Logger.error(TAG, ex);
        }
        return result;
    }

    //  Type type = new TypeToken<List<Staff>>() {}.getType();
    //  Type type = new TypeToken<Map<String, Staff>>() {}.getType();
    //  fromJson(json, type)
    public static void fromJson(String json, Type typeToken, Object result) {
        try {
            Object buffer = GSON.fromJson(json, typeToken);
            if (result instanceof List) {
                List arrBuffer = (List) buffer;
                for (int i = 0; i < arrBuffer.size(); i++) {
                    ((List) result).add(arrBuffer.get(i));
                }
            } else if (result instanceof Map) {
                Map mapBuffer = (Map) buffer;
                ((Map) result).putAll(mapBuffer);
            } else {
                Logger.error(TAG, "fromJson() function cannot support this datatype: " + result.getClass());
            }
        } catch (Exception ex) {
            Logger.error(TAG, ex);
        }
    }

    public static <T> T mapToInstance(Map<String, String> map, Class<T> tclass) {
        T object = null;
        try {
            String str = GSON.toJson(map);
            object = GSON.fromJson(str, tclass);
        } catch (Exception ex) {
            Logger.error(TAG, ex);
        }
        return object;
    }

    public static Map<String, String> instanceToMap(Object object) {
        Map<String, String> result = null;
        try {
            String str = GSON.toJson(object);
            result = GSON.fromJson(str, TYPE_STRING_STRING);
        } catch (Exception ex) {
            Logger.error(TAG, ex);
        }
        return result;
    }

}
