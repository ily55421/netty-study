package com.link.example.websocketChat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @Author: linK
 * @Date: 2022/7/22 14:08
 * @Description TODO 处理 WebSocket frame
 *
 * todo WebSockets 在“帧”里面来发送数据，其中每一个都代表了一个消息的一部分。一个完整的消息可以利用了多个帧。
 * TODO WebSocket “Request for Comments” (RFC) 定义了六中不同的 frame; Netty 给他们每个都提供了一个 POJO 实现 ，而我们的程序只需要使用下面4个帧类型：
 *<P>
 * CloseWebSocketFrame
 * PingWebSocketFrame
 * PongWebSocketFrame
 * TextWebSocketFrame
 * TODO 在这里我们只需要显示处理 TextWebSocketFrame，其他的会由 WebSocketServerProtocolHandler 自动处理。
 *
 * 下面代码展示了 ChannelInboundHandler 处理 TextWebSocketFrame，同时也将跟踪在 ChannelGroup 中所有活动的 WebSocket 连接
 *</P>
 *  <P>
 *      上面显示了 TextWebSocketFrameHandler 仅作了几件事：
 *
 *      当WebSocket 与新客户端已成功握手完成，通过写入信息到 ChannelGroup 中的 Channel 来通知所有连接的客户端，
 * 然后添加新 Channel 到 ChannelGroup
 *
 *      如果接收到 TextWebSocketFrame，调用 retain() ，并将其写、刷新到 ChannelGroup，
 * 使所有连接的 WebSocket Channel 都能接收到它。和以前一样，retain() 是必需的，因为当 channelRead0（）返回时，
 * TextWebSocketFrame 的引用计数将递减。
 * 由于所有操作都是异步的，writeAndFlush() 可能会在以后完成，我们不希望它来访问无效的引用。
 *  </P>
 */
public class TextWebSocketFrameHandler extends
        SimpleChannelInboundHandler<TextWebSocketFrame> {

    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * TextWebSocketFrameHandler 继承自 SimpleChannelInboundHandler，
     * 这个类实现了 ChannelInboundHandler 接口，ChannelInboundHandler 提供了许多事件处理的接口方法，
     * 然后你可以覆盖这些方法。
     * 现在仅仅只需要继承 SimpleChannelInboundHandler 类而不是你自己去实现接口方法。
     *
     * 覆盖了 channelRead0() 事件处理方法。每当从服务端读到客户端写入信息时，将信息转发给其他客户端的 Channel。
     * 其中如果你使用的是 Netty 5.x 版本时，需要把 channelRead0() 重命名为messageReceived()
     * @param ctx           the {@link ChannelHandlerContext} which this {@link SimpleChannelInboundHandler}
     *                      belongs to
     * @param msg           the message to handle
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx,
                                TextWebSocketFrame msg) throws Exception {
        Channel incoming = ctx.channel();
        // 每次遍历管道对象 区分自己发送和其他用户
        for (Channel channel : channels) {
            if (channel != incoming){
                channel.writeAndFlush(new TextWebSocketFrame("[" + incoming.remoteAddress() + "]" + msg.text()));
            } else {
                channel.writeAndFlush(new TextWebSocketFrame("[you]" + msg.text() ));
            }
        }
    }

    /**
     * 覆盖了 handlerAdded() 事件处理方法。
     * 每当从服务端收到新的客户端连接时，客户端的 Channel 存入 ChannelGroup 列表中，
     * 并通知列表中的其他客户端 Channel
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();

        // Broadcast a message to multiple Channels
        channels.writeAndFlush(new TextWebSocketFrame("[SERVER] - " + incoming.remoteAddress() + " 加入"));

        channels.add(incoming);
        System.out.println("Client:"+incoming.remoteAddress() +"加入");
    }

    /**
     * 覆盖了 handlerRemoved() 事件处理方法。
     * 每当从服务端收到客户端断开时，客户端的 Channel 自动从 ChannelGroup 列表中移除了，
     * 并通知列表中的其他客户端 Channel
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();

        // Broadcast a message to multiple Channels
        channels.writeAndFlush(new TextWebSocketFrame("[SERVER] - " + incoming.remoteAddress() + " 离开"));

        System.out.println("Client:"+incoming.remoteAddress() +"离开");

        // A closed Channel is automatically removed from ChannelGroup,
        // so there is no need to do "channels.remove(ctx.channel());"
    }

    /**
     * 覆盖了 channelActive() 事件处理方法。服务端监听到客户端活动
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        System.out.println("Client:"+incoming.remoteAddress()+"在线");
    }

    /**
     * 覆盖了 channelInactive() 事件处理方法。服务端监听到客户端不活动
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        System.out.println("Client:"+incoming.remoteAddress()+"掉线");
    }

    /**
     * exceptionCaught() 事件处理方法是当出现 Throwable 对象才会被调用，
     * 即当 Netty 由于 IO 错误或者处理器在处理事件时抛出的异常时。在大部分情况下，捕获的异常应该被记录下来并且把关联的 channel 给关闭掉。
     * 然而这个方法的处理方式会在遇到不同异常的情况下有不同的实现，比如你可能想在关闭连接之前发送一个错误码的响应消息。
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        Channel incoming = ctx.channel();
        System.out.println("Client:"+incoming.remoteAddress()+"异常");
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }

}