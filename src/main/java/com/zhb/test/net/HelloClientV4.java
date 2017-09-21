package com.zhb.test.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class HelloClientV4 {
	private static int port = 8000;
	private static String host = "127.0.0.1";

	public static void main(String[] args) {
		new Thread(new HelloClientHandle(host, port),"hello client v4").start();
	}

}

class HelloClientHandle implements Runnable {
	private String host;
	private int port;
	private Selector selector;
	private SocketChannel scChannel;
	private volatile boolean stop;
	//private ExecutorService pool = Executors.newFixedThreadPool(50);

	public HelloClientHandle(String host, int port) {
		// TODO Auto-generated constructor stub
		this.host = host;
		this.port = port;

		try {
			selector = Selector.open();
			scChannel = SocketChannel.open();
			scChannel.configureBlocking(false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			if(scChannel.connect(new InetSocketAddress(host, port))){
				scChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
				System.out.println("1success connect server...");
			}else{
				scChannel.register(selector, SelectionKey.OP_CONNECT);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		
		//pool.execute(new Thread(new WriteThread(scChannel),"client write thread"));
		new Thread(new WriteThread(scChannel),"client write thread").start();
		
		while(!stop){
			try{
				selector.select(1000);
				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				Iterator<SelectionKey> iterator = selectionKeys.iterator();
				SelectionKey key = null;
				while(iterator.hasNext()){
					key = iterator.next();
					iterator.remove();
					try {
						handleInput(key);
					    } catch (Exception e) {
							if (key != null) {
							    key.cancel();
							    if (key.channel() != null) {
							    	key.channel().close();
							    }
							}
					    }
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
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

	private void handleInput(SelectionKey key) throws Exception {
		// TODO Auto-generated method stub
		if(key.isValid()){
			SocketChannel scChannel = (SocketChannel)key.channel();
			if(key.isConnectable()){
				if(scChannel.finishConnect()){
					scChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
					System.out.println("1success connect server...");
				} else {
					System.exit(1);
				}
			}
			if(key.isReadable()){
				ByteBuffer readBuffer = ByteBuffer.allocate(1024);
				int readBytes = scChannel.read(readBuffer);
				if (readBytes > 0) {
				    readBuffer.flip();
				    byte[] bytes = new byte[readBuffer.remaining()];
				    readBuffer.get(bytes);
				    String body = new String(bytes, "UTF-8");
				    System.out.println("this receive: " + body);
				} else if (readBytes < 0) {
				    // 对端链路关闭
				    key.cancel();
				    scChannel.close();
				} else
				    ; // 读到0字节，忽略
			}
			
		}
	}
}

class WriteThread implements Runnable {
	private SocketChannel sc;
	private Scanner scanner = new Scanner(System.in);
	
	public WriteThread(SocketChannel sc) {
		// TODO Auto-generated constructor stub
		this.sc = sc;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			String inputString = scanner.next();
			if(inputString != null && inputString.trim().length() > 0){
				byte[] bytes = inputString.getBytes();
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
							break;
						}
					}
				}*/
			}
		}
	}
	
}
