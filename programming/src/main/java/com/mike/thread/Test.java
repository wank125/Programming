package com.mike.thread;

class Mythread implements Runnable {
  @Override
  public void run() {
    System.out.println("Thread body");
  }
}


public class Test {
  public static void main(String[] args) {
    Mythread mythread = new Mythread();
    Thread thread = new Thread(mythread);
    thread.start();
    System.out.println(thread.getState());
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println(thread.getState());
  }

}
