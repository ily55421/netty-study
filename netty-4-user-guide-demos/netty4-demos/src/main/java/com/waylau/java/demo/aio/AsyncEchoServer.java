/**
 * Welcome to https://waylau.com
 */
package com.waylau.java.demo.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Async Echo Server.
 * 
 * @since 1.0.0 2019年9月29日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
public class AsyncEchoServer {
	public static int DEFAULT_PORT = 7;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int port;

		try {
			// 启动参数
			port = Integer.parseInt(args[0]);
		} catch (RuntimeException ex) {
			// 默认端口
			port = DEFAULT_PORT;
		}
		// 异步服务器套接字通道
		AsynchronousServerSocketChannel serverChannel;
		try {
			// 打开一个异步服务器套接字通道。 默认分组
			serverChannel = AsynchronousServerSocketChannel.open();
			// Inet 套接字地址
			InetSocketAddress address = new InetSocketAddress(port);
			// 绑定地址
			serverChannel.bind(address);

			// 设置阐述  套接字接收缓冲区的大小。
			serverChannel.setOption(StandardSocketOptions.SO_RCVBUF, 4 * 1024);
			// 设置重用地址。
			serverChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);

			System.out.println("AsyncEchoServer已启动，端口：" + port);
		} catch (IOException ex) {
			ex.printStackTrace();
			return;
		}

		while (true) {

			// 可连接 接受连接。
			Future<AsynchronousSocketChannel> future = serverChannel.accept();
			// 异步套接字通道 默认null值
			AsynchronousSocketChannel socketChannel = null;
			try {
				socketChannel = future.get();
			} catch (InterruptedException | ExecutionException e) {
				System.out.println("AsyncEchoServer异常!" + e.getMessage());
			}

			System.out.println("AsyncEchoServer接受客户端的连接：" + socketChannel);
			//AsyncEchoServer已启动，端口：7888
			//AsyncEchoServer接受客户端的连接：sun.nio.ch.WindowsAsynchronousSocketChannelImpl[connected local=/127.0.0.1:7888 remote=/127.0.0.1:64625]

			// 分配缓存区
			ByteBuffer buffer = ByteBuffer.allocate(100);

			try {
				while (socketChannel.read(buffer).get() != -1) {
					buffer.flip();
					socketChannel.write(buffer).get();
					

					System.out.println("AsyncEchoServer  -> " 
							+ socketChannel.getRemoteAddress() + "：" + buffer.toString());
					
					
					if (buffer.hasRemaining()) {
						buffer.compact();
					} else {
						buffer.clear();
					}
				}

				socketChannel.close();
			} catch (InterruptedException | ExecutionException | IOException e) {
				System.out.println("AsyncEchoServer异常!" + e.getMessage());

			}

		}

	}
}
