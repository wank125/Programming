package com.mike.thread;

public interface ThreadPool<Job extends Runnable> {
  void execute(Job job);

  void shutdown();

  void addWork(int num);

  void removeWorker(int num);

  int getJobSize();
}
