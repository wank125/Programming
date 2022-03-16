package com.mike.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class SemaphoreTest {
  private static final int THREAD_COUNT = 30;
  private static ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_COUNT);

  private static Semaphore s = new Semaphore(10);
  private static AtomicInteger atomicInteger = new AtomicInteger();

  public static void main(String[] args) {

    for (int i = 0; i < THREAD_COUNT; i++) {
      threadPool.execute(new Runnable() {
        @Override
        public void run() {
          try {
            s.acquire();
            atomicInteger.incrementAndGet();
            System.out.println(atomicInteger.get() + "save data");
            s.release();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }

        }
      });

    }
    threadPool.shutdown();
  }
}
