package com.zhb.test.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {
	
	private static String[] REQUESTMETHODS = {"HEAD","POST","GET","PUT"};
	
	/**
	 * http://stackoverflow.com/questions/3584210/preferred-java-way-to-ping-an-http-url-for-availability
	 * 
	 * Pings a HTTP URL. This effectively sends a HEAD request and returns <code>true</code> if the response code is in
	 * the 200-399 range.
	 * 
	 * @param url
	 *            The HTTP URL to be pinged.
	 * @param timeout
	 *            The timeout in millis for both the connection timeout and the response read timeout. Note that
	 *            the total timeout is effectively two times the given timeout.
	 * @return <code>true</code> if the given HTTP URL has returned response code 200-399 on a HEAD request within the
	 *         given timeout, otherwise <code>false</code>.
	 */
	public static boolean pingURL(String url, int timeout,String requestMethod) {
		String supportedMethod = requestMethod;
		if (isEmpty(url))
			return false;
		if(isEmpty(requestMethod))
			supportedMethod = "GET";
		
		url = url.replaceFirst("^https", "http");// Otherwise an exception may be thrown on invalid SSL certificates.
		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setConnectTimeout(timeout);
			connection.setReadTimeout(timeout);
			connection.setRequestMethod(supportedMethod);
			int responseCode = connection.getResponseCode();
			return (200 <= responseCode && responseCode <= 399);
		} catch (IOException exception) {
			return false;
		}
	}
	
	public static boolean pingURL(String url,int timeout){
		for(String requestMethod: REQUESTMETHODS){
			if(pingURL(url,timeout,requestMethod)){
				return true;
			}
		}
		return false;
	}
	public static boolean pingURL(String url){
		return pingURL(url,1000);
	}
	public static void main(String[] args){
		String gameUrl1 = "http://g1-master-vi-vn.xsjtopgame.com:8080/pay/xg";//TYHX 越南 only support POST
		String gameUrl2 = "http://123.59.107.77:8540/xgpayv2/";//锤子三国
		String gameUrl3 = "http://120.131.9.11:9001/melon_payment.php";//正三国
		String gameUrl4 = "http://g1-master-zh-tw.xsjtopgame.com:8080/sdk/xg";//TYHX tw only support POST
		
		//channel server url 
		String gameUrl5 = "http://mis.migc.xiaomi.com/api/biz/service/verifySession.do";
		String gameUrl6 = "http://sdk.g.uc.cn/cp/account.verifySession";
		String gameUrl7 = "https://usrsys.vivo.com.cn/sdk/user/auth.do";
		String gameUrl9 = "http://i.open.game.oppomobile.com/gameopen/user/fileIdInfo";
		String gameUrl10 = "http://mis.migc.xiaomi.com/api/biz/service/verifySession.d";
		//xg
		String gameUrl8 = "http://console.xgsdk.com:53479/login";
		String gameUrl11 = "http://a2.xgsdk.com:18888";
		
		String testUrl = "http://10.20.64.128:18888/isAlive";
		
		String monitorServerEsclient = "http://doc.xgsdk.com:10086/tools/package-client.jar.xg.201703212.zip";
		
		
		System.out.println(HttpUtil.pingURL(monitorServerEsclient));
	}
	
	public static boolean isEmpty(String str){
		if(str == null || str.equals("")){
			return true;
		}
		return false;
	}

}
