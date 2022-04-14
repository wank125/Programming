package com.mike.socket.ch11;

import javax.naming.*;
import java.rmi.RemoteException;

/**
 * 客户端并发访问
 */
public class SimpleClient3 {
    public static void showRemoteObjects(Context nameContext) throws NamingException {
        NamingEnumeration<NameClassPair> e = nameContext.list("rmi:");
        while (e.hasMore()) {
            System.out.println(e.next().getName());
        }
    }

    public static void main(String[] args) {
        String url = "rmi://localhost/";
        try {
            InitialContext namingContext = new InitialContext();
            Stack stack = (Stack) namingContext.lookup(url + "MyStack");
            Producer producer1 = new Producer(stack, "producer1");
            Producer producer2 = new Producer(stack, "producer2");
            Consumer consumer1 = new Consumer(stack, "consumer1");
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

class Consumer extends Thread {
    private Stack theStack;

    public Consumer(Stack theStack, String name) {
        super(name);
        this.theStack = theStack;
        start();
    }

    @Override
    public void run() {
        String goods;
        try {
            for (; ; ) {
                goods = theStack.pop();
                System.out.println(getName() + ":pop " + goods + " from " + theStack.getName());
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}


class Producer extends Thread {
    private Stack theStack;

    public Producer(Stack stack, String name) {
        super(name);
        this.theStack = stack;
        start();
    }

    @Override
    public void run() {
        String goods;
        try {
            for (; ; ) {
                synchronized (theStack) {
                    goods = "goods" + (theStack.getPoint() + 1);
                    theStack.push(goods);
                }
                System.out.println(getName() + ":push " + goods + " to " + theStack.getName());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
