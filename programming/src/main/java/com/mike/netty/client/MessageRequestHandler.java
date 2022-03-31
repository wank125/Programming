package com.mike.netty.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestHandler> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestHandler msg) throws Exception {

    }
}
