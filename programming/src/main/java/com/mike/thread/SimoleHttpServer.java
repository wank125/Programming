package com.mike.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SimoleHttpServer {
  static ThreadPool<HttpRequestHandler> threadPool = new DefaultThreadPool<HttpRequestHandler>(1);
  static String basePath;

  static class HttpRequestHandler implements Runnable {
    private Socket socket;

    private HttpRequestHandler(Socket socket) {
      this.socket = socket;
    }

    @Override
    public void run() {

      String line = null;
      BufferedReader br = null;
      BufferedReader reader = null;
      PrintWriter out = null;
      InputStream in = null;

      try {
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String header = reader.readLine();
        String filePath = basePath + header.split(" ")[1];
        out = new PrintWriter(socket.getOutputStream());

        if (filePath.endsWith("jpg")) {
          //todo

        }
      } catch (IOException e) {
        e.printStackTrace();
      }

    }
  }
}
