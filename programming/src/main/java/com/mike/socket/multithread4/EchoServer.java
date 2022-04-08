package com.mike.socket.multithread4;

import com.mike.thread.ShutDown;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

public class EchoServer {
    private int port = 8000;
    private ServerSocket serverSocket;
    private ExecutorService executorService;
    private final int POOL_SIZE = 4;

    private int portForShutdown = 8001;
    private ServerSocket serverSocketForShutDown;
    private boolean isShutDown = false;

    private Thread shutDownThread = new Thread() {
        @Override
        public synchronized void start() {
            this.setDaemon(true);
            super.start();
        }

        @Override
        public void run() {
            while (!isShutDown) {
                Socket socketForShutdown = null;
                try {
                    socketForShutdown = serverSocketForShutDown.accept();
                    BufferedReader br = new BufferedReader(new InputStreamReader(socketForShutdown.getInputStream()));
                    String command = br.readLine();
                    if (command.equals("shutdown")) {

                        long bengTime = System.currentTimeMillis();
                        socketForShutdown.getOutputStream().write("服务器正在关闭\r\n".getBytes());
                        isShutDown = true;
                        executorService.shutdown();
                        while (!executorService.isTerminated()) {
                            executorService.awaitTermination(30, TimeUnit.SECONDS);
                        }
                        serverSocket.close();
                        long endTime = System.currentTimeMillis();
                        socketForShutdown.getOutputStream().write(("服务器已经关闭，" + "关闭服务器用了" + (endTime - bengTime) + "毫秒").getBytes());
                        socketForShutdown.close();
                        serverSocketForShutDown.close();
                    } else {
                        socketForShutdown.getOutputStream().write("错误的命令\r\n".getBytes());
                        socketForShutdown.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public EchoServer() throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(60000);
        serverSocketForShutDown = new ServerSocket(portForShutdown);

        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * POOL_SIZE);
        shutDownThread.start();
        System.out.println("服务器启动");
    }

    public void service() {
        while (!isShutDown) {
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                executorService.execute(new Handler(socket));
                System.out.println("New connection accepted" + socket.getInetAddress() + ":" + socket.getPort());
            } catch (SocketTimeoutException e) {
                //不必处理等待客户连接时出现的超时异常
            } catch (RejectedExecutionException e) {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    return;
                }
            } catch (SocketException e) {
                if (e.getMessage().indexOf("socket closed") != -1) {
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new EchoServer().service();
    }

    private class Handler implements Runnable {

        private Socket socket;

        public Handler(Socket socket) {
            this.socket = socket;
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
            try {
                BufferedReader br = getReader(socket);
                PrintWriter pw = getWriter(socket);
                String msg = null;
                while ((msg = br.readLine()) != null) {
                    System.out.println(msg);
                    pw.println(echo(msg));
                    if (msg.equals("bye")) {
                        break;
                    }
                }
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
}
