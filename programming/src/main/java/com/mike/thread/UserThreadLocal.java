package com.mike.thread;

import java.util.function.Supplier;

class Resource {
    private static ThreadLocal<Integer> local = ThreadLocal.withInitial(new Supplier<Integer>() {
        @Override
        public Integer get() {
            return 1;
        }
    });

    public static int get() {
        return local.get();
    }

    public static void increment() {
        local.set(local.get() + 1);
    }

}

public class UserThreadLocal implements Runnable {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + ":" + Resource.get());
        Resource.increment();
        System.out.println(Thread.currentThread().getName() + ":" + Resource.get());
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(new UserThreadLocal()).start();
        }
    }
}


