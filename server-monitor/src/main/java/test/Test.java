/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import org.auc.core.file.utils.Logger;

/**
 *
 * @author thaonguyen
 */
public class Test {

//    public static final String PATH_TEST = "/home/thaonguyen/sand-box/data/speed-profiles/test_large.csv";
//    public static final String PATH_TEST = "/home/thaonguyen/sand-box/data/speed-profiles/test.csv";
    public static final String PATH_TEST = "/home/thaonguyen/sand-box/data/speed-profiles/test01.csv";
//    public static final String PATH_TEST = "/home/thaonguyen/sand-box/data/speed-profiles/speed_profile_data_from_20130528_To_20150319.csv";

    public static String TAG = Test.class.getSimpleName();
    public static final Gson GSON = new Gson();
//    private static int[] ipArr = new int[26123456];

    public static void main(String[] args) {
        List<Long> list = new ArrayList();
        long t1 = 0l;
        for (int i = 0; i < 1; i++) {
            t1 = System.currentTimeMillis();
            int current = streamWithOffset(0);
            int last = streamWithOffset(current);
            System.out.println("last: " + last);
//            seekLine();
//            parseSpeedLogIO();
            long time = (System.currentTimeMillis() - t1);
            list.add(time);
            Logger.info(TAG, "Process took time: " + time + " ms");
            System.gc();
            System.out.println("--------------------");
        }
        long timeConsum = 0l;
        for (Long val : list) {
            timeConsum += val;
        }
        long average = timeConsum / list.size();
        System.out.println("Average: " + average);
    }

    public static int streamWithOffset(int off) {
        List<String> list = new ArrayList<>();
        InputStream is = null;
        BufferedReader br = null;
        int count = 0;
        int avai = 0;
        try {
            is = new FileInputStream(PATH_TEST);
            avai = is.available();
            System.out.println("bytes available: " + avai);
//            int off = 0; // offset to read file
            is.skip(off);
            br = new BufferedReader(new InputStreamReader(is));
            String s = null;
            while ((s = br.readLine()) != null) {
                System.out.println(s);
                list.add(s);
                count++;
                try {
                    Thread.sleep(4000l);
                } catch (Exception ex) {
                    Logger.error(TAG, ex);
                }
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
        System.out.println("total records: " + count);
        return avai;
    }

    public static void seekLine() {
        List<String> result = new ArrayList<>();
        try {
            RandomAccessFile raf = new RandomAccessFile(PATH_TEST, "r");
            // set the file pointer at 0 position
            raf.seek(0);
            String line = "";
            while ((line = raf.readLine()) != null) {
                result.add(line);
//                System.out.println(line);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("records: " + result.size());
    }

    public static List<String> parseSpeedLogIO() {
//        int count = 0;
        List<String> result = new ArrayList<>();
        BufferedReader br = null;
        FileReader fr = null;
        try {
            fr = new FileReader(PATH_TEST); // LogConfig.SPEED_LOG 
            br = new BufferedReader(fr);
            fr.skip(0);
            String line;
            while ((line = br.readLine()) != null) {
//                count++;
                result.add(line);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        //
        System.out.println("records: " + result.size());
//        System.out.println("====== List ======");
//        System.out.println("total records: " + count);
        return result;
    }

//    public static void readIPFromNIO() {
//        try {
//            List<String> list = new ArrayList<>();
//            FileInputStream fis = new FileInputStream(new File(PATH_TEST));
//            FileChannel channel = fis.getChannel();
//            long bufferSize = 40 * 1024;
//            ByteBuffer bb = ByteBuffer.allocate((int) bufferSize);
//            String postElements = "";
//            long len = 0;
//            while ((len = channel.read(bb)) != -1) {
//                bb.flip();
//                int getSize = bb.limit();
//                byte[] bytes = new byte[(int) getSize];
//                bb.get(bytes);
//                String packet = postElements + new String(bytes);
//                if (packet.endsWith("\n")) {
//                    list.addAll(Arrays.asList(packet.split("\n")));
//                    postElements = "";
//                } else {
//                    List<String> arr = new ArrayList(Arrays.asList(packet.split("\n")));
//                    int lastIndex = arr.size() - 1;
//                    postElements = arr.get(lastIndex);
//                    arr.remove(lastIndex);
//                    list.addAll(arr);
//                }
//                bb.clear();
//            }
//            System.out.println("records: " + list.size());
//            channel.close();
//            fis.close();
//        } catch (Exception ex) {
//            Logger.error(TAG, ex);
//        }
//    }
}
