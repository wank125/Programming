package com.mike.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Set;

public class ChatServer {

  private static final int BUFFER = 1024;
  private ByteBuffer read_buffer = ByteBuffer.allocate(BUFFER);
  private ByteBuffer write_buffer = ByteBuffer.allocate(BUFFER);

  private int port;

  public ChatServer(int port) {
    this.port = port;
  }

  private void start() {

    try (ServerSocketChannel server = ServerSocketChannel.open(); Selector selector = Selector.open()) {
      server.configureBlocking(false);

      server.socket().bind(new InetSocketAddress(port));

      server.register(selector, SelectionKey.OP_ACCEPT);
      System.out.println("启动服务器，监听端口:" + port);

      while (true) {
        if (selector.select() > 0) {
          Set<SelectionKey> selectionKeys = selector.selectedKeys();
          for (SelectionKey key : selectionKeys) {
            handles(key, selector);
          }
          selectionKeys.clear();
        }
      }

    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  private void handles(SelectionKey key, Selector selector) throws IOException {
    if (key.isAcceptable()) {

      ServerSocketChannel server = (ServerSocketChannel) key.channel();
      SocketChannel client = server.accept();
      client.configureBlocking(false);

      client.register(selector, SelectionKey.OP_READ);
      System.out.println("客户端" + client.socket().getPort() + "上线了");


    } else if (key.isReadable()) {
      SocketChannel client = (SocketChannel) key.channel();
      String msg = receive(client);
      System.out.println(msg);
      sendMessage(client, msg, selector);

      if (msg.equals("quit")) {
        key.channel();
        selector.wakeup();
        System.out.println("客户端" + client.socket().getPort() + "下线了");
      }
    }

  }


  private Charset charset = Charset.forName("UTF-8");


  private String receive(SocketChannel client) throws IOException {
    read_buffer.clear();

    while (client.read(read_buffer) > 0) {
      read_buffer.flip();
    }
    return String.valueOf(charset.decode(read_buffer));

  }

  private void sendMessage(SocketChannel client, String msg, Selector selector) throws IOException {

    msg = "客户端" + client.socket().getPort() + msg;
    for (SelectionKey key : selector.keys()) {
      if (!(key.channel() instanceof ServerSocketChannel && !client.equals(key.channel()) && key.isValid())) {
        SocketChannel otherClient = (SocketChannel) key.channel();
        write_buffer.clear();
        write_buffer.put(charset.encode(msg));
        write_buffer.flip();

        while (write_buffer.hasRemaining()) {
          otherClient.write(write_buffer);
        }
      }
    }
  }
}
