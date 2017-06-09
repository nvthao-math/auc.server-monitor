/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.core.file.utils;

import org.auc.core.model.WriterInfo;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import org.auc.core.utils.EUtils;
import org.auc.monitor.cluster.configs.ClusterConfigs;
import org.auc.core.utils.ShutdownHook;
import org.auc.core.utils.TimeUtils;

/**
 *
 * @author thaonguyen
 */
public class Logger {

    public static QueueLog LOGGER = new QueueLog();
    public static String PREFIX_INFO = "info";
    public static String PREFIX_ERROR = "error";

    static {
        ShutdownHook.addHook(LOGGER);
    }

    public static void setPrefix(String jobName) {
        PREFIX_INFO = new StringBuilder()
                .append(jobName)
                .append(EUtils.HYPHEN)
                .append(PREFIX_INFO)
                .toString();
        PREFIX_ERROR = new StringBuilder()
                .append(jobName)
                .append(EUtils.HYPHEN)
                .append(PREFIX_ERROR)
                .toString();
    }

    public static void info(String tclass, String message) {
        String fileName = PREFIX_INFO + EUtils.HYPHEN + TimeUtils.toString(new Date()) + ".log";
        String infoPath = ClusterConfigs.DEBUG + "/" + fileName;
        String content = "[" + TimeUtils.toString(new Date(), TimeUtils.yyyy_MM_dd_HHmmss) + "] " + tclass + ", " + message + "\n";
        WriterInfo info = new WriterInfo(infoPath, content);
        System.out.print(content);
        LOGGER.add(info);
    }

    public static void error(String tclass, Exception ex) {
        String fileName = PREFIX_ERROR + EUtils.HYPHEN + TimeUtils.toString(new Date()) + ".log";
        String errorPath = ClusterConfigs.DEBUG + "/" + fileName;
        StringWriter error = new StringWriter();
        ex.printStackTrace(new PrintWriter(error));
        String content = "[" + TimeUtils.toString(new Date(), TimeUtils.yyyy_MM_dd_HHmmss) + "] " + tclass + ", " + error.toString();
        WriterInfo info = new WriterInfo(errorPath, content);
        System.out.print(content);
        LOGGER.add(info);
    }

    public static void error(String tclass, String message) {
        String fileName = PREFIX_ERROR + EUtils.HYPHEN + TimeUtils.toString(new Date()) + ".log";
        String errorPath = ClusterConfigs.DEBUG + "/" + fileName;
        String content = "[" + TimeUtils.toString(new Date(), TimeUtils.yyyy_MM_dd_HHmmss) + "] " + tclass + ", " + message;
        System.out.println(content);
        WriterInfo info = new WriterInfo(errorPath, content);
        LOGGER.add(info);
    }

}
