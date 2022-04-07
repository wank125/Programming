package tomcat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequest;

public class GPTomcatHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        //super.channelRead(ctx, msg);
//        if (msg instanceof HttpRequest) {
//            System.out.println("hello");
//            HttpRequest req = (HttpRequest) msg;
//            GPRequest request = new GPRequest(ctx, req);
//            GPResponse response = new GPResponse(ctx, req);
//            String url = request.getUrl();
//            if ()
//        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //  super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        ctx.close();
    }
}
