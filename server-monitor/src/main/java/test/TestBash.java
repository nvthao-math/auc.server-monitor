/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import org.auc.monitor.bash.MemBash;
import org.auc.core.file.utils.Logger;

/**
 *
 * @author thaonguyen
 */
public class TestBash {

    public static String TAG = TestBash.class.getSimpleName();

    public static void main(String[] args) {
        try {
//            Process process = Runtime.getRuntime().exec("ps -eo nlwp | tail -n +2 | awk '{ num_threads += $1 } END { print num_threads }'"); // Mem info measure in kB
//            Process process = Runtime.getRuntime().exec("ps aux | wc -l"); // Mem info measure in kB
//            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            //            BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            String line = null;
//            while ((line = stdInput.readLine()) != null) {
//                System.out.println(line);
//            }

            //
            //
            /* Create the ProcessBuilder */
//            ProcessBuilder pb = new ProcessBuilder("echo", "$(sh ps aux | wc -l)");
            ProcessBuilder pb = new ProcessBuilder("/bin/bash", "ps -eo nlwp | tail -n +2 | awk '{ num_threads += $1 } END { echo num_threads }'");
////            pb.redirectErrorStream(true);
//
//            /* Start the process */
            Process proc = pb.start();
            System.out.println("Process started !");
//
//            /* Read the process's output */
            String line;
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    proc.getInputStream()));
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
//
//            /* Clean-up */
//            proc.destroy();
//            System.out.println("Process ended !");
//            //
//            //
//            ProcessBuilder builder = new ProcessBuilder("ps aux | wc -l");
//            Map<String, String> environ = builder.environment();
//            final Process process = builder.start();
//            InputStream is = process.getInputStream();
//            InputStreamReader isr = new InputStreamReader(is);
//            BufferedReader br = new BufferedReader(isr);
//            String line;
//            while ((line = br.readLine()) != null) {
//                System.out.println(line);
//            }
//            System.out.println("Program terminated!");
        } catch (Exception ex) {
            Logger.error(TAG, ex);
        }

    }

}
