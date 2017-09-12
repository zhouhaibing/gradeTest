package com.zhb.test.net;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/*public class HelloClient {
	private String host = "localhost";
	private int port = 8000;
	private Socket socket;
	
	public HelloClient() throws IOException{
		socket = new Socket(host,port);
	}
	
	private PrintWriter getWriter(Socket socket) throws IOException {
		OutputStream socketOut = socket.getOutputStream();
		return new PrintWriter(socketOut);
	}
	
	private BufferedReader getReader(Socket socket) throws IOException {
		InputStream socketIn = socket.getInputStream();
		return new BufferedReader(new InputStreamReader(socketIn));
	}
	
	public void talk(){
		try {
			BufferedReader br = getReader(socket);
			PrintWriter pw = getWriter(socket);
			 // 读取服务器端传过来信息的DataInputStream  
            DataInputStream in = new DataInputStream(socket  
                    .getInputStream());  
            // 向服务器端发送信息的DataOutputStream  
            DataOutputStream out = new DataOutputStream(socket  
                    .getOutputStream()); 
            
			BufferedReader localReader = new BufferedReader(new InputStreamReader(System.in));
			String msg = null;
			// 获取控制台输入的Scanner  
            Scanner scanner = new Scanner(System.in); 
			while(true) {
				String send = scanner.nextLine();  
                System.out.println("客户端：" + send);  
                // 把从控制台得到的信息传送给服务器  
                pw.println("客户端：" + send);  
                // 读取来自服务器的信息  
                String accpet = br.readLine();  
                System.out.println(accpet);  
				if(msg.equals("bye")){
					break;
				}
				String send = scanner.nextLine();  
                System.out.println("客户端：" + send);  
                // 把从控制台得到的信息传送给服务器  
                out.writeUTF("客户端：" + send);  
                // 读取来自服务器的信息  
                String accpet = in.readUTF();  
                System.out.println(accpet);  
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
	
	public static void main(String[] args) throws IOException{
		new HelloClient().talk();
	}
	
}*/


/**
 * HelloClient1 版本
 * @author ZHOUHAIBING
 *
 */
public class HelloClientV3 {
	private String host = "localhost";
	private int port = 8000;
	private Socket socket;
	private String cliName;
	
	public HelloClientV3() throws IOException{
		System.out.println("-----客户端已开启-----");
        System.out.println("请输入客户端名字：");
        Scanner sc = new Scanner(System.in);
        cliName = sc.next();
		socket = new Socket(host,port);
	}
	
	private PrintWriter getWriter(Socket socket) throws IOException {
		OutputStream socketOut = socket.getOutputStream();
		return new PrintWriter(socketOut);
	}
	
	private BufferedReader getReader(Socket socket) throws IOException {
		InputStream socketIn = socket.getInputStream();
		return new BufferedReader(new InputStreamReader(socketIn));
	}
	
	public void talk(){
		try {
			Thread threadReader = new Thread(new ThreadReader(socket.getInputStream()),Thread.currentThread().getName());
	        Thread threadWriter = new Thread(new ThreadWriter(socket.getOutputStream()));
	        threadWriter.setName(cliName);
	        threadReader.setName(cliName);
	        threadReader.start();
	        threadWriter.start();
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException{
		new HelloClientV3().talk();
	}
	
}


