package com.link.example.chat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Author: linK
 * @Date: 2022/7/22 13:47
 * @Description TODO 聊天客户端实现 输出信息
 * TODO 客户端的处理类比较简单，只需要将读到的信息打印出来即可
 */
public class SimpleChatClientHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(msg);
    }
}
