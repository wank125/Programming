package com.mike.thread;

import java.util.concurrent.locks.ReentrantLock;

public class MyData {
  private int j = 0;
  private ReentrantLock lock = new ReentrantLock(true);

//  public void add() {
//    try {
//      lock.lock();
//      j++;
//      System.out.println("线程" + Thread.currentThread().getName() + "j为: " + j);
//      Thread.sleep(5000);
//    } catch (InterruptedException e) {
//      e.printStackTrace();
//    } finally {
//      lock.unlock();
//    }
//
//  }

  public synchronized void add() {
    try {
      System.out.println("拿到锁的线程 " + Thread.currentThread().getName());
      Thread.sleep(10000);
      j++;
      System.out.println("线程" + Thread.currentThread().getName() + "j为: " + j);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }

  public void dec() {
  }
//  public void dec() {
//    try {
//      lock.lock();
//      j--;
//      System.out.println("线程" + Thread.currentThread().getName() + "j为:" + j);
//    } finally {
//      lock.unlock();
//    }
//  }

  public int getData() {
    return j;
  }

  static class AddRunnable implements Runnable {
    MyData data;


    public AddRunnable(MyData data) {
      this.data = data;
    }

    @Override
    public void run() {
      data.add();
    }
  }

  static class DecRunnable implements Runnable {
    MyData data;

    public DecRunnable(MyData data) {
      this.data = data;
    }

    @Override
    public void run() {
      data.dec();
    }
  }

  public static void main(String[] args) throws InterruptedException {
    MyData data = new MyData();
    AddRunnable addRunnable = new AddRunnable(data);
    // DecRunnable decRunnable = new DecRunnable(data);
    for (int i = 0; i < 2; i++) {
      new Thread(addRunnable).start();

      Thread thread = new Thread(addRunnable);
      thread.start();
      Thread.sleep(1000);
      System.out.println(thread.getName() + thread.getState());
      thread.interrupt();
      System.out.println(thread.getName() + thread.isInterrupted());

      //new Thread(decRunnable).start();
    }
  }
}
