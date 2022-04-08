package com.mike.socket;

import java.io.IOException;
import java.net.ServerSocket;

public class RandomPort {
  public static void main(String[] args) throws IOException {

    for (int i = 0; i < 65535; i++) {
      ServerSocket serverSocket = new ServerSocket(0);
      int localPort = serverSocket.getLocalPort();
      System.out.println(localPort);
    }

  }
}
