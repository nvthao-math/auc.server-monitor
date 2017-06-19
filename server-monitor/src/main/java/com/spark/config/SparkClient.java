/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spark.config;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SQLContext;

/**
 *
 * @author thaonv
 */
public class SparkClient {

    private static final String MASTER = "local[*]";
    private static final String APP_NAME = "spark-default-app";
    private static SparkConf sConf;
    protected static JavaSparkContext _jsc;
    protected static SQLContext _sqlc;

    static {
        _initialize();
    }

    public SparkClient() {
        super();
    }

    public static void _initialize() {
        sConf = new SparkConf()
                .setAppName(APP_NAME)
                .setMaster(MASTER);
        _jsc = new JavaSparkContext(sConf);
        _sqlc = new SQLContext(_jsc);
    }

}
