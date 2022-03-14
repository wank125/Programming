package com.mike.thread;

public class Daemon {
  public static void main(String[] args) {
    Thread thread = new Thread(new DaemonRunner(), "DaemonRunner");
    thread.setDaemon(true);
    thread.start();
  }

  static class DaemonRunner implements Runnable {

    @Override
    public void run() {
      try {
        Thread.sleep(10000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
        System.out.println("DaemonThread finally run.");
      }
    }
  }
}
