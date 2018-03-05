package com.seasun.kssdkserver.listener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.seasun.kssdkserver.dao.SdkExpPlayerDao;
import com.seasun.kssdkserver.service.CaptchaService;
import com.seasun.kssdkserver.service.KSCacheService;

@Component
public class RabbitmqTest {
	public final static String CHANGE_PASSWORD_PREFIX = "changePswInfo_";
	@Autowired
	private SdkExpPlayerDao sdkExpPlayerDao;

	@Autowired
	private KSCacheService cacheService;
	@Autowired
	private CaptchaService captchaService;
	
	private static Logger logger = LoggerFactory.getLogger(ChangePasswordListener.class);

	@RabbitListener(bindings = { @QueueBinding(
					value = @Queue(value = "passport_csm_kssdkserver", durable = "true"),
					exchange = @Exchange(value = "passport_default_exchange", durable = "true", type =
									ExchangeTypes.TOPIC),
					key = "info.pwd.modify") }
					)
					public void processMessage(byte data[]) {
		// 得到消息
		String changePswMsg = new String(data);
		logger.info("====================changePswMsg: " + changePswMsg);
		if (StringUtils.isNotEmpty(changePswMsg)) {
			JSONObject changePswObj = JSONObject.parseObject(changePswMsg);
			String changePswAccount = changePswObj.getString("account");
			if (StringUtils.isNotEmpty(changePswAccount)) {
				List<String> appIds = sdkExpPlayerDao.queryAppIdsByPassportId(changePswAccount);
				if (!CollectionUtils.isEmpty(appIds)) {
					for (String appId : appIds) {
						String key = getChangePasswordKey(appId, changePswAccount);
						JSONObject pswObj = new JSONObject();
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						pswObj.put("passport_id", changePswAccount);
						pswObj.put("update_time", format.format(new Date()));
						cacheService.set(key, pswObj.toJSONString());
					}
				}
				logger.info("====================clearCaptchaCache: " + changePswAccount);
				captchaService.clearCaptchaCache(changePswAccount);
			}
		}
	}
	
	public static String getChangePasswordKey(String appId, String passportId) {
		return CHANGE_PASSWORD_PREFIX + appId + "_" + passportId;
	}
}

