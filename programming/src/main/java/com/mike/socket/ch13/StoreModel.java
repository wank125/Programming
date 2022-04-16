package com.mike.socket.ch13;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

public interface StoreModel extends Remote {

    public void addChangeListener(StoreView sv) throws StoreException, RemoteException;

    public void addCustomer(Customer customer) throws StoreException, RemoteException;

    public void deleteCustomer(Customer customer) throws StoreException, RemoteException;

    public Customer getCustomer(long id) throws StoreException, RemoteException;

    public Set<Customer> getAllCustomers() throws StoreException, RemoteException;
}
