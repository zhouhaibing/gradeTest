package com.zhb.test.net;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/*
 * HelloServer版本使用的是DataInputStream DataOutputStream
 * HelloServer1 使用的printwriter和BufferedReader
 */
/*public class HelloServer {
	private int port = 8000;
	private ServerSocket serverSocket;
	public HelloServer() throws IOException{
		serverSocket = new ServerSocket(port);
		System.out.println("服务器启动");
	}
	
	public String echo(String msg){
		return "echo:" + msg;
	}
	
	private PrintWriter getWriter(Socket socket) throws IOException {
		OutputStream socketOut = socket.getOutputStream();
		return new PrintWriter(socketOut);
	}
	
	private BufferedReader getReader(Socket socket) throws IOException {
		InputStream socketIn = socket.getInputStream();
		return new BufferedReader(new InputStreamReader(socketIn));
	}
	
	public void service(){
		while(true) {
			Socket socket = null;
			try {
				socket = serverSocket.accept();
				System.out.println("new connection accept," + socket.getInetAddress() + ":" + socket.getPort());
				//BufferedReader br = getReader(socket);
				//PrintWriter pw = getWriter(socket);
				// 读取服务器端传过来信息的DataInputStream  
                DataInputStream in = new DataInputStream(socket  
                        .getInputStream());  
                // 向服务器端发送信息的DataOutputStream  
                DataOutputStream out = new DataOutputStream(socket  
                        .getOutputStream());  
				
				// 获取控制台输入的Scanner  
                Scanner scanner = new Scanner(System.in); 
                
				String msg = null;
				while(true) {
					String accept = br.readLine();
					System.out.println(accept);  
                    String send = scanner.nextLine();  
                    System.out.println("服务器：" + send);  
                    // 把服务器端的输入发给客户端  
                    pw.println("服务器：" + send);
					if(msg.equals("bye")) {
						break;
					}
					 // 读取来自客户端的信息  
                    String accpet = in.readUTF();  
                    System.out.println(accpet);  
                    String send = scanner.nextLine();  
                    System.out.println("服务器：" + send);  
                    // 把服务器端的输入发给客户端  
                    out.writeUTF("服务器：" + send);  
				}
			} catch(IOException e){
				e.printStackTrace();
			} finally {
				try{
					if(socket != null){
						socket.close();
					}
				} catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) throws IOException{
		new HelloServer().service();
	}
}*/


/*
 * HelloServer1版本
 */
public class HelloServer {
	private int port = 8000;
	private ServerSocket serverSocket;
	public HelloServer() throws IOException{
		serverSocket = new ServerSocket(port);
		System.out.println("服务器启动");
	}
	
	public String echo(String msg){
		return "echo:" + msg;
	}
	
	private PrintWriter getWriter(Socket socket) throws IOException {
		OutputStream socketOut = socket.getOutputStream();
		return new PrintWriter(socketOut);
	}
	
	private BufferedReader getReader(Socket socket) throws IOException {
		InputStream socketIn = socket.getInputStream();
		return new BufferedReader(new InputStreamReader(socketIn));
	}
	
	public void service(){
		while(true) {
			Socket socket = null;
			try {
				socket = serverSocket.accept();
				System.out.println("new connection accept," + socket.getInetAddress() + ":" + socket.getPort());
				BufferedReader br = getReader(socket);
				PrintWriter pw = getWriter(socket);
				
				// 获取控制台输入的Scanner  
                Scanner scanner = new Scanner(System.in); 
                
				String msg = null;
				while(true) {
					String accept = br.readLine();
					System.out.println(accept);  
                    String send = scanner.nextLine();  
                    System.out.println("服务器：" + send);  
                    // 把服务器端的输入发给客户端  
                    pw.println("服务器：" + send);
                    pw.flush();
					if(send.equals("bye")) {
						break;
					}
					
				}
			} catch(IOException e){
				e.printStackTrace();
			} finally {
				try{
					if(socket != null){
						socket.close();
					}
				} catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) throws IOException{
		new HelloServer().service();
	}
	

}


