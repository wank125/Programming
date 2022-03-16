package com.mike.lock;

import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BankWateService implements Runnable {
  private CyclicBarrier c = new CyclicBarrier(2, this);


  private Executor executor = Executors.newFixedThreadPool(4);

  ConcurrentHashMap<String, Integer> sheetBankWaterCount = new ConcurrentHashMap<String, Integer>();

  private void count() {

    for (int i = 0; i < 4; i++) {
      executor.execute(new Runnable() {
        @Override
        public void run() {
          sheetBankWaterCount.put(Thread.currentThread().getName(), 1);
          try {
            c.await();
          } catch (InterruptedException e) {
            e.printStackTrace();
          } catch (BrokenBarrierException e) {
            e.printStackTrace();
          }
        }
      });
    }
  }


  @Override
  public void run() {

    int result = 0;
    for (Map.Entry<String, Integer> sheet : sheetBankWaterCount.entrySet()) {
      result += sheet.getValue();
    }
    sheetBankWaterCount.put("result", result);
    System.out.println(result);
  }

  public static void main(String[] args) {
    BankWateService bankWateService = new BankWateService();
    bankWateService.count();
  }
}
