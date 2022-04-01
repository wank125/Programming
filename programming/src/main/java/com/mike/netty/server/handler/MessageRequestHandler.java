package com.mike.netty.server.handler;

import com.mike.netty.protocol.request.MessageRequestPacket;
import com.mike.netty.protocol.response.MessageResponsePacket;
import com.mike.netty.server.session.Session;
import com.mike.netty.server.session.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket msg) throws Exception {
//        System.out.println(new Date() + ": 收到客户端消息: " + msg.getMessage());
//        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
//        messageResponsePacket.setMessage("服务端回复【" + msg.getMessage() + "】");
//        ctx.channel().writeAndFlush(messageResponsePacket);

    // 1.拿到消息发送方的会话信息
    Session session = SessionUtil.getSession(ctx.channel());
    // 2.通过消息发送方的会话信息构造要发送的消息
    MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
    messageResponsePacket.setFromUserId(session.getUserId());
    messageResponsePacket.setFromUserName(session.getUserName());
    messageResponsePacket.setMessage(msg.getMessage());

    // 3.拿到消息接收方的 channel
    Channel toUserChannel = SessionUtil.getChannel(msg.getToUserId());

    // 4.将消息发送给消息接收方
    if (toUserChannel != null && SessionUtil.hasLogin(toUserChannel)) {
      toUserChannel.writeAndFlush(messageResponsePacket);
    } else {
      System.err.println("[" + msg.getToUserId() + "] 不在线，发送失败!");
    }

  }
}
