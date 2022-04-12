package com.mike.socket.ch11;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

public class StockQuoteRegistryImpl extends UnicastRemoteObject implements StockQuoteRegistry, Runnable {

    protected HashSet<StockQuote> clients;

    protected StockQuoteRegistryImpl() throws RemoteException {
        clients = new HashSet<StockQuote>();
    }

    @Override
    public void registerClient(StockQuote client) throws RemoteException {
        System.out.println("加入一个客户");
        clients.add(client);
    }

    @Override
    public void unregisterClient(StockQuote client) throws RemoteException {
        System.out.println("删除一个客户");
        clients.remove(client);
    }

    @Override
    public void run() {
        String[] symbols = new String[]{"SUNW", "MSFT", "DAL", "WUTK", "SAMY", "KATY"};
        Random random = new Random();
        double values[] = new double[symbols.length];

        for (int i = 0; i < values.length; i++) {
            values[i] = 25.0 + random.nextInt(100);
        }

        for (; ; ) {
            int sym = random.nextInt(symbols.length);
            int change = 100 - random.nextInt(201);
            values[sym] = values[sym] + ((double) change) / 100.0;
            if (values[sym] < 0) values[sym] = 0.01;
            Iterator<StockQuote> iter = clients.iterator();
            while (iter.hasNext()) {
                StockQuote client = iter.next();
                try {
                    //回调客户端
                    client.quote(symbols[sym], values[sym]);
                } catch (Exception e) {
                    System.out.println("删除一个无效的客户");
                    iter.remove();
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
