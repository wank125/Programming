package com.mike.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class NonBlockingClient {
    public static void main(String[] args) throws IOException {

        try (
                SocketChannel channel = SocketChannel.open();
                Selector selector = Selector.open();
        ) {
            channel.configureBlocking(false);
            channel.connect(new InetSocketAddress("127.0.0.1", 6000));
            if (channel.finishConnect()) {
                SelectionKey key = channel.register(selector, SelectionKey.OP_READ);
                channel.write(ByteBuffer.wrap("客户端".getBytes()));
                if (selector.select() > 0) {
                    if (key.isReadable()) {
                        ByteBuffer buf = ByteBuffer.allocate(1024);
                        channel.read(buf);
                        System.out.println(new String(buf.array(), 0, buf.position()));
                    }
                }
            } else {
                System.out.println("连接未建立");
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
