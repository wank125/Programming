package com.mike.thread;

import java.util.Random;
import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueDemo {

  public static void main(String[] args) {
    SynchronousQueue<Integer> queue = new SynchronousQueue<Integer>();
    new Producer(queue).start();
    new Customer(queue).start();
  }


  static class Producer extends Thread {
    SynchronousQueue<Integer> queue;

    public Producer(SynchronousQueue<Integer> queue) {
      this.queue = queue;
    }

    @Override
    public void run() {

      while (true) {

        try {
          int product = new Random().nextInt(1000);
          queue.put(product);
          System.out.println("product a data:" + product);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        System.out.println(queue.isEmpty());
      }
    }
  }


  static class Customer extends Thread {
    SynchronousQueue<Integer> queue;

    public Customer(SynchronousQueue<Integer> queue) {
      this.queue = queue;
    }

    @Override
    public void run() {

      while (true) {

        try {

          Integer data = queue.take();
          System.out.println("customer a data:" + data);

        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        System.out.println(queue.isEmpty());
      }
    }
  }

}
