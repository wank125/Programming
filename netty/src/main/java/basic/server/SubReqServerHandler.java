package basic.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import basic.client.pojo.SubscribeReq;
import basic.client.pojo.SubscribeResp;

public class SubReqServerHandler extends ChannelInboundHandlerAdapter {
//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        super.channelActive(ctx);
//    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SubscribeReq req = (SubscribeReq) msg;
        if ("Lili".equals(req.getUserName())) {
            System.out.println("Service accept client subscribe req : " + req.toString());
            ctx.writeAndFlush(resp(req.getSubReqID()));
        }
    }

    private SubscribeResp resp(int subReqID) {
        SubscribeResp subscribeResp = new SubscribeResp();
        subscribeResp.setSubReqID(subReqID);
        subscribeResp.setRespCode(0);
        subscribeResp.setDesc("Netty book order");
        return subscribeResp;
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
       // super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        ctx.close();
    }
}
