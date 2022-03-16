package com.mike.lock;

public class JoinCountDownLatchTest {
  public static void main(String[] args) throws InterruptedException {
    Thread parser1 = new Thread(new Runnable() {
      @Override
      public void run() {

      }
    });

    Thread parser2 = new Thread(new Runnable() {
      @Override
      public void run() {
        System.out.println("paraser2 finish");
      }
    });

    parser1.start();
    parser2.start();

    parser1.join();
    parser2.join();
    System.out.println("all parser finish");
  }




}
