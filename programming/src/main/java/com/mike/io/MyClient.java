package com.mike.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.channels.ServerSocketChannel;

public class MyClient {
  private int size = 1024;
  private ByteBuffer byteBuffer;
  private SocketChannel socketChannel;

  public void connectServer() throws IOException {
    socketChannel = SocketChannel.open();
    socketChannel.connect(new InetSocketAddress("127.0.0.1", 19999));
    socketChannel.configureBlocking(false);
    byteBuffer = ByteBuffer.allocate(size);
    receive();
  }

  public void receive() throws IOException {
    while (true) {
      byteBuffer.clear();
      int count;
      while ((count = socketChannel.read(byteBuffer)) > 0) {
        System.out.println((char) byteBuffer.get());
      }
      send2Server("say hi".getBytes());
      byteBuffer.clear();
      try {
        Thread.sleep(3000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  private void send2Server(byte[] bytes) throws IOException {
    byteBuffer.clear();
    byteBuffer.put(bytes);
    byteBuffer.flip();
    socketChannel.write(byteBuffer);
  }

  public static void main(String[] args) throws IOException {
    new MyClient().connectServer();
  }

}
