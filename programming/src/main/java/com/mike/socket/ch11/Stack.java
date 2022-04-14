package com.mike.socket.ch11;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Stack extends Remote {

    String getName() throws RemoteException;

    int getPoint() throws RemoteException;

    String pop() throws RemoteException;

    void push(String goods) throws RemoteException;
}
