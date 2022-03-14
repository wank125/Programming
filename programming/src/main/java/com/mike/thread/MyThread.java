package com.mike.thread;

public class MyThread extends Thread {
  @Override
  public void run() {
    super.run();
    try {
      for (int i = 0; i < 5000; i++) {

        if (this.isInterrupted()) {
          System.out.println("shoud be stopped and exit");
          // break;
          throw new InterruptedException();
        }
        System.out.println("i=" + (i + 1));
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    System.out.println("this line");
  }
}
