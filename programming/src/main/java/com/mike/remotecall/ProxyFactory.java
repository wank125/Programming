package com.mike.remotecall;

import com.mike.remotecall.client.NettyClientConnector;
import com.mike.remotecall.client.RpcClientHandler;
import com.mike.remotecall.codec.NettyMessageDecoder;
import com.mike.remotecall.codec.NettyMessageEncoder;
import com.mike.remotecall.struct.Call;
import com.mike.remotecall.struct.Header;
import com.mike.remotecall.struct.MessageType;
import com.mike.remotecall.struct.NettyMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.rmi.RemoteException;

/**
 * 动态代理-Netty
 */

public class ProxyFactory {

    public static Object getProxy(final Class classType, final String host, final int port) {

        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                NettyClientConnector connector = new NettyClientConnector(host, port);
                //  try {
                Call call = new Call(classType.getName(), method.getName(), method.getParameterTypes(), args);
                NioEventLoopGroup group = new NioEventLoopGroup();
                RpcClientHandler handler = new RpcClientHandler();
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
                                    ch.pipeline().addLast("RpcClientHandler", handler);
                                }
                            });

                    ChannelFuture future = b.connect(host, port).sync();
                    future.channel().writeAndFlush(buildCallReq(call)).sync();
                    future.channel().closeFuture().sync();
                } finally {
                    group.shutdownGracefully();
                }
                Object result = handler.getResult().getResult();
                if (result instanceof Throwable) {
                    throw new RemoteException("", (Throwable) result);
                }
                return result;
            }
        };

        return Proxy.newProxyInstance(classType.getClassLoader(), new Class[]{classType}, handler);
    }

    private static NettyMessage buildCallReq(Object obj) {
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.CALL_REQ.value());
        message.setHeader(header);
        message.setBody(obj);
        return message;
    }
}
