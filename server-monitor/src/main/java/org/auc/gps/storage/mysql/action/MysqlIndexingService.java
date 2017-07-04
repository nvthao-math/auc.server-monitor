/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.gps.storage.mysql.action;

import java.util.Arrays;
import java.util.List;
import org.auc.core.file.utils.Logger;
import org.auc.core.jdbc.hakiri.dao.HakiriExecutor;

/**
 *
 * @author thaonv
 */
public class MysqlIndexingService {

    private static final String TAG = MysqlIndexingService.class.getSimpleName();

    private void createTable() {
        try {
            String queryDBExist = "CREATE DATABASE IF NOT EXISTS data_center";
            String queryCreateTable = new StringBuilder()
                    .append("CREATE TABLE speed_profiles ")
                    .append("(")
                    .append("no LONG NOT NULL AUTO_INCREMENT,")
                    .append("id LONG NOT NULL,")
                    .append("arcId STRING NOT NULL,")
                    .append("subClass STRING NOT NULL,")
                    .append("length DOUBLE NOT NULL,")
                    .append("speedMax DOUBLE NOT NULL,")
                    .append("speed DOUBLE NOT NULL,")
                    .append("time STRING NOT NULL,")
                    .append("dayOfWeek STRING NOT NULL,")
                    .append("dayNameOfWeek STRING NOT NULL")
                    .toString();
            List<String> queries = Arrays.asList(queryDBExist, queryCreateTable);
            (new HakiriExecutor<Boolean>(queryDBExist) {
                @Override
                protected Boolean build() {
                    try {
                        this.pstm.executeQuery();

                    } catch (Exception ex) {
                        Logger.error(TAG, ex);
                    }

                    return true;
                }
            }).run();

        } catch (Exception ex) {
            Logger.error(TAG, ex);
        }

    }

}
