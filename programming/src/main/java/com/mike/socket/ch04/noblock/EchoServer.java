package com.mike.socket.ch04.noblock;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;

public class EchoServer {
    private Selector selector = null;
    private int port = 8000;
    private ServerSocketChannel serverSocketChannel = null;
   // private ExecutorService executorService;

    private static final int POOL_MULTIPLE = 4;

    private Charset charset = Charset.forName("GBK");


    public EchoServer() throws IOException {

        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        //使得在同一个主机上关闭了服务器程序，紧接着再启动该服务器程序时，可以绑定相同的端口。
        serverSocketChannel.socket().setReuseAddress(true);
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(port));

        System.out.println("服务器启动");
    }

    public void service() throws IOException {

        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (selector.select() > 0) {
            Set<SelectionKey> readyKeys = selector.selectedKeys();
            Iterator<SelectionKey> it = readyKeys.iterator();
            while (it.hasNext()) {
                SelectionKey key = null;
                try {
                    key = (SelectionKey) it.next();
                    it.remove();
                    if (key.isAcceptable()) {
                        ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                        SocketChannel socketChannel = (SocketChannel) ssc.accept();
                        System.out.println("收到客户端的连接，来自：" + socketChannel.socket().getInetAddress() + ":"
                                + socketChannel.socket().getPort());
                        socketChannel.configureBlocking(false);
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        socketChannel.register(selector,
                                SelectionKey.OP_WRITE | SelectionKey.OP_WRITE, buffer);

                    }
                    if (key.isReadable()) {
                        receive(key);
                    }

                    if (key.isWritable()) {
                        send(key);
                    }
                } catch (IOException e) {
                    if (key != null) {
                        key.cancel();
                        key.channel().close();
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new EchoServer().service();
    }

    private void send(SelectionKey key) throws IOException {
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        SocketChannel channel = (SocketChannel) key.channel();
        buffer.flip();
        String data = decode(buffer);
        if (data.indexOf("\n\r") == -1) return;
        String outputData = data.substring(0, data.indexOf("\n\r") + 1);
        System.out.println(outputData);

        ByteBuffer outputBuffer = encode("echo:" + outputData);
        while (outputBuffer.hasRemaining()) {
            channel.write(outputBuffer);
        }
        ByteBuffer temp = encode(outputData);
        buffer.position(temp.limit());
        buffer.compact();

        if (outputData.equals("byte\r\n")) {
            key.cancel();
            key.channel().close();
            System.out.println("关闭与客户的连接");
        }

    }

    private String decode(ByteBuffer buffer) {
        CharBuffer decode = charset.decode(buffer);
        return decode.toString();
    }

    private ByteBuffer encode(String s) {
        ByteBuffer encode = charset.encode(s);
        return encode;
    }

    private void receive(SelectionKey key) throws IOException {
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer readBuffer = ByteBuffer.allocate(32);
        socketChannel.read(readBuffer);
        readBuffer.flip();
        buffer.limit(buffer.capacity());
        buffer.put(readBuffer);
    }

    private class Handler implements Runnable {

        private SocketChannel socketChannel;

        public Handler(SocketChannel socket) {
            this.socketChannel = socket;
        }

        private void handle(SocketChannel socketChannel) {
            Socket socket = null;
            try {
                socket = socketChannel.socket();
                System.out.println("收到客户端的连接，来自：" + socket.getInetAddress() + ":" + socket.getPort());
                BufferedReader br = getReader(socket);
                PrintWriter pw = getWriter(socket);

                String msg = null;
                while ((msg = br.readLine()) != null) {
                    System.out.println(msg);
                    pw.write(echo(msg));
                    if (msg.equals("bye")) {
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (socketChannel != null) {
                    try {
                        socketChannel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

        private PrintWriter getWriter(Socket socket) throws IOException {
            OutputStream socketOutputStream = socket.getOutputStream();
            return new PrintWriter(socketOutputStream, true);
        }

        private BufferedReader getReader(Socket socket) throws IOException {
            InputStream inputStream = socket.getInputStream();
            return new BufferedReader(new InputStreamReader(inputStream));
        }

        public String echo(String msg) {
            return "echo: " + msg;
        }

        @Override
        public void run() {
            handle(socketChannel);
        }
    }
}
