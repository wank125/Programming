package com.mike.socket.ch11;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class StackImpl extends UnicastRemoteObject implements Stack {
    private String name;
    private String[] buffer = new String[100];

    int point = -1;

    public StackImpl(String name) throws RemoteException {
        //super();
        this.name = name;
    }

    @Override
    public String getName() throws RemoteException {
        return name;
    }

    @Override
    public synchronized int getPoint() throws RemoteException {
        return point;
    }

    @Override
    public synchronized String pop() throws RemoteException {
        this.notifyAll();
        while (point == -1) {
            System.out.println(Thread.currentThread().getName() + ":wait");
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String goods = buffer[point];
        buffer[point] = null;
        Thread.yield();
        point--;
        return goods;
    }

    @Override
    public synchronized void push(String goods) throws RemoteException {
        this.notifyAll();
        while (point == buffer.length - 1) {
            System.out.println(Thread.currentThread().getName() + ":wait");
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        point++;
        Thread.yield();
        buffer[point] = goods;
    }
}
