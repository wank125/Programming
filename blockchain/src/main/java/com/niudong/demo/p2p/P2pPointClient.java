package com.niudong.demo.p2p;

import com.google.common.base.Strings;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import javax.annotation.PostConstruct;

import org.java_websocket.client.WebSocketClient;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Component
public class P2pPointClient {
  private Logger logger = LoggerFactory.getLogger(P2pPointClient.class);
  private String wsUrl = "ws://localhost:7001";

  private List<WebSocket> localSockets = new ArrayList<>();

  public List<WebSocket> getLocalSockets() {
    return localSockets;
  }

  public void setLocalSockets(List<WebSocket> localSockets) {
    this.localSockets = localSockets;
  }

  @PostConstruct
  @Order(2)
  public void connectPeer() {
    try {
      System.out.println("启动客户端");
      WebSocketClient socketClient = new WebSocketClient(new URI(wsUrl)) {

        @Override
        public void onOpen(ServerHandshake handshakedata) {
          sendMessage(this, "北京客户端成功创建");
          localSockets.add(this);
        }

        @Override
        public void onMessage(String message) {
          logger.info("北京客户端发送到服务端的消息:" + message);
        }

        @Override
        public void onClose(int code, String reason, boolean remote) {
          logger.info("北京客户端关闭");
          localSockets.remove(this);
        }

        @Override
        public void onError(Exception ex) {
          logger.info("北京客户端错误");
          localSockets.remove(this);
        }
      };
      socketClient.connect();
    } catch (Exception e) {
      e.printStackTrace();
    }

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
