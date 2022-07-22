package com.link.example.websocketChat;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @Author: linK
 * @Date: 2022/7/22 14:14
 * @Description TODO 服务初始化
 * <P>
 *     由于 Netty 处理了其余大部分功能，唯一剩下的我们现在要做的是初始化 ChannelPipeline 给每一个创建的新的 Channel 。
 *     做到这一点，我们需要一个ChannelInitializer
 * </P>
 */
public class WebsocketChatServerInitializer extends
        ChannelInitializer<SocketChannel> {	//扩展 ChannelInitializer
    /**
     * initChannel() 方法设置 ChannelPipeline 中所有新注册的 Channel,
     * 安装所有需要的　 ChannelHandler。
     * @param ch            the {@link Channel} which was registered.
     * @throws Exception
     */
    @Override
    public void initChannel(SocketChannel ch) throws Exception {//添加 ChannelHandler　到 ChannelPipeline
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(64*1024));
        pipeline.addLast(new ChunkedWriteHandler());
        //增加请求标识
        pipeline.addLast(new HttpRequestHandler("/ws"));
        //增加协议处理器路径标识
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        pipeline.addLast(new TextWebSocketFrameHandler());

    }
}
