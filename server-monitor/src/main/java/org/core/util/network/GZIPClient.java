package org.core.util.network;

///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package test;
//
//import org.apache.commons.httpclient.HttpClient;
//import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
//import org.apache.commons.httpclient.methods.PostMethod;
//import org.apache.commons.httpclient.methods.RequestEntity;
//import java.io.BufferedInputStream;
//import java.io.FileInputStream;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.io.PipedInputStream;
//import java.io.PipedOutputStream;
//import java.util.zip.GZIPOutputStream;
//
///**
// *
// * @author thaonv
// */
//public class GZIPClient {
//
//    public static void main(String[] args) throws Exception {
//        // The unzipped file to be uploaded as a POST is supplied 
//        // as the first argument. 
//
////        final PipedOutputStream pipeOut = new PipedOutputStream();
////        final PipedInputStream pipeIn = new PipedInputStream(pipeOut);
////        final InputStream fileIn = new FileInputStream(args[0]);
////        final InputStream in = new BufferedInputStream(fileIn);
////        final OutputStream out = new GZIPOutputStream(pipeOut);
//
////        Runnable r = new Runnable() {
////            public void run() {
////                try {
////                    try {
////                        byte[] buf = new byte[4096];
////                        int count = in.read(buf);
////                        while (count > 0) {
////                            System.out.println("GZipping " + count + " bytes.");
////                            out.write(buf, 0, count);
////                            count = in.read(buf);
////                        }
////                        System.out.println("Finished gzipping!");
////                    } finally {
////                        pipeOut.flush();
////                        out.flush();
////                        out.close();
////                        in.close();
////                        fileIn.close();
////                    }
////                } catch (Exception e) {
////                    e.printStackTrace();
////                }
////            }
////        };
//
//        // A separate thread fills the PipedInputStream with the gzipped content. 
////        new Thread(r).start();
//
//        PostMethod post = new PostMethod("http://127.0.0.1:8080/gzip/hello");
//        // It's very important to set Transfer-Encoding to "chunked", unless we 
//        // want to pre-zip the file to disk or memory to figure out an exact 
//        // content-length value. 
//        int AUTO = InputStreamRequestEntity.CONTENT_LENGTH_AUTO;
//        RequestEntity re = new InputStreamRequestEntity(pipeIn, AUTO);
//        post.setRequestEntity(re);
//        post.setRequestHeader("Content-Encoding", "gzip");
//
//        HttpClient client = new HttpClient();
//        int statusCode = client.executeMethod(post);
//
//        // Let's see what the server sent back as content. 
//        InputStream response = post.getResponseBodyAsStream();
//        response = new BufferedInputStream(response);
//        int c = response.read();
//        StringBuffer buf = new StringBuffer(8 * 1024);
//        while (c >= 0) {
//            byte b = (byte) c;
//            buf.append((char) b);
//            c = response.read();
//        }
//        post.releaseConnection();
//        System.out.println("RESPONSE FROM HTTP SERVER:");
//        System.out.println("------------------------------------------------");
//        System.out.println(buf.toString());
//        System.out.println("------------------------------------------------");
//        System.out.println("HTTP Status Code: " + statusCode);
//    }
//
//}
