package com.mike.socket.ch1105;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.server.Unreferenced;

public class HelloServiceImpl extends UnicastRemoteObject implements HelloService, Unreferenced {

    private boolean isAccessed = false;

    public HelloServiceImpl() throws RemoteException {
    }

    @Override
    public boolean isAccessed() throws RemoteException {
        return isAccessed;
    }

    @Override
    public void access() throws RemoteException {
        System.out.println("HelloServiceImpl:一个客户远程引用");
        isAccessed = true;
    }

    @Override
    public void bye() throws RemoteException {
        System.out.println("HelloServiceImpl:一个客户不在引用我");
    }

    @Override
    public void unreferenced() {
        System.out.println("HelloServiceImpl:我不再被引用");
    }
}
