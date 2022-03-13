package com.mike.thread;

import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Cpuload {
  public static long threshold = 1000 * 60 * 60 * 2;

  public static void main(String[] args) {

    ThreadPoolExecutor exector = new ThreadPoolExecutor(4, 1024, 0L,
        TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(1024));

    for (int i = 0; i < 4; i++) {

      exector.execute(new Runnable() {
        @Override
        public void run() {
          String str = "start";
          long t1 = System.currentTimeMillis();
          while (true) {

            str = MD53(str);
            if (System.currentTimeMillis() - t1 > threshold) {
              break;
            }
          }
        }
      });
    }

    exector.shutdown();

    while (true) {
      if (exector.isTerminated()) {
        System.out.println("所有的子线程都结束了！");
        //System.out.printf("count: " + aLong.get());
       // System.out.println("time: " + (System.currentTimeMillis() - startTime));
        break;
      }
    }

  }

  private static String MD53(String input) {
    if (input == null || input.length() == 0) {
      return null;
    }
    try {
      MessageDigest md5 = MessageDigest.getInstance("MD5");
      md5.update(input.getBytes());
      byte[] byteArray = md5.digest();

      StringBuilder sb = new StringBuilder();
      for (byte b : byteArray) {
        // 一个byte格式化成两位的16进制，不足两位高位补零
        sb.append(String.format("%02x", b));
      }
      return sb.toString();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return null;
  }
}
