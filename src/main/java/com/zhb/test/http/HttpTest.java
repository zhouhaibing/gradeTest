package com.zhb.test.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


public class HttpTest {
	
	protected static final String MAC_NAME = "HmacSHA1";
	public static final String ENCODING = "UTF-8";
	public static final String URL_PARAM_START = "?";
	public static final String URL_PARAM_SEPARATOR = "&";
	public static final String URL_PARAM_EQUAL = "=";

	public static final String GAME_CHARSET = "UTF-8";
	public static final String REQUEST_EQUAL = "=";
	public static final String REQUEST_SEPARATOR = "&";
	public static final String REQUEST_CONTENTTYPE = "Content-Type";
	public static final String SEPARATOR = "__";
	
	
	public static void main(String[] args) throws UnsupportedEncodingException{
		//String url = "http://localhost:18888/pay-notify/2166/1198/v1?cp_order_id=217q251000396816&extend_info=217q251000396816&game_id=53&pay_amount=1.00&pay_order_number=PF_20170214105617gzXl&pay_status=1&pay_time=1487040977&props_name=10%E5%85%83%E5%AE%9D&server_id=200001&sign=MzQ0YzJjNjRlMGViZjgzMWE1ZTJmYzNiYjg4OTRiZjk%253D&user_account=530117";
		//String res = HttpUtils.doGet(url);
		//System.out.println(res);
		
		//System.out.println(URLEncoder.encode("cp_order_id=217q251000396816&extend_info=217q251000396816&game_id=53&pay_amount=1.00&pay_order_number=PF_20170214105617gzXl&pay_status=1&pay_time=1487040977&props_name=10%E5%85%83%E5%AE%9D&server_id=200001&sign=MzQ0YzJjNjRlMGViZjgzMWE1ZTJmYzNiYjg4OTRiZjk%253D&user_account=530117", "utf-8"));
		
		
		/*String url = "http://localhost:18888/pay-notify/pengyouwan/91000184/v1?";
		String paramUrl = "{\"uid\":\"40d59a64ddfc75cb680a231969c1e252\",\"tid\":\"6e16de2a-6fe4-43\",\"sign\":\"80772f0da9666b85b9ffce231a88615e\",\"gamekey\":\"1199ab5526\",\"channel\":\"PYW\",\"cp_orderid\":\"a17q241000416703\",\"ch_orderid\":\"R1710126N2286073\",\"amount\":\"6.00\",\"cp_param\":\"{\\\"roles_nick\\\":\\\"\u963f\u8428\u5fb7&\u963f\u8428\u5fb7\\\",\\\"product_desc\\\":\\\"6\u5143\u8d2d\u4e7010\u5143\u5b9d\\\",\\\"order_id\\\":\\\"a17q241000416703\\\",\\\"product_id\\\":\\\"1\\\",\\\"channel\\\":\\\"pengyouwan\\\",\\\"area_num\\\":\\\"1\\\"}\"}";
		//String paramUrl = "{\"uid\":\"40d59a64ddfc75cb680a231969c1e252\",\"tid\":\"6e16de2a-6fe4-43\",\"sign\":\"80772f0da9666b85b9ffce231a88615e\",\"gamekey\":\"1199ab5526\",\"channel\":\"PYW\",\"cp_orderid\":\"a17q241000416703\",\"ch_orderid\":\"R1710126N2286073\",\"amount\":\"6.00\",\"cp_param\":\"{\"roles_nick\":\"\u963f\u8428\u5fb7&\u963f\u8428\u5fb7\",\"product_desc\":\"6\u5143\u8d2d\u4e7010\u5143\u5b9d\",\"order_id\":\"a17q241000416703\",\"product_id\":\"1\",\"channel\":\"pengyouwan\",\"area_num\":\"1\"}\"}";
		//JSONObject o = JSONObject.parseObject(paramUrl);
		//String urlString = "http://localhost:18888/pay-notify/pengyouwan/91000184/v1?{\"uid\":\"40d59a64ddfc75cb680a231969c1e252\",\"tid\":\"6e16de2a-6fe4-43\",\"sign\":\"80772f0da9666b85b9ffce231a88615e\",\"gamekey\":\"1199ab5526\",\"channel\":\"PYW\",\"cp_orderid\":\"a17q241000416703\",\"ch_orderid\":\"R1710126N2286073\",\"amount\":\"6.00\",\"cp_param\":\"{\"roles_nick\":\"\u963f\u8428\u5fb7&\u963f\u8428\u5fb7\",\"product_desc\":\"6\u5143\u8d2d\u4e7010\u5143\u5b9d\",\"order_id\":\"a17q241000416703\",\"product_id\":\"1\",\"channel\":\"pengyouwan\",\"area_num\":\"1\"}\"}";
		String urlString = url + URLEncoder.encode(paramUrl,"UTF-8");
		//String urlString = url + paramUrl;
		String reString = HttpUtils.doPostJson(urlString, null);
		System.out.println(reString);*/
		
		/*String url = "http://localhost:18888/pay-notify/pengyouwan/91000184/v1";
		String paramUrl = "{\"uid\":\"40d59a64ddfc75cb680a231969c1e252\",\"tid\":\"6e16de2a-6fe4-43\",\"sign\":\"80772f0da9666b85b9ffce231a88615e\",\"gamekey\":\"1199ab5526\",\"channel\":\"PYW\",\"cp_orderid\":\"a17q241000416703\",\"ch_orderid\":\"R1710126N2286073\",\"amount\":\"6.00\",\"cp_param\":\"{\\\"roles_nick\\\":\\\"\u963f\u8428\u5fb7&\u963f\u8428\u5fb7\\\",\\\"product_desc\\\":\\\"6\u5143\u8d2d\u4e7010\u5143\u5b9d\\\",\\\"order_id\\\":\\\"a17q241000416703\\\",\\\"product_id\\\":\\\"1\\\",\\\"channel\\\":\\\"pengyouwan\\\",\\\"area_num\\\":\\\"1\\\"}\"}";
		
		System.out.println(HttpUtils.doPostForm(url, paramUrl));*/
		
		//System.out.println(HttpUtils.doPostJson("http://localhost:8093/http/test", "name=hello&age=10"));
		//"name=hello&age=10"
		
		Map<String,Object> params=new TreeMap<String,Object>();
		params.put("middlewareCode", "xgUserLimit");
		params.put("accessId", "xgsdk");
		params.put("accessTimestamp", String.valueOf(System.currentTimeMillis()));
		params.put("appId", "15985");
		//业务数据
		params.put("platformType", "ios");
		params.put("accountId", "ios_jinshanapple__987c02e1ce14f9d5112924__exp_.");
		params.put("deviceId", "08bbaf12-699d-4ec9-b398-0af3c1bd27e1");
		params.put("ip", "10.101.10.10");
		//所有参数签名
		params.put("sign", DigestUtils.md5Hex(getParameterString(params)+"&accessKey=dccc8de598cc375d649815e34197fd4e"));

		
		System.out.println(HttpUtils.doPostForm("http://dmw.xoyo.com/customs/xgUserForbidden.do", getParameterString(params)));
		
		
	}
	
	
	public static String getParameterString(Object request) {
		String jsonString = JSON.toJSONString(request);
		JSONObject jsonObject = JSON.parseObject(jsonString);
		List<String> keyList = new ArrayList<>(jsonObject.keySet());
		Collections.sort(keyList);
		StringBuilder builder = new StringBuilder();
		for (String key : keyList) {
			String value = jsonObject.getString(key);
			if (!StringUtils.isEmpty(value)) {
				try {
					value = URLEncoder.encode(value, "UTF-8").replace("+", "%20");
				} catch (UnsupportedEncodingException e) {
					//XGLog.supplementExceptionMessage(e);
				}
			}
			if (!StringUtils.isEmpty(value)) {
				builder.append(key).append(REQUEST_EQUAL).append(value).append(REQUEST_SEPARATOR);
			}
		}
		builder.delete(builder.length() - REQUEST_SEPARATOR.length(), builder.length());
		return builder.toString();
	}
}
