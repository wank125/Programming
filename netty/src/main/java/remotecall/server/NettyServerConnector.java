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
package remotecall.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import remotecall.NettyConstant;
import remotecall.codec.NettyMessageDecoder;
import remotecall.codec.NettyMessageEncoder;

import java.util.HashMap;
import java.util.Map;

public class NettyServerConnector {

  private Map<String, Object> remoteObjects = new HashMap();

  public void register(String className, Object remoteObject) {
    remoteObjects.put(className, remoteObject);
  }


  public void bind() throws Exception {
    NioEventLoopGroup bossGroup = new NioEventLoopGroup();
    NioEventLoopGroup workGroup = new NioEventLoopGroup();
    ServerBootstrap b = new ServerBootstrap();
    b.group(bossGroup, workGroup)
        .channel(NioServerSocketChannel.class)
        .option(ChannelOption.SO_BACKLOG, 1024)
        .childHandler(new ChannelInitializer<SocketChannel>() {
          @Override
          protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline().addLast(new NettyMessageDecoder(1024 * 1024, 4, 4));
            ch.pipeline().addLast(new NettyMessageEncoder());
            ch.pipeline().addLast("readTimeoutHandler", new ReadTimeoutHandler(50));
            ch.pipeline().addLast("RpcServerHandler", new RpcServerHandler());
          }
        });
    b.bind(NettyConstant.REMOTEIP, NettyConstant.PORT).sync();
    System.out.println("Netty server start ok : "
        + (NettyConstant.REMOTEIP + " : " + NettyConstant.PORT));

  }

  public static void main(String[] args) throws Exception {
    NettyServerConnector connector = new NettyServerConnector();
    connector.bind();
  }


}
