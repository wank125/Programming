package com.mike.netty.server.handler;

import com.mike.netty.protocol.response.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestHandler> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestHandler msg) throws Exception {
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        System.out.println(new Date() + ": 收到客户端消息: " + messageResponsePacket.getMessage());
        messageResponsePacket.setMessage("服务端回复【" + messageResponsePacket.getMessage() + "】");

        ctx.channel().writeAndFlush(messageResponsePacket);
    }
}
