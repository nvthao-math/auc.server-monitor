/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *
 * @author thaonv
 */
public class ExecuteShellComand {

    public static void main(String[] args) {

        ExecuteShellComand obj = new ExecuteShellComand();
        //in mac oxs
        String command = "ps aux | wc -l";

        //in windows
        //String command = "ping -n 3 " + domainName;
        String output = obj.executeCommand(command);

        System.out.println(output);

    }

    private String executeCommand(String command) {

        StringBuffer output = new StringBuffer();

        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return output.toString();

    }

}
