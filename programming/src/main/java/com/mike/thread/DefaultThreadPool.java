package com.mike.thread;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class DefaultThreadPool<Job extends Runnable> implements ThreadPool<Job> {

  private static final int MAX_WORKER_NUMBERS = 10;
  private static final int DEFAULT_WORKER_NUMBERS = 5;
  private static final int Min_WORKER_NUMBERS = 1;

  private final LinkedList<Job> jobs = new LinkedList<Job>();
  private final List<Worker> workers = Collections.synchronizedList(new ArrayList<Worker>());
  private int workNum = DEFAULT_WORKER_NUMBERS;

  private AtomicLong threadNum = new AtomicLong();

  public DefaultThreadPool() {
    initializeWokers(DEFAULT_WORKER_NUMBERS);
  }

  public DefaultThreadPool(int num) {

    workNum = num > MAX_WORKER_NUMBERS ? MAX_WORKER_NUMBERS : num < Min_WORKER_NUMBERS ? Min_WORKER_NUMBERS : num;
    initializeWokers(workNum);
  }

  private void initializeWokers(int num) {
    for (int i = 0; i < num; i++) {
      Worker worker = new Worker();
      workers.add(worker);
      Thread thread = new Thread(worker, "ThreadPool-Work-" + threadNum.incrementAndGet());
      thread.start();
    }
  }

  class Worker implements Runnable {

    private volatile boolean running = true;

    @Override
    public void run() {
      while (running) {
        Job job = null;
        synchronized (jobs) {
          while (jobs.isEmpty()) {
            try {
              jobs.wait();
            } catch (InterruptedException e) {
              Thread.currentThread().interrupt();
              return;
            }
          }
          job = jobs.removeFirst();

        }
        if (job != null) {
          try {
            job.run();
          } catch (Exception e) {
            e.printStackTrace();
          }

        }
      }
    }

    public void shutdown() {
      running = false;
    }
  }

  @Override
  public void execute(Job job) {
    if (job != null) {
      synchronized (jobs) {
        jobs.addLast(job);
        jobs.notify();
      }
    }
  }

  @Override
  public void shutdown() {
    for (Worker worker : workers) {
      worker.shutdown();
    }

  }

  @Override
  public void addWork(int num) {

    synchronized (jobs) {
      if (num + this.workNum > MAX_WORKER_NUMBERS) {
        num = MAX_WORKER_NUMBERS - this.workNum;
      }
      initializeWokers(num);
      this.workNum += num;
    }
  }

  @Override
  public void removeWorker(int num) {
    synchronized (jobs) {
      if (num >= this.workNum) {
        throw new IllegalArgumentException("beyond workNum");
      }
      int count = 0;
      while (count < num) {

        Worker worker = workers.get(count);
        if (workers.remove(worker)) {
          worker.shutdown();
          count++;
        }
      }
      this.workNum -= count;
    }
  }

  @Override
  public int getJobSize() {
    return jobs.size();
  }


  public static void main(String[] args) {
    DefaultThreadPool<Runnable> threadPool = new DefaultThreadPool<>();
    threadPool.execute(new Runnable() {
      @Override
      public void run() {
        System.out.println("---");
      }
    });
  }
}
