/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 *
 * @author thaonv
 */
public class TestMap {

    public static void main(String[] args) {
        for (int i = 1; i < 10; i++) {
            Map<String, Double> hashMap = new HashMap<>();
            Map<String, Double> treeMap = new TreeMap<>();
            hashMap.put("10", 1d);
            hashMap.put("01", 1d);
            hashMap.put("21", 1d);
            //
            treeMap.put("10", 1d);
            treeMap.put("01", 1d);
            treeMap.put("21", 4d);
            //
            System.out.println(hashMap);
            System.out.println(treeMap);
            System.out.println(treeMap.values().toArray()[treeMap.size() - 1]);
        }

    }

}
