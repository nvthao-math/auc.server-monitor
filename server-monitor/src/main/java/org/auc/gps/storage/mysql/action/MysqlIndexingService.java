/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.gps.storage.mysql.action;

import org.auc.core.file.utils.Logger;
import org.auc.core.jdbc.hakiri.dao.HakiriExecutor;

/**
 *
 * @author thaonv
 */
public class MysqlIndexingService {

    private static final String SP_TABLE = "speed_profiles";
    private static final String TAG = MysqlIndexingService.class.getSimpleName();

    private void createTable() {
        try {
            String queryDBExist = "CREATE DATABASE IF NOT EXISTS data_center";
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
