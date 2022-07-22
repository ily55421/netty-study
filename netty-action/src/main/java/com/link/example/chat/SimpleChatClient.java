package com.link.example.chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @Author: linK
 * @Date: 2022/7/22 13:51
 * @Description TODO 聊天客户端实例
 */
public class SimpleChatClient {
    public static void main(String[] args) throws Exception{
        new SimpleChatClient("localhost", 11111).run();
    }

    private final String host;
    private final int port;

    public SimpleChatClient(String host, int port){
        this.host = host;
        this.port = port;
    }

    public void run() throws Exception{
        // 创建时间循环组
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // 启动引导程序
            Bootstrap bootstrap = new Bootstrap()
                    // 添加组
                    .group(group)
                    // 添加默认管道
                    .channel(NioSocketChannel.class)
                    // 添加处理器初始化
                    .handler(new SimpleChatClientInitializer());
            // 管道连接
            Channel channel = bootstrap.connect(host, port).sync().channel();
            // 读取输入
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            // 写出并刷新读取到的信息
            while(true){
                channel.writeAndFlush(in.readLine() + "\r\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭客户端
            group.shutdownGracefully();
        }

    }
}
