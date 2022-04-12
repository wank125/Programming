package com.mike.socket.ch11;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class StockQuoteImpl extends UnicastRemoteObject implements StockQuote {

    protected StockQuoteImpl() throws RemoteException {
    }

    @Override
    public void quote(String stockSymbol, double price) throws RemoteException {
        System.out.println(stockSymbol + ":" + price);
    }
}
