package com.mike.socket.ch11;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface StockQuote extends Remote {
    public void quote(String stockSymbol, double price) throws RemoteException;
}
