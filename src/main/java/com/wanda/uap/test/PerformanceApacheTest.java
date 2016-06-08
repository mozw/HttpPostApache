package com.wanda.uap.test;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
/**
 * Created by krast on 5/13/16.
 */

import java.io.FileWriter;


public class PerformanceApacheTest extends Thread {
	public static int sleepint = 0;
	public static long total = 0;
	public static long lcount = 0;
	//    public static String filepath = "F:\\exception.txt";
	public static String filepath = "/opt/exception.txt";

	public static void main(String[] args) {

		int thnum = new Integer(args[0]);
		Thread[] thread = new Thread[thnum];

		sleepint = new Integer(args[1]);

		filepath = filepath + args[2];

		for (int i = 0; i < thnum; i++) {
			thread[i] = new Thread(new PerformanceApacheTest());
			thread[i].start();
		}


	}

	public void run() {
		while (0 == 0) {
			QueryMemberTest(sleepint);
		}
	}

	private int count = 0;
	private static String host = "http://10.213.42.236";
	private static String url = "/v1/query/member";
	//    private String body = "{\"reqHeader\":{\"accessToken\":\"F4400-PRB3O-KZBAZ-FLNWG-2KKM6\",\"appId\":\"99bill\",\"entityId\":\"99bill\",\"reqId\":\"123\",\"sessionId\":\"123\"},\"PWID\":\"300111000087811200\"}";
	private static String body = "{\"reqHeader\":{\"accessToken\":\"F4400-PRB3O-KZBAZ-FLNWG-2KKM6\",\"appId\":\"99bill\",\"entityId\":\"99bill\",\"reqId\":\"123\",\"sessionId\":\"123\"},\"PWID\":\"100000010000002180\"}";
	private static String exrespons = "{\"PWID\":300111000087811200,\"PWIDStatus\":9,\"PWIDType\":\"99bill\",\"createTime\":1463368540000,\"email\":\"912756634@qq.com\",\"entityFrom\":\"99bill\",\"idNumber\":\"123456789009087766\",\"idType\":1,\"mobile\":\"13920000014\",\"name\":\"xiaoming\",\"realNameLevel\":105,\"regTime\":1463368540000,\"regType\":2,\"respHeader\":{\"reqId\":\"123\",\"respCode\":\"UAP-10000\",\"respMessage\":\"成功\",\"sessionId\":\"123\"},\"rnlUpdateTime\":1463368552000,\"securityLevel\":2,\"sysFrom\":\"99bill\",\"updateTime\":1463368622000}";


    public StringEntity entity = new StringEntity(body,"utf-8");//解决中文乱码问题
    public HttpPost httppost = new HttpPost(host + url);


	private static FileWriter fw = null;

	public void QueryMemberTest(int sleepint) {
		try {

            HttpResponse response = null;
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httppost.setEntity(entity);

			for (int i = 0; i < 10000; i++) {
				try {

					sleep(sleepint);

				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				lcount++;
				long begin = System.currentTimeMillis();

                DefaultHttpClient httpclient = new DefaultHttpClient();
                response  = httpclient.execute(httppost);
//				if(response.getStatusLine().getStatusCode()==200) {
//					try {
//						/**读取服务器返回过来的json字符串数据**/
//						System.out.println(EntityUtils.toString(response.getEntity()));
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						//e.printStackTrace();
//					}
//				}

                httpclient.close();

				long end = System.currentTimeMillis()-begin;
				total+=end;
				System.out.println(end+"--"+total/lcount);
			}

		} catch (Exception e) {
			lcount--;
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
