package com.mike.thread;

class ThreadDemo extends Thread {

  @Override
  public void run() {
    // super(target);
    System.out.println("ThreadDemo:begin");
    try {
      Thread.sleep(1000);

    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("threadDemo:end");
  }
}

public class Test1 {
  public static void test1() {
    System.out.println("test1:begin");
    Thread t1 = new ThreadDemo();
    t1.start(); //异步操作
    System.out.println("test1:end");
  }

  public static void test2() {
    System.out.println("test2:begin");
    Thread t1 = new ThreadDemo();
    t1.run(); //同步操作
    System.out.println("test2:end");
  }

  public static void main(String[] args) {
    test1();
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println();
    test2();
  }
}
