package tomcat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class GPTomcat {
    private int port = 8080;
    private ServerSocket server;
    private Map<String, GPServlet> servletMapping = new HashMap<>();
    private Properties webxml = new Properties();

    private void init() {
        try {
            String WEB_INI = this.getClass().getResource("/").getPath();
            FileInputStream fis = new FileInputStream(WEB_INI + "web.properties");
            webxml.load(fis);

            for (Object k : webxml.keySet()) {
                String key = k.toString();
                if (key.endsWith(".url")) {
                    String servletName = key.replaceAll("\\.url$", "");
                    String url = webxml.getProperty(key);
                    String className = webxml.getProperty(servletName + ".className");
                    GPServlet obj = (GPServlet) Class.forName(className).newInstance();
                    servletMapping.put(url, obj);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void start() throws IOException {

        init();

        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new HttpResponseEncoder());
                            ch.pipeline().addLast(new HttpRequestDecoder());
                            ch.pipeline().addLast(new GPTomcatHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture future = bootstrap.bind(this.port).sync();
            future.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws IOException {
        new GPTomcat().start();
    }

    class GPTomcatHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            if (msg instanceof HttpRequest) {
                System.out.println("hello");
                HttpRequest req = (HttpRequest) msg;
                GPRequest request = new GPRequest(ctx, req);
                GPResponse response = new GPResponse(ctx, req);
                String url = request.getUrl();
                if (servletMapping.containsKey(url)){
                    servletMapping.get(url).service(request,response);
                }else {
                    response.write("404 - Not Found");
                }
            }

        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }
    }
}
