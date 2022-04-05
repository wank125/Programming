package client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import user.UserInfo;

import java.util.logging.Logger;

public class EchoClientHandler extends ChannelInboundHandlerAdapter {

    private final int sendNumber;
    private static final Logger logger = Logger.getLogger(TimeClient.class.getName());

    private int counter;
    static final String ECHO_REQ = "Hi ,LILI.Welconme to Netty.$_";
    // private final ByteBuf firstMessage;

//    public EchoClientHandler() {
//        byte[] req = "QUERY TIME ORDER".getBytes();
//        //firstMessage = Unpooled.buffer(req.length);
//       // firstMessage.writeBytes(req);
//    }

    public EchoClientHandler(int sendNumber) {
        this.sendNumber = sendNumber;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        UserInfo[] infos = UserInfo();
        for (UserInfo u : infos) {
            ctx.write(u);
        }
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //  System.out.println("This is " + ++counter + " time receice server : [" + msg + "]");
        System.out.println("Client receive the msgppack message : " + msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // super.exceptionCaught(ctx, cause);
        logger.warning("Unexpected exception from downstream : " + cause.getMessage());
        ctx.close();
    }

    private UserInfo[] UserInfo() {
        UserInfo[] userinfos = new UserInfo[sendNumber];
        UserInfo userInfo = null;
        for (int s = 0; s < sendNumber; s++) {
            userInfo = new UserInfo();
            userInfo.setAge(s);
            userInfo.setName("ABCDEFG ----->" + s);
            userinfos[s] = userInfo;
        }
        return userinfos;
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
