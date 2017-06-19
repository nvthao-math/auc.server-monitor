/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.auc.core.file.utils.Logger;
import org.auc.core.utils.NumberUtils;
import org.auc.gps.config.LogConfig;
import org.auc.gps.speed.model.SpeedModel;
import org.auc.gps.speed.model.SpeedSchema;

/**
 *
 * @author thaonv
 */
public class TestWithSpark {

    private static final String TAG = TestWithSpark.class.getSimpleName();

    public static void main(String[] args) {
        long t1 = System.currentTimeMillis();
        String fileName = LogConfig.SPEED_LOG;
        int count = read(fileName);
        System.out.println("size: " + count);
        System.out.println("Time consuming: " + (System.currentTimeMillis() - t1) + " (ms)");
    }

    public static int read(String fileName) {
        int count = 0;
        InputStream is = null;
        BufferedReader br = null;
        try {
            is = new FileInputStream(fileName);
            br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = br.readLine()) != null) {
                parseLog(line);
                count++;
            }
        } catch (Exception ex) {
            Logger.error(TAG, ex);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException ex) {
                Logger.error(TAG, ex);
            }
        }
        return count;
    }

    public static SpeedModel parseLog(String record) {
        SpeedModel model = new SpeedModel();
        try {
            String[] parts = record.split(",");
            double speed = Double.parseDouble(parts[SpeedSchema.SPEED]);
            model.setId(parts[SpeedSchema.USER_ID]);
            model.setArcId(parts[SpeedSchema.ARC_ID]);
            model.setSubClass(parts[SpeedSchema.SUB_CLASS]);
            model.setLength(parts[SpeedSchema.LENGTH]);
            model.setSpeedMax(parts[SpeedSchema.SPEED_MAX]);
            model.setSpeed(speed);
            String time = new StringBuilder().append(parts[SpeedSchema.YEAR]).append("-")
                    .append(NumberUtils.leadingZeroFill(Integer.parseInt(parts[SpeedSchema.MONTH]), 2)).append("-")
                    .append(NumberUtils.leadingZeroFill(Integer.parseInt(parts[SpeedSchema.DAY]), 2)).append("-")
                    .append(NumberUtils.leadingZeroFill(Integer.parseInt(parts[SpeedSchema.HOUR]), 2))
                    .toString();
            model.setTime(time);
            model.setDayOfWeek(parts[SpeedSchema.DAY_OF_WEEK]);
            model.setDayNameOfWeek(parts[SpeedSchema.DAY_NAME_OF_WEEK]);
//            System.out.println(parts[0]);
        } catch (Exception ex) {
            Logger.error(TAG, ex);
        }
        return model;
    }

}
