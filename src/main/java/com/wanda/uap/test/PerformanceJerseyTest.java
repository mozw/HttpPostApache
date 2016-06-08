package com.wanda.uap.test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import java.io.FileWriter;

/**
 * Created by krast on 5/13/16.
 */

public class PerformanceJerseyTest extends Thread {
    public static int sleepint = 0;
//    public static String filepath = "F:\\exception.txt";
    public static String filepath = "/opt/exception.txt";

    public static void main(String[] args){

        int thnum = new Integer(args[0]);
        Thread[] thread = new Thread[thnum];
        sleepint = new Integer(args[1]);

        filepath = filepath + args[2];
        for (int i = 0; i < thnum ; i++) {
            thread[i] = new Thread(new PerformanceJerseyTest());
            thread[i].start();
        }

    }

    public void run(){
        while (0==0) {
            QueryMemberTest(sleepint);
        }
    }

    private static String host = "http://10.213.42.236";
    private static String url = "/v1/query/member";
    private static String body = "{\"reqHeader\":{\"accessToken\":\"F4400-PRB3O-KZBAZ-FLNWG-2KKM6\",\"appId\":\"99bill\",\"entityId\":\"99bill\",\"reqId\":\"123\",\"sessionId\":\"123\"},\"PWID\":\"100000010000002180\"}";

    public static Client client = Client.create();
    public static WebResource webResource = client
            .resource(host+url);
    private static FileWriter fw = null;

    public void QueryMemberTest(int sleepint){
        try {

            ClientResponse response = null;
            String output = null;

            for (int i = 0; i < 10000; i++) {
                try {
                    sleep(sleepint);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                response = webResource.type("application/json")
                        .post(ClientResponse.class, body);

//                if (response.getStatus() != 200) {
//                    throw new RuntimeException("Failed : HTTP error code : "
//                            + response.getStatus());
//                }
//                output = response.getEntity(String.class);
//                System.out.println(output);

            }

        } catch (Exception e) {

            e.printStackTrace();
            try {
                fw = new FileWriter(filepath, true);
                fw.write("\n");
                fw.write(e.toString());
                fw.write(e.getStackTrace().toString());
                fw.flush();
                fw.close();

            } catch (Exception e1) {
                e1.printStackTrace();
            }

        }
    }

}
