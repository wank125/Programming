package com.mike.socket.ch11;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FlightFactory extends Remote {
    public Flight getFlight(String flightNumber) throws RemoteException;
}
