package com.mike.socket.ch13;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface StoreView extends Remote {

    public void addUserGestureListener(StoreController controller) throws StoreException, RemoteException;

    public void showDisplay(Object display) throws StoreException, RemoteException;

    public void handleCustomerChange(Customer customer) throws StoreException, RemoteException;
}
