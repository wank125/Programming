package com.mike.socket.ch1105;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HelloService extends Remote {
    boolean isAccessed() throws RemoteException;

    void access() throws RemoteException;

    void bye() throws RemoteException;
}
