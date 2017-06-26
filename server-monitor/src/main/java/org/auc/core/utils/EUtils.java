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
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.auc.core.file.utils.Logger;
import scala.Tuple2;
import test.User;

/**
 *
 * @author thaonguyen
 */
public class EUtils {

    public static final String TAG = EUtils.class.getSimpleName();
    public static final String NUMBER_PATTERN = "\\d*\\.?\\d+";
    public static Gson GSON = new Gson();
    public static Type TYPE_STRING_STRING = new TypeToken<Map<String, String>>() {
    }.getType();
    public static final String COMMA = ",";
    public static final String UNDER_SCORE = "_";
    public static final String HYPHEN = "-";
    public static final String COLON = ":";
    public static final String TAB_DELIMITER = "\t";
    public static final String FIELD_DELIMITER = ",";
    public static final String HYPHEN_DELIMITER = "-";
    public static final String NEW_LINE = "\n";

    public static Gson getGson() {
        return GSON;
    }

    public static String toJson(Object obj) {
        return GSON.toJson(obj);
    }

    public static <T1, T2> Map<T1, T2> string2Map(String input, String delimeter, Class<T1> keyClass, Class<T2> valueClass) {
        Map<T1, T2> result = new HashMap<>();
        String[] parts = input.split(delimeter);
        for (String val : parts) {
            String[] fields = val.split("=");
            result.put(keyClass.cast(fields[0]), valueClass.cast(fields[1]));
        }
        return result;
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

    public static Map.Entry firstElement(Map map) throws Exception {
        if (map == null || map.isEmpty()) {
            throw new IllegalArgumentException("Input Map not null or empty");
        }
        Map.Entry firstEntry = (Map.Entry) map.entrySet()
                .iterator()
                .next();
        return firstEntry;
    }

    public static Object firstKey(Map map) throws Exception {
        Map.Entry firstEntry = firstElement(map);
        Object key = firstEntry.getKey();
        return key; //  String value = entry.getValue();
    }

    public static Object firstValue(Map map) throws Exception {
        Map.Entry firstEntry = firstElement(map);
        Object firstValue = firstEntry.getValue();
        return firstValue;
    }

    public static boolean isNumber(String str) {
        boolean result = false;
        if (str != null) {
            result = str.matches(NUMBER_PATTERN);
        }
        return result;
    }

    public static <T1, T2> Iterable<Tuple2<T1, T2>> asIterable(Map<T1, T2> map) {
        Iterable<Tuple2<T1, T2>> result;
        List<Tuple2<T1, T2>> list = new ArrayList<>();
        map.keySet().forEach((key) -> {
            T2 value = map.get(key);
            list.add(new Tuple2(key, value));
        });
        final Iterator<Tuple2<T1, T2>> iterator = list.iterator();
        result = () -> iterator;
        return result;
    }

//    public static <E> Iterable<E> iterable(final Iterator<E> iterator) {
//        if (iterator == null) {
//            throw new NullPointerException();
//        }
//        return new Iterable<E>() {
//            public Iterator<E> iterator() {
//                return iterator;
//            }
//        };
//    }
    public static void main(String[] args) throws Exception {
        Map<String, Integer> map = new HashMap<>();
        map.put("123", 12);
//        map.put("456", 14);
        int val = Collections.max(map.values());
        System.out.println(val);
    }

}
