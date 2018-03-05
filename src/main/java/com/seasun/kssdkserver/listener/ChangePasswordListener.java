package com.seasun.kssdkserver.listener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.seasun.kssdkserver.dao.SdkExpPlayerDao;
import com.seasun.kssdkserver.service.CaptchaService;
import com.seasun.kssdkserver.service.KSCacheService;

@Configuration
public class ChangePasswordListener {

	@Value("${spring.rabbitmq.host}")
	private String host;

	@Value("${spring.rabbitmq.port}")
	private String port;

	@Value("${spring.rabbitmq.username}")
	private String username;

	@Value("${spring.rabbitmq.password}")
	private String password;

	@Value("${spring.rabbitmq.virtual-host}")
	private String virtualHost;

	@Value("${powerking.sdk_server.changpwd.queue}")
	private String changpwdQueue;
	@Value("${powerking.sdk_server.changpwd.exchange}")
	private String changpwdExchange;
	@Value("${powerking.sdk_server.changpwd.key}")
	private String changpwdKey;
	
	@Value("${powerking.sdk_server.changpwd.isListener}")
	private String isListener;

	public final static String CHANGE_PASSWORD_PREFIX = "changePswInfo_";
	@Autowired
	private SdkExpPlayerDao sdkExpPlayerDao;

	@Autowired
	private KSCacheService cacheService;
	@Autowired
	private CaptchaService captchaService;

	private static Logger logger = LoggerFactory.getLogger(ChangePasswordListener.class);

	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.setAddresses(host + ":" + port);
		connectionFactory.setUsername(username);
		connectionFactory.setPassword(password);
		connectionFactory.setVirtualHost(virtualHost);
		connectionFactory.setPublisherConfirms(true); // 必须要设置
		return connectionFactory;
	}

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	// 必须是prototype类型
	public RabbitTemplate rabbitTemplate() {
		RabbitTemplate template = new RabbitTemplate(connectionFactory());
		return template;
	}

	/**
	 * 针对消费者配置
	 * 1. 设置交换机类型
	 * 2. 将队列绑定到交换机
	 * 
	 * 
	 * FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
	 * HeadersExchange ：通过添加属性key-value匹配
	 * DirectExchange:按照routingkey分发到指定队列
	 * TopicExchange:多关键字匹配
	 */
	@Bean
	public TopicExchange defaultExchange() {
		return new TopicExchange(changpwdExchange);
	}

	@Bean
	public Queue queue() {
		return new Queue(changpwdQueue, true); // 队列持久
	}

	@Bean
	public Binding binding() {
		return BindingBuilder.bind(queue()).to(defaultExchange()).with(changpwdKey);
	}

	@Bean
	public SimpleMessageListenerContainer messageContainer() {
		if (!"true".equals(isListener)) {
			return null;
		}
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());
		container.setQueues(queue());
		container.setExposeListenerChannel(true);
		container.setMaxConcurrentConsumers(1);
		container.setConcurrentConsumers(1);
		container.setAcknowledgeMode(AcknowledgeMode.MANUAL); // 设置确认模式手工确认
		container.setMessageListener(new ChannelAwareMessageListener() {

			@Override
			public void onMessage(Message message, Channel channel) throws Exception {
				byte[] body = message.getBody();
				// 得到消息
				String changePswMsg = new String(body);
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
				channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); // 确认消息成功消费

			}
		});
		return container;
	}

	public static String getChangePasswordKey(String appId, String passportId) {
		return CHANGE_PASSWORD_PREFIX + appId + "_" + passportId;
	}

}
