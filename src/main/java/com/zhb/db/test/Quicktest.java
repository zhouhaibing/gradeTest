package com.zhb.db.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class Quicktest {
	public static void main(String[] args){
		String s = "{\"amount\":\"6.00\",\"grade\":\"1\",\"roleName\":\"fh\",\"accountId\":\"196989786\",\"serverId\":\"0\",\"roleId\":\"10000638\",\"callbackInfo\":\"c16q2f1000391323\",\"cpOrderId\":\"c16q2f1000391323\"}";
		Quicktest test = new Quicktest();
		System.out.println(test.signWithoutRequestSeparator(JSON.parseObject(s), "5e96440152d6052fecfc36e4bb872758"));
		
		System.out.println(Hex.encodeHexString(DigestUtils.md5("accountId=196989786196989786=6.00callbackInfo=c16q2f1000391323cpOrderId=c16q2f1000391323grade=1roleId=10000638roleName=fhserverId=05e96440152d6052fecfc36e4bb872758")).toLowerCase());
	}
	
	
	private static final String REQUEST_EQUAL = "=";
    private static final String REQUEST_SEPARATOR = "&";
    
	public String signWithoutRequestSeparator(JSONObject jsonObject,String appSecret){
    	List<String> keyList = new ArrayList<String>(jsonObject.keySet());
        Collections.sort(keyList);
        StringBuilder builder = new StringBuilder();
        for (String key : keyList) {
            String value = jsonObject.getString(key);
            if(StringUtils.isNotEmpty(value)){
            	builder.append(key).append(REQUEST_EQUAL).append(value);
            }
        }
        builder.append(appSecret);
        String signingString = builder.toString();
        signingString.replaceAll(REQUEST_SEPARATOR, "");
        signingString.replaceAll("/n", "");
        signingString.replaceAll("/r", "");
        System.out.println(signingString);
        return Hex.encodeHexString(DigestUtils.md5(signingString)).toLowerCase();
    }

}
