package com.mike.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class MyServer {

  private int size = 1024;
  private ServerSocketChannel serverSocketChannel;

  private ByteBuffer byteBuffer;
  private Selector selector;

  private int remoteClientNum = 0;

  public MyServer(int port) {
    try {
      initChannel(port);
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(-1);
    }
  }

  private void initChannel(int port) throws IOException {
    serverSocketChannel = ServerSocketChannel.open();
    serverSocketChannel.configureBlocking(false);

    serverSocketChannel.bind(new InetSocketAddress(port));
    System.out.println("listener on port: " + port);
    selector = Selector.open();
    serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    byteBuffer = ByteBuffer.allocate(size);
  }

  private void listener() throws Exception {
    while (true) {
      int n = selector.select();
      if (n == 0) {
        continue;
      }
      Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

      while (iterator.hasNext()) {
        SelectionKey key = iterator.next();
        System.out.println(key);
        if (key.isAcceptable()) {
          ServerSocketChannel server = (ServerSocketChannel) key.channel();
          SocketChannel channel = server.accept();
          registerChannel(selector, channel, SelectionKey.OP_READ);
          remoteClientNum++;
          System.out.println("oneline clinet num=" + remoteClientNum);
          write(channel, "hello client".getBytes());
         // break;
        }

        if (key.isReadable()) {
          read(key);
        }
        iterator.remove();
      }


    }
  }

  /**
   * @param key SelectionKey
   * @throws IOException
   */
  private void read(SelectionKey key) throws IOException {

    SocketChannel socketChannel = (SocketChannel) key.channel();
    int count;
    byteBuffer.clear();

    while ((count = socketChannel.read(byteBuffer)) > 0) {
      byteBuffer.flip();
      while (byteBuffer.hasRemaining()) {
        System.out.println((char) byteBuffer.get());
      }
      byteBuffer.clear();
    }
    if (count < 0) {
      socketChannel.close();
    }

  }

  /**
   * @param channel   SocketChannel
   * @param writeData data
   * @throws IOException
   */
  private void write(SocketChannel channel, byte[] writeData) throws IOException {
    byteBuffer.clear();
    byteBuffer.put(writeData);

    byteBuffer.flip();
    channel.write(byteBuffer);
  }


  private void registerChannel(Selector selector, SocketChannel channel, int opRead) throws IOException {
    if (channel == null) {
      return;
    }
    channel.configureBlocking(false);
    channel.register(selector, opRead);
  }

  public static void main(String[] args) {

    try {
      MyServer myServer = new MyServer(19999);
      myServer.listener();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
