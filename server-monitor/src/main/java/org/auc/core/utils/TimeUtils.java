/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.auc.core.file.utils.Logger;

/**
 *
 * @author thaonguyen
 */
public class TimeUtils {

    private static final String TAG = TimeUtils.class.getSimpleName();
    public static final SimpleDateFormat yyyy_MM_dd_HH = new SimpleDateFormat("yyyy-MM-dd-HH");
    public static final SimpleDateFormat yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat yyyyMMddHH = new SimpleDateFormat("yyyy/MM/dd/HH");
    public static final SimpleDateFormat yyyyMMdd_HHmmss = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    public static final SimpleDateFormat yyyy_MM_dd_HHmmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String toString(Date date, SimpleDateFormat formatter) {
        return formatter.format(date);
    }

    public static String toString(Date date) {
        return yyyy_MM_dd_HH.format(date);
    }

    public static Date toTime(String str, SimpleDateFormat formatter) throws ParseException {
        return formatter.parse(str);
    }

    public static Date toTime(String str) throws ParseException {
        return yyyy_MM_dd_HH.parse(str);
    }

    public static String getHour(Date date) {
        String hour = null;
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            hour = NumberUtils.leadingZeroFill(calendar.get(Calendar.HOUR_OF_DAY), 2);
        } catch (Exception ex) {
            Logger.error(TAG, ex);
        }
        return hour;
    }

    public static String asPath(String time) {
        String path = null;
        try {
            Date date = TimeUtils.toTime(time);
            path = new StringBuilder("day=")
                    .append(TimeUtils.toString(date, TimeUtils.yyyy_MM_dd))
                    .append("/hour=").append(TimeUtils.getHour(date))
                    .toString();
        } catch (Exception ex) {
            Logger.error(TAG, ex);
        }
        return path;
    }

}
