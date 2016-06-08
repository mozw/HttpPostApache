package com.wanda.uap.test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by krast on 5/13/16.
 */

public class PerformanceJavaTest extends Thread {
    public static int sleepint = 0;
    //    public static String filepath = "F:\\exception.txt";
    public static String filepath = "/Users/mozhiwu/Documents/exlog/exception.txt";

    public PerformanceJavaTest() throws IOException {
    }

    public static void main(String[] args){

        int thnum = new Integer(args[0]);
        Thread[] thread = new Thread[thnum];
        sleepint = new Integer(args[1]);

        filepath = filepath + args[2];
        for (int i = 0; i < thnum ; i++) {
            try {
                thread[i] = new Thread(new PerformanceJavaTest());
                thread[i].start();
            } catch (IOException e) {
                //e.printStackTrace();
            }

        }

    }

    private int count = 0;
    private static String host = "http://10.213.42.236";
    private static String method = "/v1/query/member";
    private static String body = "{\"reqHeader\":{\"accessToken\":\"F4400-PRB3O-KZBAZ-FLNWG-2KKM6\",\"appId\":\"99bill\",\"entityId\":\"99bill\",\"reqId\":\"123\",\"sessionId\":\"123\"},\"PWID\":\"100000010000002180\"}";
//            "{\n" +
//            "  \"reqHeader\":{\n" +
//            "        \"accessToken\":\"TJDHVJ-3JR5-FJTJ599-CNDH22459-F\",\n" +
//            "    \"appId\":\"99bill\",\n" +
//            "    \"entityId\":\"99bill\",\n" +
//            "    \"reqId\":\"123\",\n" +
//            "    \"sessionId\":\"123\"\n" +
//            "  },\n" +
//            "  \"PWID\":\"100000010012744883\"\n" +
//            "}";


    private static FileWriter fw = null;

    public void run(){

        try {
            fw = new FileWriter(filepath, true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        while (0==0) {
            QueryMemberTest(sleepint);
        }
    }


    public void QueryMemberTest(int sleepint){


        URL url = null;

        HttpURLConnection conn = null;

        try {
            sleep(sleepint);
            url = new URL(host+method);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-type", "application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");// post

            conn.setUseCaches(false);
            conn.connect();
            // DataOutputStream out = new
            // DataOutputStream(conn.getOutputStream());
            PrintWriter outprint = new PrintWriter(new OutputStreamWriter(
                    conn.getOutputStream(), "UTF-8"));
            outprint.write(body);
            outprint.flush();
            outprint.close();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(), "UTF-8"));
//                StringBuffer buffer = new StringBuffer();
//                String line = "";
//                while ((line = reader.readLine()) != null) {
//                    buffer.append(line);
//                }
//                System.out.println(buffer.toString());
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
            try {

                fw.write("\n");
                fw.write(e.toString());
                fw.write(e.getStackTrace().toString());
                fw.flush();

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        if (conn != null) {
            conn.disconnect();
        }





    }


}
