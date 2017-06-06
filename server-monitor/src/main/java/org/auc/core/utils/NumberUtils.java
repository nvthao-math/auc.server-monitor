/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.core.utils;

import java.lang.reflect.Constructor;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.concurrent.ConcurrentHashMap;
import org.auc.core.file.utils.Logger;

/**
 *
 * @author thaonguyen
 */
public class NumberUtils {

    private static final String TAG = NumberUtils.class.getSimpleName();
    public static ConcurrentHashMap<Integer, NumberFormat> FLOATING_POINT_DATA = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<Integer, NumberFormat> LEADING_DATA = new ConcurrentHashMap<>();

    static {
        NumberFormat zero_formatter = new DecimalFormat("#0");
        FLOATING_POINT_DATA.put(0, zero_formatter);
        NumberFormat one_formatter = new DecimalFormat("0");
        LEADING_DATA.put(1, one_formatter);
    }

    public static int safeParseInteger(String number) {
        return Integer.parseInt(StringUtils.removeTwoSideSpaces(number));
    }

    public static double safeParseDouble(String number) {
        return Double.parseDouble(StringUtils.removeTwoSideSpaces(number));
    }

    public static <T extends Number> T setFloatingPointNumber(T x, int floating_number, Class<T> tclazz) throws Exception {
        NumberFormat formatter = FLOATING_POINT_DATA.get(floating_number);
        if (null == formatter) {
            StringBuffer sbuff = new StringBuffer("#0.");
            for (int i = 0; i < floating_number; i++) {
                sbuff.append("0");
            }
            formatter = new DecimalFormat(sbuff.toString());
            FLOATING_POINT_DATA.put(floating_number, formatter);
        }
        String number = formatter.format(x);
        Constructor<T> constructor = tclazz.getConstructor(String.class);
        T result = constructor.newInstance(number);
        return result;
    }

    public static <T extends Number> String toStringWithFloatingPoint(T x, int floating_number, Class<T> tclazz) {
        NumberFormat formatter = FLOATING_POINT_DATA.get(floating_number);
        if (null == formatter) {
            StringBuffer sbuff = new StringBuffer("#0.");
            for (int i = 0; i < floating_number; i++) {
                sbuff.append("0");
            }
            formatter = new DecimalFormat(sbuff.toString());
            FLOATING_POINT_DATA.put(floating_number, formatter);
        }
        String number = formatter.format(x);
        return number;
    }

    public static String leadingZeroFill(long x, int number) {
        String result = null;
        try {
            if (number > 0) {
                NumberFormat formatter = LEADING_DATA.get(number);
                if (null == formatter) {
                    StringBuffer pattern = new StringBuffer();
                    for (int i = 0; i < number; i++) {
                        pattern.append("0");
                    }
                    formatter = new DecimalFormat(pattern.toString());
                }
                result = formatter.format(x);
            } else {
                Logger.error(TAG, "Can not fill with number of leading less than zero.");
                result = Long.toString(x);
            }
        } catch (Exception ex) {
            Logger.error(TAG, ex);
        }
        return result;
    }

}
