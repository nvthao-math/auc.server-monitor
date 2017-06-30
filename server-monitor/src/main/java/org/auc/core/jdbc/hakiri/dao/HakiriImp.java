/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.core.jdbc.hakiri.dao;

import java.sql.SQLException;

/**
 *
 * @author thaonv
 */
public class HakiriImp {

    public static void main(String[] args) {
        String statement = "SELECT * FROM Cars";
        HakiriExecutor<String> executor = new HakiriExecutor<String>(statement) {
            @Override
            protected String build() throws SQLException {
                StringBuilder sb = new StringBuilder();
                while (this.rs.next()) {
                    sb.append(String.format("%d %s %d", this.rs.getInt(1), this.rs.getString(2), this.rs.getInt(3)))
                            .append("\n");
                }
                return sb.toString();
            }
        };
        String result = executor.run();
        System.out.println(result);
    }

}
