/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.file;

import org.auc.core.file.utils.Logger;
import org.auc.core.file.utils.WriterExecutor;

/**
 *
 * @author thaonguyen
 */
public class WriteEx {
    
    public static final String TAG = WriteEx.class.getSimpleName();
    public static final String PATH_TEST = "/home/thaonguyen/sand-box/data/speed-profiles/test01.csv";
    
    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {
            (new WriterExecutor(PATH_TEST, true) {
                @Override
                public void builds() throws Exception {
                    for (int i = 0; i <= 10; i++) {
                        String str = i + " - new line";
                        bufferedWriter.write(str);
                        bufferedWriter.write("\n");
                    }
                }
            }).run();
            try {
                Thread.sleep(2000l);
            } catch (Exception ex) {
                Logger.error(TAG, ex);
            }
        }
    }
    
}
