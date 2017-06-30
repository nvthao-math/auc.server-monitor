/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.core.jdbc.hakiri;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author thaonv
 */
public class HikariCPEx {

    public static void main(String[] args) {
        HikariConfig hikariConf = new HikariConfig();
        hikariConf.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariConf.setJdbcUrl("jdbc:mysql://localhost:3306/wte_logcentral_stats?serverTimezone=Asia/Ho_Chi_Minh");
        hikariConf.setUsername("root");
        hikariConf.setPassword("");
        hikariConf.addDataSourceProperty("cachePrepStmts", true);
        hikariConf.addDataSourceProperty("prepStmtCacheSize", 250);
        hikariConf.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        //

        HikariDataSource ds = new HikariDataSource(hikariConf);

        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            con = ds.getConnection();
            pstm = con.prepareStatement("SELECT * FROM Cars");
            rs = pstm.executeQuery();

            while (rs.next()) {
                System.out.format("%d %s %d %n", rs.getInt(1), rs.getString(2),
                        rs.getInt(3));
            }
        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(HikariCPEx.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {

            try {

                if (rs != null) {
                    rs.close();
                }

                if (pstm != null) {
                    pstm.close();
                }

                if (con != null) {
                    con.close();
                }

                ds.close();

            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
    }

}
