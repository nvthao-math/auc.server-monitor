/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.HashMap;
import java.util.Map;
import org.auc.monitor.cluster.configs.RedisConfigs;
import org.auc.core.redis.utils.RedisExecutor;

/**
 *
 * @author inet
 */
public class HmsetRedis {

    public static void main(String[] args) {
        User user = new User();
        user.setUsername("Ben");
        user.setEmail("khongbich@gmail.com");
        user.setPassword("1234");
        user.setFirstName("Nguyen");
        user.setLastName("Thanh");
        Map<String, String> putMap = new HashMap<>();
        //
        putMap.put("UserName", user.getUsername());
        putMap.put("email", user.getEmail());
        putMap.put("password", user.getPassword());
        (new RedisExecutor<Boolean>(RedisConfigs.INFO) {
            @Override
            public Boolean builds() {
                jedis.hmset("xyz", putMap);
                return true;
            }
        }).run();
    }

}
