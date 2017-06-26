/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.gps.speed.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author thaonv
 */
public class SpeedSchema {

    // ID,Arc_id,SubClass,Length,Speed_Max,Speed,Year,Month,Day,Hour,DayOfWeek,DayNameOfWeek
    public static final int USER_ID = 0;
    public static final int ARC_ID = 1;
    public static final int SUB_CLASS = 2;
    public static final int LENGTH = 3;
    public static final int SPEED_MAX = 4;
    public static final int SPEED = 5;
    public static final int YEAR = 6;
    public static final int MONTH = 7;
    public static final int DAY = 8;
    public static final int HOUR = 9;
    public static final int DAY_OF_WEEK = 10;
    public static final int DAY_NAME_OF_WEEK = 11;
    public static final int BATCH = 60000;
    //  production configuration
    public static final int SPEED_LOWER = 1;
    public static final int SPEED_UPPER = 110;
    public static final int TIME_ACTIVE_LOWER = 07;
    public static final int TIME_ACTIVE_UPPER = 21;
    public static final double SPEED_DEFAULT = 0.0;
    // data 
    public static final List<Double> SPECIAL_TIME = new ArrayList<>();
    public static final List<Double> NORMAL_TIME = new ArrayList<>();

    static {
        _initialize();
    }

    private static void _initialize() {
        // special time
        for (double i = 0; i < 7; i++) {
            SPECIAL_TIME.add(i);
        }
        SPECIAL_TIME.add(22d);
        SPECIAL_TIME.add(23d);
        // normal time
        for (double i = 7; i < 22; i++) {
            NORMAL_TIME.add(i);
        }
    }

}
