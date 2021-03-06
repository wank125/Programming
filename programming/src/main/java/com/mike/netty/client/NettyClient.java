package com.mike.netty.client;

import com.mike.netty.client.handler.*;
import com.mike.netty.protocol.PacketDecoder;
import com.mike.netty.protocol.PacketEncoder;
import com.mike.netty.protocol.command.ConsoleCommandManager;
import com.mike.netty.protocol.command.LoginConsoleCommand;
import com.mike.netty.protocol.request.LoginRequestPacket;
import com.mike.netty.protocol.request.MessageRequestPacket;
import com.mike.netty.protocol.PacketCodeC;
import com.mike.netty.server.session.SessionUtil;
import com.mike.netty.util.LoginUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class NettyClient {
    public static final int MAX_RETRY = 80;

    public static void main(String[] args) {
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 7, 4));
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new LoginResponseHandler());
                        ch.pipeline().addLast(new MessageResponseHandler());
                        ch.pipeline().addLast(new CreateGroupResponseHandler());
                        ch.pipeline().addLast(new JoinGroupResponseHandler());
                        // ?????????????????????
                        ch.pipeline().addLast(new QuitGroupResponseHandler());
                        // ??????????????????????????????
                        ch.pipeline().addLast(new ListGroupMembersResponseHandler());
                        // ???????????????
                        ch.pipeline().addLast(new GroupMessageResponseHandler());
                        // ?????????????????????
                        ch.pipeline().addLast(new LogoutResponseHandler());
                        ch.pipeline().addLast(new PacketEncoder());

                    }
                });
        connect(bootstrap, "localhost", 8080, 10);

    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println(port + "?????????????????????");
                    Channel channel = ((ChannelFuture) future).channel();
                    startConsoleThread(channel);
                } else if (retry == 0) {
                    System.out.println(port + "???????????????????????????");
                } else {
                    int order = (MAX_RETRY - retry) + 1;
                    int delay = 1 << order;
                    System.out.println("order: " + order + ",delay: " + delay);

                    System.err.println(new Date() + ": ??????????????????" + order + "???????????????");
                    bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);
                }
            }
        });
    }

    private static void startConsoleThread(Channel channel) {
        ConsoleCommandManager consoleCommandManager = new ConsoleCommandManager();
        LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();
        Scanner scanner = new Scanner(System.in);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted()) {
                    if (!SessionUtil.hasLogin(channel)) {
                        loginConsoleCommand.exec(scanner, channel);
                    } else {
                        consoleCommandManager.exec(scanner, channel);
                    }
                }
            }
        }).start();
    }
}

class FirstClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        for (int i = 0; i < 1000; i++) {
            ByteBuf buffer = getByteBuf(ctx);
            ctx.channel().writeAndFlush(buffer);
        }

//        System.out.println(new Date() + ": ?????????????????????");
//        ByteBuf byteBuf = getByteBuf(ctx);
//        ctx.channel().writeAndFlush(byteBuf);

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        System.out.println(new Date() + "??????????????????????????????->" + buf.toString(Charset.forName("utf-8")));
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        ByteBuf buffer = ctx.alloc().buffer();
        byte[] bytes = "??????,???????????????".getBytes(Charset.forName("utf-8"));
        buffer.writeBytes(bytes);
        return buffer;
    }
}
