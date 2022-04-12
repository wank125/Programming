package com.mike.socket.ch11;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface StockQuoteRegistry extends Remote {

    public void registerClient(StockQuote client) throws RemoteException;

    public void unregisterClient(StockQuote client) throws RemoteException;

}
