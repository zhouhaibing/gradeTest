package com.zhb.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Utils {
	private static final Logger logger = LoggerFactory.getLogger(Utils.class);
	
	public static String getInputStream1(HttpServletRequest request){
		InputStream is = null;
		BufferedReader br = null;
		String result = "";
		try {
			String tmpStr = "";
			is = request.getInputStream();
			br = new BufferedReader(new InputStreamReader(is));
			while((tmpStr = br.readLine()) != null){
				result += tmpStr;
			}
			
		} catch (IOException e) {
			logger.error("query_bound getInputStream error");
		} finally{
			if(br != null){
				try {
					br.close();//only close br,then br will close the inner stream.
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	public static String getInputStream(HttpServletRequest request){
		ByteArrayOutputStream os = null;
		InputStream is = null;
		try{
			is = request.getInputStream();
			os = new ByteArrayOutputStream();
			byte[] bytes = new byte[1024];
			int length = is.read(bytes);
			while(length > 0){
				os.write(bytes, 0, length);
				length = is.read(bytes);
			}
			return new String(os.toByteArray(),"UTF-8");
		} catch(IOException e){
			logger.error("get inputstream error");
		} finally{
			if(os != null){
				try{
					os.close();
				} catch(IOException e){
					e.printStackTrace();
				}
			}
			if(is != null){
				try{
					is.close();
				} catch(IOException e){
					e.printStackTrace();
				}
			}
		}
		return "";
	}

}
