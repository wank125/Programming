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

import com.mike.remotecall.Call;
import com.mike.remotecall.NettyConstant;
import com.mike.remotecall.codec.NettyMessageDecoder;
import com.mike.remotecall.codec.NettyMessageEncoder;
import com.mike.remotecall.struct.Header;
import com.mike.remotecall.struct.MessageType;
import com.mike.remotecall.struct.NettyMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NettyClientConnector {
    private final String host;
    private final int port;

    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    private Channel channel;
    private SimpleChatClientInitializer initializer;
    private CountDownLatch lathc = new CountDownLatch(1);

    public NettyClientConnector(String host, int port) throws Exception {
        this.host = host;
        this.port = port;
        this.initializer = new SimpleChatClientInitializer(lathc);
    }


    public void connect() throws Exception {
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(this.initializer);
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
                            connect();// 发起重连操作
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
        ChannelFuture future = channel.writeAndFlush(buildCallReq(obj));
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {

                if (future.isSuccess()) {
                    System.out.println("===========发送成功");
                } else {
                    System.out.println("------------------发送失败");
                }
            }
        });
    }

    public Object receive() throws Exception {
        this.lathc.await();
        return this.initializer.getResult();
    }

    private NettyMessage buildCallReq(Object obj) {
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.LOGIN_REQ.value());
        message.setHeader(header);
        message.setBody(obj);
        return message;
    }

    public void close() throws Exception {
        this.channel.close();
        this.executor.shutdown();
    }

    class SimpleChatClientInitializer extends ChannelInitializer<SocketChannel> {
        private CountDownLatch lathc;
        private RpcClientHandler handler = new RpcClientHandler(lathc);

        public SimpleChatClientInitializer(CountDownLatch lathc) {
            this.lathc = lathc;
        }

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline().addLast(new NettyMessageDecoder(1024 * 1024, 4, 4));
            ch.pipeline().addLast("MessageEncoder", new NettyMessageEncoder());
            ch.pipeline().addLast("readTimeoutHandler", new ReadTimeoutHandler(50));
            ch.pipeline().addLast("RpcClientHandler", handler);
        }

        public Call getResult() {
            return this.handler.getResult();
        }

        //重置同步锁
        public void resetLathc(CountDownLatch initLathc) {
            handler.resetLatch(initLathc);
        }
    }


}
