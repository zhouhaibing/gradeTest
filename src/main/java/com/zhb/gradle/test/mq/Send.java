package com.zhb.gradle.test.mq;

import com.google.common.base.Strings;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Send {

  private final static String QUEUE_NAME = "hello";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();
    

    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    String message = "Hello World!hahha";
    channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
    System.out.println(" [x] Sent '" + message + "'");

    channel.close();
    connection.close();
  }
  
  private String EXCHANGE_NAME = "hello_exchange";
  public void topicSend() throws Exception {
	  ConnectionFactory factory = new ConnectionFactory();
	  factory.setHost("127.0.0.1");
	  Connection con = factory.newConnection();
	  Channel channel = con.createChannel();
	  
	  channel.exchangeDeclare(EXCHANGE_NAME, "topic");
	//所有设备和日志级别  
      String[] facilities ={"auth","cron","kern","auth.A"};  
      String[] severities={"error","info","warning"};  
        
      for(int i=0;i<4;i++){  
          for(int j=0;j<3;j++){  
          //每一个设备，每种日志级别发送一条日志消息  
          String routingKey = facilities[i]+"."+severities[j%3];  
            
          // 发送的消息  
          String message =" Hello World!"+Strings.repeat(".", i+1);  
          //参数1：exchange name  
          //参数2：routing key  
          channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes());  
          System.out.println(" [x] Sent [" + routingKey +"] : '"+ message + "'");  
          }  
      }  
      // 关闭频道和连接  
      channel.close();  
      con.close();  
  }
}