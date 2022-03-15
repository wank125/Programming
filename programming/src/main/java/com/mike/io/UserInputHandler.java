package com.mike.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserInputHandler implements Runnable {
  ChatClient client;

  public UserInputHandler(ChatClient client) {
    this.client = client;
  }

  @Override
  public void run() {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    while (true) {
      try {
        String input = reader.readLine();
        client.send(input);
        if (input.equals("quit")) {
          break;
        }
      } catch (IOException e) {
        e.printStackTrace();
      }

    }
  }
}
