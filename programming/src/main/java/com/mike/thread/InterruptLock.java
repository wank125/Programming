package com.mike.thread;

import java.util.concurrent.locks.ReentrantLock;

public class InterruptLock {
  public ReentrantLock lock1 = new ReentrantLock();
  public ReentrantLock lock2 = new ReentrantLock();

  public Thread lock1() {
    Thread t = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          lock1.lockInterruptibly();
          try {
            Thread.sleep(500);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          lock2.lockInterruptibly();
          System.out.println(Thread.currentThread().getName() + ",执行完毕!");

        } catch (InterruptedException e) {
          e.printStackTrace();
        } finally {
          if (lock1.isHeldByCurrentThread()) {
            lock1.unlock();
          }
          if (lock2.isHeldByCurrentThread()) {
            lock2.unlock();
          }
          System.out.println(Thread.currentThread().getName() + ",退出");
        }


      }
    });
    t.start();
    return t;
  }


  public Thread lock2() {
    Thread t = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          lock2.lockInterruptibly();
          try {
            Thread.sleep(500);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          lock1.lockInterruptibly();
          System.out.println(Thread.currentThread().getName() + ",执行完毕!");

        } catch (InterruptedException e) {
          e.printStackTrace();
        } finally {
          if (lock1.isHeldByCurrentThread()) {
            lock1.unlock();
          }
          if (lock2.isHeldByCurrentThread()) {
            lock2.unlock();
          }
          System.out.println(Thread.currentThread().getName() + ",退出");
        }
      }
    });
    t.start();
    return t;
  }

  public static void main(String[] args) {
    long time = System.currentTimeMillis();
    InterruptLock interruptLock = new InterruptLock();
    Thread thread1 = interruptLock.lock1();
    Thread thread2 = interruptLock.lock2();

    while (true) {
      if (System.currentTimeMillis() - time >= 3000) {
        thread2.interrupt();
      }
    }
  }
}
