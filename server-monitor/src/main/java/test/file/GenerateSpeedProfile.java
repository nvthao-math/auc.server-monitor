/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.file;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.auc.core.file.utils.Logger;
import org.auc.core.file.utils.WriterExecutor;
import org.auc.gps.config.LogConfig;

/**
 *
 * @author thaonv
 */
public class GenerateSpeedProfile {

    private static final String TAG = GenerateSpeedProfile.class.getSimpleName();
    private static final String OUT = "/home/cpu10869-local/workspace/data/speed-profiles/day=2017-03-22/hour=05/sp.log";

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        int count = 0;
        InputStream is = null;
        BufferedReader br = null;
        try {
            is = new FileInputStream(LogConfig.SPEED_LOG);
            br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = br.readLine()) != null) {
                count++;
                list.add(line);
                if (count % 10000 == 0) {
                    (new WriterExecutor(OUT, true) {
                        @Override
                        public void builds() throws Exception {
                            for (int i = 0; i < list.size(); i++) {
                                this.bufferedWriter.write(list.get(i));
                                this.bufferedWriter.write("\n");
                            }
                        }
                    }).run();
                    list.clear();
                } else if (count == 2000002) {
                    (new WriterExecutor(OUT, true) {
                        @Override
                        public void builds() throws Exception {
                            for (int i = 0; i < list.size(); i++) {
                                this.bufferedWriter.write(list.get(i));
                                this.bufferedWriter.write("\n");
                            }
                        }
                    }).run();
                    break;
                } else {
                    // ignore
                }
            }
        } catch (Exception ex) {
            Logger.error(TAG, ex);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException ex) {
                Logger.error(TAG, ex);
            }
        }
    }

}
