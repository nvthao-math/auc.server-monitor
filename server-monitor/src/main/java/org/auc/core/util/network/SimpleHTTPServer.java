/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.core.util.network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author thaonv
 */
//http://www.java2s.com/Tutorial/Java/0320__Network/SendingaPOSTRequestUsingaSocket.htm
//http://javarevisited.blogspot.com/2015/06/how-to-create-http-server-in-java-serversocket-example.html
public class SimpleHTTPServer {

    public static void main(String[] argv) throws Exception {
        ServerSocket server = new ServerSocket(8080);
        System.out.println("Listening for connection on port 8080 ....");
        while (true) {
            Socket clientSocket = server.accept();
            InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
            BufferedReader reader = new BufferedReader(isr);
            String line = reader.readLine();
            while (!line.isEmpty()) {
                System.out.println(line);
                line = reader.readLine();
            }
        }
    }

}
