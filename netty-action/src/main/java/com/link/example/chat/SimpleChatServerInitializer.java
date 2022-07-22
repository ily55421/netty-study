package com.link.example.chat;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @Author: linK
 * @Date: 2022/7/22 11:26
 * @Description TODO SimpleChatServerInitializer 用来增加多个的处理类到 ChannelPipeline 上，包括编码、解码、SimpleChatServerHandler 等。
 * ChannelInitializer Initializer
 */
public class SimpleChatServerInitializer extends ChannelInitializer<SocketChannel> {



    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        // 管道责任链
        ChannelPipeline pipeline = ch.pipeline();
        // 基础帧
        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        // 字符串解码
        pipeline.addLast("decoder", new StringDecoder());
        // 字符串编码
        pipeline.addLast("encoder", new StringEncoder());
        // 注册聊天服务器
        pipeline.addLast("handler", new SimpleChatServerHandler());

        // 连接通知
        System.out.println("SimpleChatClient:"+ch.remoteAddress() +"连接上");
    }
}
