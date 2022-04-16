package com.mike.socket.ch13;

public interface StoreController {

    public void handleGetCustomerGesture(long id);

    public void handleAddCustomerGesture(Customer c);

    public void handleDeleteCustomerGesture(Customer c);

    public void handleUpdateCustomerGesture(Customer c);

    public void handleGetAllCustomersGesture();
}
