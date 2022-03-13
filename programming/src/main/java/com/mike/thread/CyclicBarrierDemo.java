package com.mike.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {
  public static void main(String[] args) {
    CyclicBarrier cyclicBarrier = new CyclicBarrier(4);
    for (int i = 0; i < 4; i++) {
      new BusinessThread(cyclicBarrier).start();
    }
  }

  static class BusinessThread extends Thread {
    private CyclicBarrier cyclicBarrier;

    public BusinessThread(CyclicBarrier cyclicBarrier) {
      this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
      try {
        Thread.sleep(5000);
        System.out.println(Thread.currentThread().getName() + "线程执行前准备工作完成，等待其他线程准备工作");
        cyclicBarrier.await();
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (BrokenBarrierException e) {
        e.printStackTrace();
      }

      System.out.println(Thread.currentThread().getName() + "所有线程准备工作均完成");
    }
  }
}



