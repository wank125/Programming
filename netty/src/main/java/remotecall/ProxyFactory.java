package remotecall;

import flashnetty.protocol.Call;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import remotecall.client.NettyClientConnector;
import remotecall.client.RpcClientHandler;
import remotecall.codec.NettyMessageDecoder;
import remotecall.codec.NettyMessageEncoder;
import remotecall.struct.Header;
import remotecall.struct.MessageType;
import remotecall.struct.NettyMessage;

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
