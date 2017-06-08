/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author thaonv
 */
public class TestMap {

    public static void main(String[] args) {
        String input = "jobName=read-speed-profiles,fromTime=2017-03-23-00,endTime=2017-03-23-23,synchTo=elastic_csv_parquet,realtime=false";
        Map<String, String> result = Arrays.stream(input.split(","))
                .map(s -> s.split("="))
                .collect(Collectors.toMap(
                        a -> a[0], //key
                        a -> a[1] //value
                ));
        System.out.println(result);
    }

}
