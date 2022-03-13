package com.mike.thread;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class MyDelay implements Delayed {

  private long delayTime;
  private String name;

  public MyDelay(long delayTime, String name) {
    this.delayTime = delayTime * 1000 + System.currentTimeMillis();
    //this.delayTime = delayTime * 1000;
    this.name = name;
  }


  public long getDelayTime() {
    return delayTime;
  }

  public String getName() {
    return name;
  }

  @Override
  public long getDelay(TimeUnit unit) {

    return unit.convert((this.delayTime - System.currentTimeMillis()), TimeUnit.MILLISECONDS);
    //return unit.convert(this.delayTime, TimeUnit.MILLISECONDS);
  }

  @Override
  public int compareTo(Delayed o) {
    return (int) (this.delayTime - ((MyDelay) o).getDelayTime());
  }

  public static void main(String[] args) throws InterruptedException {
    DelayQueue<MyDelay> delayQueue = new DelayQueue<>();
    MyDelay delay;
    long delayTime;
    for (int i = 0; i < 10; i++) {
      delayTime = (long) (Math.random() * 100L);
      System.out.println(delayTime);
      delay = new MyDelay(delayTime, delayTime + "");
      delayQueue.put(delay);
    }

    for (int i = 0; i < 10; i++) {
      delay = delayQueue.take();
      System.out.println("name= " + delay.getName());
    }

  }
}
