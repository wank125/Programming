package com.mike.socket.ch11;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class FlightImpl extends UnicastRemoteObject implements Flight {

    private String flightNumber;
    private String origin;
    private String destination;

    private String skdDeparture;
    private String skdArrival;

    public FlightImpl(String flightNumber, String origin, String destination, String skdDeparture, String skdArrival) throws RemoteException {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.skdDeparture = skdDeparture;
        this.skdArrival = skdArrival;
    }

    @Override
    public String getFlightNumber() throws RemoteException {
        System.out.println("调用getFlightNumber(),返回" + flightNumber);
        return flightNumber;
    }

    @Override
    public String getOrigin() throws RemoteException {
        System.out.println("调用getOrigin(),返回" + origin);
        return origin;
    }

    @Override
    public String getDestination() throws RemoteException {
        System.out.println("调用getDestination() ,返回" + destination);
        return destination;
    }

    @Override
    public String getSkdDeparture() throws RemoteException {
        System.out.println("调用getskdDeparture(),返回" + skdDeparture);
        return skdDeparture;
    }

    @Override
    public String getSkdArrival() throws RemoteException {
        System.out.println("调用getSkdArrival(),返回" + skdArrival);
        return skdArrival;
    }

    @Override
    public void setOrigin(String origin) throws RemoteException {

        this.origin = origin;
    }

    @Override
    public void setDestination(String destination) throws RemoteException {
        this.destination = destination;
    }

    @Override
    public void setSkdDeparture(String skdDeparture) throws RemoteException {
        this.skdDeparture = skdDeparture;
    }

    @Override
    public void setSkdArrival(String skdArrival) throws RemoteException {
        //
        // return null;
        this.skdArrival = skdArrival;
    }
}
