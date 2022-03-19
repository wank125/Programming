package com.mike.thread;

import java.sql.Connection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionPoolTest {
  static ConnectionPool pool = new ConnectionPool(10);
  static CountDownLatch start = new CountDownLatch(1);
  static CountDownLatch end;

  public static void main(String[] args) throws InterruptedException {
    int thredCount = 10;
    end = new CountDownLatch(thredCount);

    int count = 20;
    AtomicInteger got = new AtomicInteger();
    AtomicInteger notGot = new AtomicInteger();
    for (int i = 0; i < thredCount; i++) {
      Thread thread = new Thread(new ConnectionRunner(count, got, notGot), "ConnectionRunnerThread");
      thread.start();
    }
    start.countDown();
    end.await();
    System.out.println("total invoke:" + (thredCount * count));
    System.out.println("got connection: " + got);
    System.out.println("not got connection " + notGot);
  }

  static class ConnectionRunner implements Runnable {
    int count;
    AtomicInteger got;
    AtomicInteger notgot;

    public ConnectionRunner(int count, AtomicInteger got, AtomicInteger notgot) {
      this.count = count;
      this.got = got;
      this.notgot = notgot;
    }

    @Override
    public void run() {

//      Boolean match = false;
//      if (str != null && (str.length() != 0)) {
//        match = Pattern.matches(pattern, str);
//      }
//


      try {
        start.await();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      while (count > 0) {
        try {
          Connection connection = pool.fetchConnection(1000);
          if (connection != null) {
            try {
              connection.createStatement();
              connection.commit();
            } finally {
              pool.releaseConnection(connection);
              got.incrementAndGet();
            }


          } else {
            notgot.incrementAndGet();
          }
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          count--;
        }
      }
      end.countDown();
    }
  }
}
