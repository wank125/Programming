package com.mike.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class EchoClient {
  private String host = "localhost";
  private int port = 8000;
  private Socket socket;

  public EchoClient() throws IOException {
    socket = new Socket(host, port);
   // System.out.println(socket.getTrafficClass());
  }

  public static void main(String[] args) throws IOException {
    new EchoClient().talk();
  }

  private PrintWriter getWriter(Socket socket) throws IOException {
    OutputStream socketOutputStream = socket.getOutputStream();
    return new PrintWriter(socketOutputStream,true);
  }

  private BufferedReader getReader(Socket socket) throws IOException {
    InputStream inputStream = socket.getInputStream();
    return new BufferedReader(new InputStreamReader(inputStream));
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
