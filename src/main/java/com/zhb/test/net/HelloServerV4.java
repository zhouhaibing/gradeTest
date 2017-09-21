package com.zhb.test.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * NIO 非阻塞IO
 * NIO 
 * 1. 多人聊天程序
 * 2. 广播功能
 * 
 * @author ZHOUHAIBING
 */
public class HelloServerV4 {
	
	private static int port = 8000;
	
	public static void main(String[] args){
		new Thread(new ServerConnHandler(port), "hello server v4").start();
	}
}


class ServerConnHandler implements Runnable {
	private ServerSocketChannel ssc;
	private Selector selector;
	private volatile boolean stop;
	private ExecutorService pool = Executors.newFixedThreadPool(50);
	
	public ServerConnHandler(int port) {
		try {
			selector = Selector.open();
			ssc = ServerSocketChannel.open();
			ssc.configureBlocking(false);
			ssc.socket().bind(new InetSocketAddress(port), 1024);
			ssc.register(selector, SelectionKey.OP_ACCEPT);
			System.out.println("The time server is start in port : " + port);
		}catch(IOException e){
			e.printStackTrace();
			System.exit(1);
		}
		
	}
	
	public void stop() {
		this.stop = true;
	}
	
	@Override
	public void run() {
		
		pool.execute(new Thread(new ServerBroadcastThread(selector)));
		
		while(!stop){
			try{
				selector.select(1000);
				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				Iterator<SelectionKey> iterator = selectionKeys.iterator();
				SelectionKey key = null;
				while(iterator.hasNext()){
					key = iterator.next();
					iterator.remove();
					try{
						handleInput(key);
					}catch(Exception e){
						if(key != null) {
							key.cancel();
							if(key.channel() != null){
								key.channel().close();
							}
						}
					}
				}
			} catch(Throwable t) {
				t.printStackTrace();
			}
		}
		
		if (selector != null){
			try {
				selector.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public void handleInput(SelectionKey key) throws Exception {
		if(key.isValid()){
			if(key.isAcceptable()){
				ServerSocketChannel ssChannel = (ServerSocketChannel)key.channel();
				SocketChannel sc = ssChannel.accept();
				sc.configureBlocking(false);
				sc.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
			}
			if(key.isReadable()){
				SocketChannel sChannel = (SocketChannel)key.channel();
				ByteBuffer readBuffer = ByteBuffer.allocate(1024);
				int readBytes = sChannel.read(readBuffer);
				if(readBytes > 0){
					readBuffer.flip();
					byte[] bytes = new byte[readBuffer.remaining()];
					readBuffer.get(bytes);
					String bodyString = new String(bytes, "UTF-8");
					System.out.println("server receive: " + bodyString);
				} else if (readBytes < 0) {
					key.cancel();
					sChannel.close();
				} else {
					;
				}
			}
			/*if(key.isWritable()){
				
			}*/
		}
	}
	
}

class ServerBroadcastThread implements Runnable {
	private Selector selector;
	private Scanner scanner = new Scanner(System.in);
	
	public ServerBroadcastThread(Selector selector) {
		// TODO Auto-generated constructor stub
		this.selector = selector;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub		
		while(true){
			String inputString = scanner.next();
			System.out.println("inputstring: " + inputString);
			if(inputString != null && inputString.trim().length() > 0){
				try {
					selector.select(1000);
					Set<SelectionKey> selectionKeys = selector.selectedKeys();
					Iterator<SelectionKey> iterator = selectionKeys.iterator();
					SelectionKey key = null;
					while(iterator.hasNext()){
						key = iterator.next();
						iterator.remove();
						if(key.isValid()) {
							if(key.isWritable()){
								SocketChannel sChannel = (SocketChannel)key.channel();
								doWrite(sChannel,inputString);
							}
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					break;
				}
			}
			
		}
		
		if (selector != null){
			try {
				selector.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void doWrite(SocketChannel sc, String input){
		byte[] bytes = input.getBytes();
		ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
		writeBuffer.put(bytes);
		writeBuffer.flip();
		try {
			sc.write(writeBuffer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} /*finally {
			if(sc != null) {
				try {
					sc.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}*/
	}
}


