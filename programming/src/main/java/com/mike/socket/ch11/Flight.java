package com.mike.socket.ch11;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Flight extends Remote {

    String getFlightNumber() throws RemoteException;

    String getOrigin() throws RemoteException;

    String getDestination() throws RemoteException;

    String getSkdDeparture() throws RemoteException;

    String getSkdArrival() throws RemoteException;

    void setOrigin(String origin) throws RemoteException;

    void setDestination(String destination) throws RemoteException;

    void setSkdDeparture(String skdDeparture) throws RemoteException;

    void setSkdArrival(String skdArrival) throws RemoteException;
}
