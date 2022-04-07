package flashnetty.client;

import flashnetty.protocol.Packet;
import flashnetty.protocol.PacketCodeC;
import flashnetty.protocol.response.LoginResponsePacket;
import flashnetty.protocol.response.MessageResponsePacket;
import flashnetty.util.LoginUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        System.out.println(new Date() + "客户端开始登陆");
//        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
//        loginRequestPacket.setUserId(UUID.randomUUID().toString());
//        loginRequestPacket.setUsername("flash");
//        loginRequestPacket.setPassword("pwd");
//        ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginRequestPacket);
//        ctx.channel().writeAndFlush(byteBuf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);
        if (packet instanceof LoginResponsePacket) {
            LoginResponsePacket login = (LoginResponsePacket) packet;
            if (login.isSuccess()) {
                LoginUtil.markAsLogin(ctx.channel());
                System.out.println("登陆成功");
            } else {
                System.out.println("客户端登陆失败,原因: " + login.getReason());
            }
        }
        if (packet instanceof MessageResponsePacket) {

            MessageResponsePacket messageResponsePacket = (MessageResponsePacket) packet;
            String message = messageResponsePacket.getMessage();
            System.out.println("收到服务端信息：" + message);
        }
    }
}
