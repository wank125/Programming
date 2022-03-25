package com.mike.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadTcpServer {
    private static AtomicInteger count = new AtomicInteger();

    private static class SocketTask implements Runnable {
        private final Socket socket;

        public SocketTask(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (
                    InputStream in = socket.getInputStream();
                    OutputStream out = socket.getOutputStream();
            ) {
                String data = "Hello,you are client: " + count.incrementAndGet();
                out.write(data.getBytes());
                byte[] buffer = new byte[1024];
                int len = in.read(buffer);
                System.out.println("data is: " + new String(buffer, 0, len));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        try (ServerSocket serverSocket = new ServerSocket(6000);) {
            while (true) {
                Socket accept = serverSocket.accept();
                exec.execute(new SocketTask(accept));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
