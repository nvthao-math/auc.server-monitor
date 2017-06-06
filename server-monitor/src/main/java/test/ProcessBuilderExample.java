/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author thaonv
 */
public class ProcessBuilderExample {

    public static void main(String[] args) throws Exception {
        List<String> commands = new ArrayList<>();
        commands.add("bash");
        //Add arguments
        commands.add("-c");
        commands.add("echo $[100-$(vmstat 1 2|tail -1|awk '{print $15}')]");
        System.out.println(commands);

        //Run macro on target
        ProcessBuilder pb = new ProcessBuilder(commands);
        pb.directory(new File("/tmp"));
        pb.redirectErrorStream(true);
        Process process = pb.start();

        //Read output
        StringBuilder out = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = null, previous = null;
        while ((line = br.readLine()) != null) {
            if (!line.equals(previous)) {
                previous = line;
                out.append(line).append('\n');
                System.out.println(line);
            }
        }

        //Check result
        if (process.waitFor() == 0) {
            System.out.println("Success!");
            System.exit(0);
        }

        //Abnormal termination: Log command parameters and output and throw ExecutionException
        System.err.println(commands);
        System.err.println(out.toString());
        System.exit(1);
    }

}
