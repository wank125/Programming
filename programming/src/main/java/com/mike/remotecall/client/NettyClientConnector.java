package com.mike.remotecall.client;

import com.mike.remotecall.NettyConstant;
import com.mike.remotecall.codec.NettyMessageDecoder;
import com.mike.remotecall.codec.NettyMessageEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NettyClientConnector {

    // private final String host;
    // private final int port;

    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    private Channel channel;

    public NettyClientConnector(String host, int port) throws Exception {
        connect(port, host);
    }


    public void connect(int port, String host) throws Exception {
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyMessageDecoder(1024 * 1024, 4, 4));
                            ch.pipeline().addLast("MessageEncoder", new NettyMessageEncoder());
                            ch.pipeline().addLast("readTimeoutHandler", new ReadTimeoutHandler(50));
                            // ch.pipeline().addLast("LoginAuthHandler", new LoginAuthReqHandler());
                            // ch.pipeline().addLast("HeartBeatHandler", new HeartBeatReqHandler());
                        }
                    });
            // 发起异步连接操作
            ChannelFuture future = b.connect(new InetSocketAddress(host, port), new InetSocketAddress(NettyConstant.LOCALIP,
                    NettyConstant.LOCAL_PORT)).sync();
            //
            this.channel = future.channel();
            future.channel().closeFuture().sync();
        } finally {
            //group.shutdownGracefully();
            // 所有资源释放完成之后，清空资源，再次发起重连操作
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                        try {
                            connect(NettyConstant.PORT, NettyConstant.REMOTEIP);// 发起重连操作
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void send(Object obj) throws IOException {
        channel.writeAndFlush(obj);
    }

//    public Object receive() throws Exception {
//        return ois.readObject();
//    }
}
