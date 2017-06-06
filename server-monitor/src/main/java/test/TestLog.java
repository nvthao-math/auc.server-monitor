/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.auc.core.file.utils.QueueLog;
import org.auc.core.file.utils.Logger;
import org.auc.core.model.WriterInfo;

/**
 *
 * @author thaonguyen
 */
public class TestLog {

    public static String TAG = TestLog.class.getSimpleName();

    public static void main(String[] args) {
//        for (int j = 0; j < 4; j++) {
        int loop = 80; // 800000
        for (int i = 0; i <= loop; i++) {
            Logger.info(TAG, i + ":: info");
//                try {
//                    Thread.sleep(200l);
//                } catch (Exception ex) {
//                }
        }
//        }

        //
        for (int i = 0; i <= loop; i++) {
            Exception ex = new Exception(i + ", Exception");
            Logger.error(TAG, ex);
        }
    }

}
