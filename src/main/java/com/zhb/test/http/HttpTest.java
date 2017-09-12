package com.zhb.test.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class HttpTest {
	public static void main(String[] args) throws UnsupportedEncodingException{
		String url = "http://localhost:18888/pay-notify/2166/1198/v1?cp_order_id=217q251000396816&extend_info=217q251000396816&game_id=53&pay_amount=1.00&pay_order_number=PF_20170214105617gzXl&pay_status=1&pay_time=1487040977&props_name=10%E5%85%83%E5%AE%9D&server_id=200001&sign=MzQ0YzJjNjRlMGViZjgzMWE1ZTJmYzNiYjg4OTRiZjk%253D&user_account=530117";
		String res = HttpUtils.doGet(url);
		System.out.println(res);
		
		System.out.println(URLEncoder.encode("cp_order_id=217q251000396816&extend_info=217q251000396816&game_id=53&pay_amount=1.00&pay_order_number=PF_20170214105617gzXl&pay_status=1&pay_time=1487040977&props_name=10%E5%85%83%E5%AE%9D&server_id=200001&sign=MzQ0YzJjNjRlMGViZjgzMWE1ZTJmYzNiYjg4OTRiZjk%253D&user_account=530117", "utf-8"));
		
	}

}
