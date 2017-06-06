/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.core.utils;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 *
 * @author thaonguyen
 */
public class StringUtils {

    public static String CommandInfo(BufferedReader br) {
        StringBuilder sbuff = new StringBuilder();
        try {
            String line = null;
            while ((line = br.readLine()) != null) {
                sbuff.append(line);
            }
        } catch (Exception ex) {
            StringWriter error = new StringWriter();
            ex.printStackTrace(new PrintWriter(error));
            System.out.println(error.toString());
        }
        return sbuff.toString();
    }

    public static String removeLeadingSpaces(String str) {
        return str.replaceAll("^\\s+", "");
    }

    public static String removeTailingSpaces(String str) {
        return str.replaceAll("\\s+$", "");
    }

    public static String removeTwoSideSpaces(String str) {
        return str.replaceAll("^\\s+|\\s+$", "");
    }

}
