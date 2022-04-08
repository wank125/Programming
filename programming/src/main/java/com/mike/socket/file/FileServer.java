package com.mike.socket.file;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class FileServer {
    private int port = 8000;
    private ServerSocket serverSocket;

    private String filename = "/tmp/adobegc.log";
    private File file = new File(filename);

    public FileServer() throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("服务器启动");
    }

    public String echo(String msg) {
        return "echo: " + msg;
    }

    private PrintWriter getWriter(Socket socket) throws IOException {
        OutputStream socketOutputStream = socket.getOutputStream();
        return new PrintWriter(socketOutputStream, true);
    }

    private BufferedReader getReader(Socket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();
        return new BufferedReader(new InputStreamReader(inputStream));
    }

    private void sendFile(Socket socket) throws IOException {
        OutputStream outStream = socket.getOutputStream();
        RandomAccessFile fileOutStream = new RandomAccessFile(file, "r");
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = fileOutStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        fileOutStream.close();
        socket.close();
    }

    public void service() {
        while (true) {
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                System.out.println("New connection accepted" + socket.getInetAddress() + ":" + socket.getPort());
                sendFile(socket);

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
        new FileServer().service();
    }
}
