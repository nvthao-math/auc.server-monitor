/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Map;
import org.auc.core.model.RedisInfo;
import org.auc.core.redis.utils.RedisExecutor;

/**
 *
 * @author thaonguyen
 */
public class RedisExecuter {

    public static Gson GSON = new Gson();

    public static void main(String[] args) {
        RedisInfo info = new RedisInfo("localhost", 6379);
        Map<String, String> result = (new RedisExecutor<Map>(info) {
            @Override
            public Map builds() {
                Map<String, String> result = jedis.hgetAll("abx_abs");
                return result;
            }
        }).run();
        System.out.println(GSON.toJson(result));
        Map<String, String> result1 = (new RedisExecutor<Map>(info) {
            @Override
            public Map builds() {
                Map<String, String> result = jedis.hgetAll("abx_abs");
                return result;
            }
        }).run();
    }

}
