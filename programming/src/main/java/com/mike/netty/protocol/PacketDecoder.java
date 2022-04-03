package com.mike.netty.protocol;

import com.mike.netty.server.handler.LogoutRequestHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;


public class PacketDecoder extends ByteToMessageDecoder {
    // 2. 构造单例
   // public static final PacketDecoder INSTANCE = new PacketDecoder();

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        list.add(PacketCodeC.INSTANCE.decode(byteBuf));
    }
}
