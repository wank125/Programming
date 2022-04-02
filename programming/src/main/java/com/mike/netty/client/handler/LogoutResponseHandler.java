package com.mike.netty.client.handler;

import com.mike.netty.protocol.response.LoginResponsePacket;
import com.mike.netty.server.session.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class LogoutResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
    }
}
