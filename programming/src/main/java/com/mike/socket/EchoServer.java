package com.mike.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
  private int port = 8000;
  private ServerSocket serverSocket;

  public EchoServer() throws IOException {
    serverSocket = new ServerSocket(port);
    System.out.println("服务器启动");
  }

  public String echo(String msg) {
    return "echo: " + msg;
  }

  private PrintWriter getWriter(Socket socket) throws IOException {
    OutputStream socketOutputStream = socket.getOutputStream();
    return new PrintWriter(socketOutputStream,true);
  }

  private BufferedReader getReader(Socket socket) throws IOException {
    InputStream inputStream = socket.getInputStream();
    return new BufferedReader(new InputStreamReader(inputStream));
  }

  public void service() {
    while (true) {
      Socket socket = null;
      try {
        socket = serverSocket.accept();
        System.out.println("New connection accepted" + socket.getInetAddress() + ":" + socket.getPort());
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

  public static void main(String[] args) throws IOException {
    new EchoServer().service();
  }
}
