/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.core.jdbc.hakiri.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author thaonv
 * @param <T>
 */
public abstract class HakiriExecutor<T> {

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/wte_logcentral_stats?serverTimezone=Asia/Ho_Chi_Minh";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static final Boolean IS_CACHE_STMS = true;
    private static final Integer CACHE_SIZE_STMS = 250;
    private static final Integer CACHE_LIMIT_STMS = 2048;
    private static HikariConfig HAKIRI_CONF;

    // attributes
    private HikariDataSource hds;
    private String prepareStatement;
    private Connection con;
    private PreparedStatement pstm;
    protected ResultSet rs;

    // constructor
    public HakiriExecutor() {
        super();
    }

    public HakiriExecutor(String statement) {
        this.prepareStatement = statement;
    }

    private static HikariConfig initHikariConfig() {
        if (HAKIRI_CONF == null) {
            HikariConfig hikariConf = new HikariConfig();
            hikariConf.setDriverClassName(JDBC_DRIVER);
            hikariConf.setJdbcUrl(JDBC_URL);
            hikariConf.setUsername(USER);
            hikariConf.setPassword(PASSWORD);
            hikariConf.addDataSourceProperty("cachePrepStmts", IS_CACHE_STMS);
            hikariConf.addDataSourceProperty("prepStmtCacheSize", CACHE_SIZE_STMS);
            hikariConf.addDataSourceProperty("prepStmtCacheSqlLimit", CACHE_LIMIT_STMS);
            HAKIRI_CONF = new HikariDataSource(hikariConf);
        }
        return HAKIRI_CONF;
    }

    protected T run() {
        T result = null;
        try {
            this.hds = new HikariDataSource(initHikariConfig());
            con = this.hds.getConnection();
            pstm = con.prepareStatement(this.prepareStatement);
            rs = pstm.executeQuery();
            // manual code here
            result = build();
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            try {
                if (this.rs != null) {
                    this.rs.close();
                }
                if (this.pstm != null) {
                    this.pstm.close();
                }
                if (con != null) {
                    con.close();
                }
                this.hds.close();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
        return result;
    }

    protected abstract T build() throws Exception;

}
