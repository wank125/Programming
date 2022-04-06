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
import com.mike.remotecall.struct.MessageType;
import com.mike.remotecall.struct.NettyMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class RpcClientHandler extends ChannelInboundHandlerAdapter {
    public RpcClientHandler() {
  }

  private Call result;

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

    NettyMessage message = (NettyMessage) msg;
    // 如果是握手应答消息，需要判断是否认证成功
    if (message.getHeader() != null
        && message.getHeader().getType() == MessageType.CALL_RESP.value()) {
      result = (Call) message.getBody();
      if (result != null) {
        System.out.printf("Call for result : " + "Class Name : " + result.getClass().getName() + result);
      }
    } else
      ctx.fireChannelRead(msg);

  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

    cause.printStackTrace();
    ctx.close();
    ctx.fireExceptionCaught(cause);
  }

  public Call getResult() {
    return result;
  }
}
