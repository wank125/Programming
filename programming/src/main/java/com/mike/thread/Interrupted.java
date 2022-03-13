package com.mike.thread;



public class Interrupted {

  public static void main(String[] args) throws InterruptedException {
    Thread sleepThread = new Thread(new SleepRunner(), "SleepThread");
    sleepThread.setDaemon(true);

    Thread busyThread = new Thread(new BusyRunner(), "BusyThread");
    busyThread.setDaemon(true);

    sleepThread.start();
    busyThread.start();

    Thread.sleep(5000);
    sleepThread.interrupt();
    busyThread.interrupt();

    System.out.println(sleepThread.isInterrupted());
    System.out.println(busyThread.isInterrupted());
    Thread.sleep(2000);

  }

  static class SleepRunner implements Runnable {
    @Override
    public void run() {
      while (true) {
        try {
          Thread.sleep(10000);
        } catch (InterruptedException e) {
          e.printStackTrace();
          Thread.currentThread().isInterrupted();
        }
      }
    }
  }

  static class BusyRunner implements Runnable {
    @Override
    public void run() {
      while (true) {

      }
    }
  }
}
