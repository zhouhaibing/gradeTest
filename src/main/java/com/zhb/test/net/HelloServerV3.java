package com.zhb.test.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 伪异步IO, 使用线程池 M/N
 * @author ZHOUHAIBING
 *
 */
public class HelloServerV3 {
	private int port = 8000;
	private ServerSocket serverSocket;
	public HelloServerV3() throws IOException{
		serverSocket = new ServerSocket(port);
		System.out.println("服务器启动");
	}
	
	public String echo(String msg){
		return "echo:" + msg;
	}
	
	public void service(){
		ServerHandleExecutePool pool = new ServerHandleExecutePool(50, 1000);
		while(true) {
			Socket socket = null;
			try {
				socket = serverSocket.accept();
				System.out.println("new connection accept," + socket.getInetAddress() + ":" + socket.getPort());
                /*Thread t = new Thread(new ServerConnectHandler(socket));
                t.setName("hello server v3");
                t.start();*/
				pool.execute(new ServerConnectHandler(socket));
			} catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) throws IOException{
		new HelloServerV3().service();
	}
}


