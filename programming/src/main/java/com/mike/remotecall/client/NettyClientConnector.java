/*
 * Copyright 2022 WangKai
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mike.remotecall.client;

import com.mike.remotecall.struct.Call;
import com.mike.remotecall.codec.NettyMessageDecoder;
import com.mike.remotecall.codec.NettyMessageEncoder;
import com.mike.remotecall.struct.Header;
import com.mike.remotecall.struct.MessageType;
import com.mike.remotecall.struct.NettyMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class NettyClientConnector {
    private final String host;
    private final int port;

    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    private Channel channel;
    //private SimpleChatClientInitializer initializer;
    private CountDownLatch lathc = new CountDownLatch(1);

    public NettyClientConnector(String host, int port) throws Exception {
        this.host = host;
        this.port = port;
       // this.initializer = new SimpleChatClientInitializer(lathc);
    }


    public void connect() throws Exception {
        NioEventLoopGroup group = new NioEventLoopGroup();
        RpcClientHandler handler = new RpcClientHandler();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    //.handler(this.initializer);
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyMessageDecoder(1024 * 1024, 4, 4));
                            ch.pipeline().addLast("MessageEncoder", new NettyMessageEncoder());
                            ch.pipeline().addLast("RpcClientHandler", handler);
                        }
                    });
            // ????????????????????????
//            ChannelFuture future = b.connect(new InetSocketAddress(host, port), new InetSocketAddress(NettyConstant.LOCALIP,
//                    NettyConstant.LOCAL_PORT)).sync();

            ChannelFuture future = b.connect(host, port).sync();
            //
            this.channel = future.channel();
            future.channel().closeFuture().addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    System.out.println("??????????????????");
                }
            });
            // future.channel().closeFuture().sync();
        } finally {
            //group.shutdownGracefully();
            // ????????????????????????????????????????????????????????????????????????
//            executor.execute(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        TimeUnit.SECONDS.sleep(1);
//                        try {
//                            connect();// ??????????????????
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
        }
    }

    public void send(Object obj) throws IOException, InterruptedException {
        ChannelFuture future = channel.writeAndFlush(buildCallReq(obj));
        //future.sync();
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("===========????????????");
                } else {
                    System.out.println("------------------????????????");
                }
            }
        });
    }

//    public Object receive() throws Exception {
//        this.lathc.await();
//        return this.initializer.getResult();
//    }

    private NettyMessage buildCallReq(Object obj) {
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.CALL_REQ.value());
        message.setHeader(header);
        message.setBody(obj);
        return message;
    }

    public void close() throws Exception {

        this.channel.close();
        this.executor.shutdown();
    }

//    class SimpleChatClientInitializer extends ChannelInitializer<SocketChannel> {
//        private RpcClientHandler handler;
//
//        public SimpleChatClientInitializer(CountDownLatch lathc) {
//            handler = new RpcClientHandler(lathc);
//        }
//
//        @Override
//        protected void initChannel(SocketChannel ch) throws Exception {
//            ch.pipeline().addLast(new NettyMessageDecoder(1024 * 1024, 4, 4));
//            ch.pipeline().addLast("MessageEncoder", new NettyMessageEncoder());
//            ch.pipeline().addLast("RpcClientHandler", handler);
//        }
//
//        public Call getResult() {
//            return this.handler.getResult();
//        }
//
//        //???????????????
//        public void resetLathc(CountDownLatch initLathc) {
//            handler.resetLatch(initLathc);
//        }
//    }


}
