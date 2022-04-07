package tomcat;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class GPResponse {

    private ChannelHandlerContext ctx;
    private HttpRequest req;

    public GPResponse(ChannelHandlerContext ctx, HttpRequest response) {
        this.ctx = ctx;
        this.req = response;
    }

    public void write(String out) throws IOException {
        try {
            if (out == null || out.length() == 0) {
                return;
            }
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK,
                    Unpooled.wrappedBuffer(out.getBytes(StandardCharsets.UTF_8))
            );
            response.headers().set("Content-Type", "text/html");
            ctx.write(response);
        } finally {
            ctx.flush();
            ctx.close();
        }

    }
}
