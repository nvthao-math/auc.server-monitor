/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.data.generate.adlogs;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.auc.core.file.utils.FileInfo;
import org.auc.core.file.utils.Logger;
import org.auc.core.file.utils.ReaderExecutor;
import org.auc.core.utils.EUtils;
import org.auc.core.utils.NetWorkUtils;
import org.auc.core.utils.TimeUtils;
import org.auc.data.generate.adlogs.dao.Click;

/**
 *
 * @author thaonv
 */
public class ClickGenerate {

    private static final String TAG = ClickGenerate.class.getSimpleName();
    private static String CLICK_PATH = "/home/cpu10869-local/workspace/data/Ad-logs/click/day=2017-02-21/hour=08";
    private static Set<String> URL = new HashSet<>();
    private static final String URL_PATH = "data/adlogs/info/click/url.txt";
    private static Set<String> IP = new HashSet<>();
    private static final String IP_PATH = "data/adlogs/info/click/ip.txt";
    private static Set<String> DEVICE = new HashSet<>(); // os	browser		device	
    private static final String DEVICE_PATH = "data/adlogs/info/click/device.txt";
    private static final List<String> TYPE = Arrays.asList(new String[]{"spam", "valid"});
    //
    //
    private static Set<Long> USER_ID = new HashSet<>();
    private static final String USER_ID_PATH = "data/adlogs/info/click/userId.txt";
    private static Set<Long> ZONE_ID = new HashSet<>();
    private static final String ZONE_ID_PATH = "data/adlogs/info/click/zoneId.txt";
    private static Set<Long> CREATIVE_ID = new HashSet<>();
    private static final String CREATIVE_ID_PATH = "data/adlogs/info/click/creativeId.txt";
    private static Set<Long> CAMPAIGN_ID = new HashSet<>();
    private static final String CAMPAIGN_ID_PATH = "data/adlogs/info/click/campaignId.txt";
    private static final String COUNTRY = "vietnam";
    private static List<String> PROVINCE = new ArrayList<>();
    private static final String PROVINCE_PATH = "data/adlogs/info/click/province.txt";
    private static List<String> PRODUCT = new ArrayList<>();
    private static final String PRODUCT_PATH = "data/adlogs/info/click/product.txt";
    private static final List<String> GENDER = Arrays.asList(new String[]{"male", "female", "undefined"});
    private static final Random RND = new Random();

    public static void main(String[] args) {
        String fromTime = "2017-04-02-00";
        String endTime = "2017-04-02-01";
        generate(fromTime, endTime);
//        for (int i = 0; i < 10; i++) {
//            System.out.println(RND.nextInt(10));
//        }
    }

    public static void generate(String fromTime, String endTime) {
        try {
            int count = 0;
            long t1 = System.currentTimeMillis();
            //
            Calendar fromDate = Calendar.getInstance();
            Calendar endDate = Calendar.getInstance();
            fromDate.setTime(TimeUtils.toTime(fromTime));
            endDate.setTime(TimeUtils.toTime(endTime));
            List<Click> clickRecords = new ArrayList<>();
            while (!fromDate.after(endDate)) {
                Click record = new Click();
                record.setLogTime(fromDate.getTime().getTime());
                String time = TimeUtils.toString(fromDate.getTime(), TimeUtils.yyyy_MM_dd_HHmmss);
                record.setTime(time);
                record.setType(getType());
                String url = getUrl();
                record.setUrl(url);
                record.setDomain(NetWorkUtils.getDomainName(url));
                String[] devices = getDevice();
//                os
                record.setOs(devices[0]);
// browser
                record.setBrowser(devices[1]);
// devType
                record.setDevice(devices[2]);
// ip
                record.setIp(getIp());
// province
                record.setProvince(getProvince());
// country
                record.setCountry("vietnam");
// gender
                record.setGender(getGender());
// product
                record.setProduct(getProduct());
// timeToClick
                record.setTimeToClick(getTimeToClick());
// userId
                record.setUserId(getUserId());
// zoneId
                record.setZoneId(getZoneId());
// creativeId
                record.setCreativeId(getCreativeId());
// campaignId
                record.setCampaignId(getCampaignId());
                System.out.println("Time: " + time);
                System.out.println(EUtils.toJson(record));
                // increase time
                fromDate.add(Calendar.SECOND, 1);
                count++;
            }
            long duration = System.currentTimeMillis() - t1;
            Logger.info(TAG, "Time consuming: " + duration + " (ms)");
            System.out.println("Count: " + count);
        } catch (Exception ex) {
            Logger.error(TAG, ex);
        }
    }

    public static long getCampaignId() {
        if (CAMPAIGN_ID.isEmpty()) {
            List<String> zones = ReaderExecutor.read(CAMPAIGN_ID_PATH, true);
            for (String zone : zones) {
                CAMPAIGN_ID.add(Long.parseLong(zone));
            }
        }
        ArrayList<Long> list = new ArrayList<>();
        list.addAll(CAMPAIGN_ID);
        int index = RND.nextInt(CAMPAIGN_ID.size());
        return list.get(index);
    }

    public static long getCreativeId() {
        if (CREATIVE_ID.isEmpty()) {
            List<String> zones = ReaderExecutor.read(CREATIVE_ID_PATH, true);
            for (String zone : zones) {
                CREATIVE_ID.add(Long.parseLong(zone));
            }
        }
        ArrayList<Long> list = new ArrayList<>();
        list.addAll(CREATIVE_ID);
        int index = RND.nextInt(CREATIVE_ID.size());
        return list.get(index);
    }

    public static long getZoneId() {
        if (ZONE_ID.isEmpty()) {
            List<String> zones = ReaderExecutor.read(ZONE_ID_PATH, true);
            for (String zone : zones) {
                ZONE_ID.add(Long.parseLong(zone));
            }
        }
        ArrayList<Long> list = new ArrayList<>();
        list.addAll(ZONE_ID);
        int index = RND.nextInt(ZONE_ID.size());
        return list.get(index);
    }

    public static long getUserId() {
        if (USER_ID.isEmpty()) {
            List<String> users = ReaderExecutor.read(USER_ID_PATH, true);
            for (String user : users) {
                USER_ID.add(Long.parseLong(user));
            }
        }
        List<Long> list = new ArrayList<>();
        list.addAll(USER_ID);
        int index = RND.nextInt(USER_ID.size());
        return list.get(index);
    }

    public static int getTimeToClick() {
        return RND.nextInt(7201);
    }

    public static String getProduct() {
        if (PRODUCT.isEmpty()) {
            PRODUCT = ReaderExecutor.read(PRODUCT_PATH, true);
        }
        int index = RND.nextInt(PRODUCT.size());
        return PRODUCT.get(index);
    }

    public static String getGender() {
        int index = RND.nextInt(GENDER.size());
        return GENDER.get(index);
    }

    public static String getProvince() {
        if (PROVINCE.isEmpty()) {
            PROVINCE = ReaderExecutor.read(PROVINCE_PATH, true);
        }
        int index = RND.nextInt(PROVINCE.size());
        return PROVINCE.get(index);
    }

    public static String getIp() {
        if (IP.isEmpty()) {
            List<String> list = ReaderExecutor.read(IP_PATH, true);
            for (String str : list) {
                IP.add(str);
            }
        }
        int index = RND.nextInt(IP.size());
        List<String> ip = new ArrayList<>();
        ip.addAll(IP);
        return ip.get(index);
    }

    public static String[] getDevice() {
        if (DEVICE.isEmpty()) {
            List<String> list = ReaderExecutor.read(DEVICE_PATH, true);
            for (String str : list) {
                DEVICE.add(str);
            }
        }
        int index = RND.nextInt(DEVICE.size());
        List<String> devices = new ArrayList<>();
        devices.addAll(DEVICE);
        String info = devices.get(index);
        return info.split("\t");
    }

    public static String getUrl() {
        if (URL.isEmpty()) {
            List<String> list = ReaderExecutor.read(URL_PATH, true);
            for (String str : list) {
                URL.add(str);
            }
        }
        int index = RND.nextInt(URL.size());
        List<String> urls = new ArrayList<>();
        urls.addAll(URL);
        return urls.get(index);
    }

    public static String getType() {
        int index = RND.nextInt(2);
        return TYPE.get(index);
    }

    public static void saveInfo() {
        int count = 0;
        File clickFolder = new File(CLICK_PATH);
        File[] files = clickFolder.listFiles();
        for (File file : files) {
            String fileName = file.getAbsolutePath();
            List<String> records = ReaderExecutor.read(fileName, true);
            for (String record : records) {
                try {
                    String[] valArr = record.split("\t");
                    if (valArr.length >= 31) {
                        Click click = new Click();
//                        long time = Long.parseLong(valArr[2]) * 1000;
//                        click.setTime(new Date(time));
                        click.setType(valArr[4]);
                        click.setUserId(Long.parseLong(valArr[5]));
                        USER_ID.add(Long.parseLong(valArr[5]));
                        click.setUrl(valArr[6]);
                        URL.add(valArr[6]);
//                        click.setAdId(Long.parseLong(valArr[8]));
//                        click.setHostname(valArr[10]);
                        click.setZoneId(Long.parseLong(valArr[11]));
                        ZONE_ID.add(Long.parseLong(valArr[11]));
                        click.setCreativeId(Long.parseLong(valArr[13]));
                        CREATIVE_ID.add(Long.parseLong(valArr[13]));
                        click.setCampaignId(Long.parseLong(valArr[14]));
                        CAMPAIGN_ID.add(Long.parseLong(valArr[14]));
                        click.setTimeToClick(Integer.parseInt(valArr[21]));
                        click.setOs(valArr[24]);
                        click.setBrowser(valArr[25]);
//                        click.setDevType(valArr[27]);
//                        click.setDevModel(valArr[28]);
                        click.setIp(valArr[29].trim());
                        IP.add(valArr[29].trim());
//                        click.setProvince(Integer.parseInt(valArr[30]));
                        click.setCountry(valArr[31]);
//                        click.setGender(Integer.parseInt(valArr[32]));
//                        click.setProduct(Long.parseLong(valArr[33]));
                        //
                        System.out.println(EUtils.toJson(click));
                        //
                        StringBuilder device = new StringBuilder();
//                        if ("Other".equals(click.getDevModel())) {
//                            device.append(click.getOs())
//                                    .append("\t")
//                                    .append(click.getBrowser())
//                                    .append("\t")
//                                    .append("Desktop");
//                        } else {
////                            device.append(click.getOs())
////                                    .append("\t")
////                                    .append(click.getBrowser())
////                                    .append("\t")
////                                    .append(click.getDevModel());
//                        }
                        DEVICE.add(device.toString());
                    }
                } catch (Exception ex) {
                    Logger.error(TAG, ex);
                }
            }
            System.out.println(records.size());
            count++;
        }
        System.out.println("Number of file: " + count);
        System.out.println("============ URL ===============");
        System.out.println(EUtils.toJson(URL));
        FileInfo.toStorage(URL, URL_PATH, true);
        System.out.println("============ IP ===============");
        System.out.println(EUtils.toJson(IP));
        FileInfo.toStorage(IP, IP_PATH, true);
        System.out.println("============ DEVICE ===============");
        System.out.println(EUtils.toJson(DEVICE));
        FileInfo.toStorage(DEVICE, DEVICE_PATH, true);
        System.out.println("============ USER_ID ===============");
        System.out.println(EUtils.toJson(USER_ID));
        FileInfo.toStorage(USER_ID, USER_ID_PATH, true);
        System.out.println("============ ZONE_ID ===============");
        System.out.println(EUtils.toJson(ZONE_ID));
        FileInfo.toStorage(ZONE_ID, ZONE_ID_PATH, true);
        System.out.println("============ CREATIVE_ID ===============");
        System.out.println(EUtils.toJson(CREATIVE_ID));
        FileInfo.toStorage(CREATIVE_ID, CREATIVE_ID_PATH, true);
    }

}
