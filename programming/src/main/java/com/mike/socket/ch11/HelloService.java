package com.mike.socket.ch11;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

public interface HelloService extends Remote {

    public String echo(String msg) throws RemoteException;

    public Date getTime() throws RemoteException;
}
