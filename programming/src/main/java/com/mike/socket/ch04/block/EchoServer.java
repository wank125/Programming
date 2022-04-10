package com.mike.socket.ch04.block;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoServer {
    private int port = 8000;
    private ServerSocketChannel serverSocketChannel = null;
    private ExecutorService executorService;

    private static final int POOL_MULTIPLE = 4;


    public EchoServer() throws IOException {
        executorService = Executors.
                newFixedThreadPool(Runtime.getRuntime().availableProcessors() * POOL_MULTIPLE);
        serverSocketChannel = ServerSocketChannel.open();

        //使得在同一个主机上关闭了服务器程序，紧接着再启动该服务器程序时，可以绑定相同的端口。
        serverSocketChannel.socket().setReuseAddress(true);
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        System.out.println("服务器启动");
    }

    public void service() {
        while (true) {
            SocketChannel socket = null;
            try {
                socket = serverSocketChannel.accept();
                executorService.execute(new Handler(socket));
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (socket != null) {
                        socket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new EchoServer().service();
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
