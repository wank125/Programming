package com.niudong.demo.p2p;

import org.java_websocket.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

public class P2pPointServer {

  private Logger logger = LoggerFactory.getLogger(P2pPointServer.class);

  private int port = 7001;

  private List<WebSocket> localSockets = new ArrayList<>();

  public List<WebSocket> getLocalSockets() {
    return localSockets;
  }

  public void setLocalSockets(List<WebSocket> localSockets) {
    this.localSockets = localSockets;
  }

  @PostConstruct
  @Order
  public void initServer() {

  }

}
