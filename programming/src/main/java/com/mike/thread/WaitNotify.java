package com.mike.thread;

import java.io.PipedWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class WaitNotify {
  static boolean flag = true;
  static Object lock = new Object();

  public static void main(String[] args) throws InterruptedException {
    Thread waitThread = new Thread(new Wait(), "WaitThread");
    waitThread.start();

    TimeUnit.SECONDS.sleep(1);
    Thread notifyThread = new Thread(new Wait.Notify(), "NotifyThread");
    notifyThread.start();

  }

  static class Wait implements Runnable {
    @Override
    public void run() {
      synchronized (lock) {
        while (flag) {
          System.out.println(Thread.currentThread() + "flag is true. waite @"
              + new SimpleDateFormat("HH:mm:ss").format(new Date()));
          try {
            lock.wait();
            System.out.println("---------");
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          System.out.println(Thread.currentThread() + "flag is false. running @"
              + new SimpleDateFormat("HH:mm:ss").format(new Date()));
        }
      }
    }

    static class Notify implements Runnable {
      @Override
      public void run() {
        synchronized (lock) {
          System.out.println(Thread.currentThread() + "hold lock. notify @"
              + new SimpleDateFormat("HH:mm:ss").format(new Date()));

          lock.notifyAll();
          flag = false;
          try {
            TimeUnit.SECONDS.sleep(5);
            System.out.println("---");
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
        synchronized (lock) {
          System.out.println(Thread.currentThread() + "hold lock again. sleep @"
              + new SimpleDateFormat("HH:mm:ss").format(new Date()));
          try {
            TimeUnit.SECONDS.sleep(5);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    }
  }
}
