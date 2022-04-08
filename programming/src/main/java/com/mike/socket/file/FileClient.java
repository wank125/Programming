package com.mike.socket.file;

import java.io.*;
import java.net.Socket;
import java.util.Properties;

public class FileClient {
    private String host = "localhost";
    private int port = 8000;
    private Socket socket;

    String filename = "/tmp/adobegc1.log";
    File file = new File(filename);

    public FileClient() throws IOException {
        socket = new Socket(host, port);
        // System.out.println(socket.getTrafficClass());
    }

    public static void main(String[] args) throws IOException {
        // new FileClient().talk();
        new FileClient().receive();
    }

    private PrintWriter getWriter(Socket socket) throws IOException {
        OutputStream socketOutputStream = socket.getOutputStream();
        return new PrintWriter(socketOutputStream, true);
    }

    private BufferedReader getReader(Socket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();
        return new BufferedReader(new InputStreamReader(inputStream));
    }

    private void receive() throws IOException {
        InputStream inputStream = socket.getInputStream();
        RandomAccessFile fileOutStream = new RandomAccessFile(file, "rwd");
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {//从输入流中读取数据写入到文件中
            fileOutStream.write(buffer, 0, len);
        }
        fileOutStream.close();
        socket.close();
    }

    public void talk() throws IOException {
        try {
            BufferedReader br = getReader(socket);
            PrintWriter pw = getWriter(socket);
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String msg = null;
            while ((msg = reader.readLine()) != null) {
                pw.println(msg);
                System.out.println(br.readLine());
                if (msg.equals("bye")) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
