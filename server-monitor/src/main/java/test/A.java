/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.auc.core.file.utils.WriterExecutor;

/**
 *
 * @author thaonguyen
 */
public class A {

    public static final String PATH_TEST = "/media/inet/Thanh Huong/workspace/data/speed-profiles/speed_profile_data_from_20130528_To_20150319.csv";
//    public static final String PATH_TEST = "/home/thaonguyen/sand-box/data/speed-profiles/test01.csv";
//    public static final String PATH_TEST = "/home/thaonguyen/sand-box/data/speed-profiles/speed_profile_data_from_20130528_To_20150319.csv";
    public static final String OUT = "/media/inet/Thanh Huong/workspace/data/speed-profiles/speed-profiles-20130528-to-20150319.csv";

    public static void main(String[] args) {
        streamWrite();
//        streamWithOffset();
    }

    public static void streamWrite() {
        List<String> list = new ArrayList<>();
        int count = 0;
        InputStream is = null;
        BufferedReader br = null;
        try {
            is = new FileInputStream(PATH_TEST);
            br = new BufferedReader(new InputStreamReader(is));
            String s = null;
            while ((s = br.readLine()) != null) {
                count++;
                list.add(s);
//                System.out.println(s); break;
                if (count % 10000 == 0) {
                    (new WriterExecutor(OUT, true) {
                        @Override
                        public void builds() throws Exception {
                            for (String str : list) {
                                bufferedWriter.write(str);
                                bufferedWriter.write("\n");
                            }
                        }
                    }).run();
                    list.clear();
                }
            }
            if (!list.isEmpty()) {
                (new WriterExecutor(OUT, true) {
                    @Override
                    public void builds() throws Exception {
                        for (String str : list) {
                            bufferedWriter.write(str);
                            bufferedWriter.write("\n");
                        }
                    }
                }).run();
            }
            br.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void streamWithOffset() {
        List<String> list = new ArrayList<>();
        InputStream is = null;
        BufferedReader br = null;
        int count = 0;
        try {
            is = new FileInputStream(PATH_TEST);
            int avai = is.available();
            System.out.println("bytes available: " + avai);
            int off = 0; // offset to read file
            is.skip(off);
            br = new BufferedReader(new InputStreamReader(is));
            String s = null;
            int markedOffset = 0;
            int stringLen = 0;
            while ((s = br.readLine()) != null) {
//                i++;
                System.out.println(s);
                list.add(s);
                int readLen = s.getBytes().length + 1;
                markedOffset += readLen;
                System.out.println("read: " + readLen);
                System.out.println("String length: " + (s.length() + 1));
                stringLen += (s.length() + 1);
//                System.out.println("avai: " + is.available());
                count++;
                if (count == 10) {
                    break;
                }
            }
            System.out.println("marked offset: " + markedOffset);
            System.out.println("String has read: " + stringLen);
            br.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("total records: " + count);
    }

}
