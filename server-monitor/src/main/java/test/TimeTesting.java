/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.Date;

/**
 *
 * @author thaonv
 */
public class TimeTesting {

    public static void main(String[] args) {
        String time = "1487638803000";
        long logtime = Long.parseLong(time);
        Date date = new Date(logtime);
        System.out.println(date);
        //
        String log = "0h86004	0	1487638803	2017-02-21 08:00:03	stt	3593106338	http://www.24h.com.vn/bong-da/sutton-arsenal-kien-cuong-chang-david-c48a855449.html	http://www.24h.com.vn/bong-da/sutton-arsenal-kien-cuong-chang-david-c48a855449.html	1600002661	529488170		532386097	529490688	583206672	583206667	0	2	0.0	0.0	0.0	0.0	9	1		Windows 7	Chrome	56	General_Desktop	Other	113.175.134.44	24	vn	1	0	0	-	0	-	5089003215060446	-	3593106338b1487638803	0	0	{\"M_TP\":[{\"id\": -1,\"score\": -1}]}	{\"C_TP\":[{\"id\": -1,\"score\": -1}]}	{\"M_IT\":[{\"id\": -1,\"score\": -1}]}	{\"M_IM_IT\":[{\"id\": -1,\"score\": -1}]}	{\"C_IT\":[{\"id\": -1,\"score\": -1}]}	{\"C_IM_IT\":[{\"id\": -1,\"score\": -1}]}	22100	1	2	1	37058	0	1	1	{\"M_RM\":[{\"id\": -1,\"score\": -1}]}	range4	1000	39";
        String[] rowArr = log.split("\t");
        System.out.println(rowArr[0]);
//        double spentOrCost = StringUtil.safeParseDouble(rowArr[17]);
//        double spentCost = StringUtil.safeParseDouble(rowArr[18]);
//        double spentBuyCost = StringUtil.safeParseDouble(rowArr[19]);
//        double secondPrice = StringUtil.safeParseDouble(rowArr[20]);
        System.out.println(rowArr[30]);
        System.out.println(rowArr[31]);
        System.out.println(rowArr[49]);
    }

}
