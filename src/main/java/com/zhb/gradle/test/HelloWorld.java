package com.zhb.gradle.test;


import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

public class HelloWorld {
	private static final Logger logger = LoggerFactory.getLogger(HelloWorld.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("hello world");
		/* String timeFomat = "yyyy-MM-dd HH:mm:ss";
		 String timeFomat2 = "yyyy-MM-dd HH:mm:ss.SSS";
		 String time1 = "2016-06-18 14:41:51.0";
		 SimpleDateFormat format = new SimpleDateFormat(timeFomat);
		 System.out.println(format.format(new Date()));
		 
		 try {
			System.out.println(format.format(DateUtils.parseDate(time1, timeFomat2)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 String time = "1351504294";
		 long t = Long.parseLong(time);
		 Timestamp ts = new Timestamp(t*1000);
		 System.out.println(ts);
		 
		 System.out.println("hellow" == "hello");*/

		// String str = "{\"productId\":\"1\",\"orderId\":\"616q291000334702\",\"paymentId\":\"201606271752217701299286\",\"price\":\"1\",\"appId\":\"Q0s6_NDsPxD_\",\"sign\":\"RUmMs9DV608WRPDRwGF3380Nsgv_Y63N48BBBzRK1BUcf8jYyhJojF1moEOmclEFuhQiEVRAlXo49oziHpEtmA\",\"userId\":\"DL2I6CRttkNY\",\"version\":\"1.0.0\",\"productName\":\"元宝\",\"checkCode\":\"iA_XkuSQo9vow9nyWH12v_QASnBPN3JrNPYXVCtkGkxM9B35IbkgVY8mwE9JRt1wzLohiP4LyTyp8QjHu6exsw\",\"deliveryCode\":\"cwhZILTdbY99XX4ep9ewhlFZhU2-wc4t3Hd87qFSB5J9AuHDLDOgtVEjFRlQjAw0flg2FuyJzKeqQYSVB82J2A\"}";
		// YouxiduoNotifyRequest request = JSON.parseObject(str, YouxiduoNotifyRequest.class);
		/*Person p = new Person();
		p.setName("");
		p.setHobby("basketball");
		System.out.println(p);*/
		
		
		/*String ss = "{\"addinfo\":\"{\"tradeNo\":\"716q261000217015\",\"platform\":\"ANDROID\"}\",\"items\":\"vng-productid\"}";
		JSONObject obj = JSON.parseObject(ss);
		JSONObject o = obj.getJSONObject("addinfo");//{"addinfo":{"hello":"world"}}. not {"addinfo":"{"hello":"world"}"}*/
		
		
		logger.info(MessageFormat.format("hello,{0},{1}.","good","mornig"));
		
	}

	static class Person{
		private String name;
		private int age;
		private String hobby;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
		}
		public String getHobby() {
			return hobby;
		}
		public void setHobby(String hobby) {
			this.hobby = hobby;
		}
		
		@Override
		public String toString(){
			return JSON.toJSONString(this);
		}
		
	}
}
