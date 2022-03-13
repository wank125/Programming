package com.mike.thread;

import java.awt.image.ImageObserver;
import java.util.concurrent.TimeUnit;

public class Profiler {
  private static final ThreadLocal<Long> TIME_THREADLOCAL = new ThreadLocal<Long>() {
    @Override
    protected Long initialValue() {
      return System.currentTimeMillis();
    }
  };


  public static final void begin() {
    TIME_THREADLOCAL.set(System.currentTimeMillis());

  }

  public static final Long end() {
    return System.currentTimeMillis() - TIME_THREADLOCAL.get();
  }

  public static void main(String[] args) throws InterruptedException {
    Profiler.begin();
    TimeUnit.SECONDS.sleep(1);
    System.out.println("Cost: " + Profiler.end() + " mills");
  }

}
