package com.niudong.demo.p2p;

import com.google.common.base.Strings;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

@Component
public class P2pPointServer {

  private Logger logger = LoggerFactory.getLogger(P2pPointServer.class);

  private final int port = 7001;

  private List<WebSocket> localSockets = new ArrayList<>();

  public List<WebSocket> getLocalSockets() {
    return localSockets;
  }

  public void setLocalSockets(List<WebSocket> localSockets) {
    this.localSockets = localSockets;
  }

  @PostConstruct
  @Order(1)
  public void initServer() {
    System.out.println("启动服务端");

    final WebSocketServer webSocketServer = new WebSocketServer(new InetSocketAddress(port)) {
      @Override
      public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {

        sendMessage(webSocket, "北京服务短成功创建连接");
        localSockets.add(webSocket);
      }

      @Override
      public void onClose(WebSocket webSocket, int i, String s, boolean b) {
        logger.info(webSocket.getRemoteSocketAddress() + "客户端与服务端断开连接");
        localSockets.remove(webSocket);
      }

      @Override
      public void onMessage(WebSocket webSocket, String s) {
        logger.info("北京服务端接收到客户端信息: " + s);

      }

      @Override
      public void onError(WebSocket webSocket, Exception e) {
        logger.info(webSocket.getRemoteSocketAddress() + "客户端连接错误");
        localSockets.remove(webSocket);
      }

      @Override
      public void onStart() {
        logger.info("北京Websocket Server 端启动");
      }
    };
    webSocketServer.start();
    logger.info("北京服务端监听socketServer端口：" + port);
  }

  public void sendMessage(WebSocket ws, String message) {

    logger.info("发送给" + ws.getRemoteSocketAddress().getPort() + "的消息是:" + message);
    ws.send(message);

  }

  public void broatcast(String message) {
    if (localSockets.size() == 0 || Strings.isNullOrEmpty(message)) {
      return;
    }

    logger.info("Glad to say broatcast to clients beging startted!");
    for (WebSocket socket : localSockets) {
      this.sendMessage(socket, message);
    }
    logger.info("Glad to say broatcast to clients has overred!");
  }


}
