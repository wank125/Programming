package com.mike.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

public class NonBlockingServer {
    private static final int BUF_SIZE = 1024;
    private static final int PORT = 6000;
    private static final int TIMEOUT = 6000;

    private static void handleAccept(SelectionKey key) throws IOException {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel sc = serverChannel.accept();
        sc.configureBlocking(false);
        sc.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(BUF_SIZE));
    }


    private static void handleCommunication(SelectionKey key) throws IOException {
        SocketChannel sc = (SocketChannel) key.channel();
        ByteBuffer buf = (ByteBuffer) key.attachment();

        while (sc.read(buf) > 0) {
            System.out.println(new String(buf.array(), 0, buf.position()));
            buf.clear();
        }
        sc.write(ByteBuffer.wrap("欢迎".getBytes()));
        key.cancel();
        sc.close();
    }

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        Selector selector = Selector.open();
        serverChannel.configureBlocking(false);
        serverChannel.bind(new InetSocketAddress(PORT));
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            if (selector.select(TIMEOUT) == 0) {
                continue;
            }

            Set<SelectionKey> keys = selector.selectedKeys();
            keys.forEach(key -> {
                try {
                    if (key.isAcceptable()) {
                        handleAccept(key);
                    }
                    if (key.isReadable()) {
                        handleCommunication(key);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                keys.remove(key);
            });

        }
    }
}
