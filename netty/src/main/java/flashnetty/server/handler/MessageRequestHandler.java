package flashnetty.server.handler;

import flashnetty.protocol.request.MessageRequestPacket;
import flashnetty.protocol.response.MessageResponsePacket;
import flashnetty.server.session.Session;
import flashnetty.server.session.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
  public static final MessageRequestHandler INSTANCE = new MessageRequestHandler();

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
