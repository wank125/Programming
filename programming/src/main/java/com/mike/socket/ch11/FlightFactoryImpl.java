package com.mike.socket.ch11;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class FlightFactoryImpl extends UnicastRemoteObject implements FlightFactory {
    protected HashMap<String, Flight> flights;

    public FlightFactoryImpl() throws RemoteException {
        this.flights = new HashMap<String, Flight>();
    }

    @Override
    public Flight getFlight(String flightNumber) throws RemoteException {
        Flight flight = flights.get(flightNumber);
        if (flight != null) return flight;

        flight = new FlightImpl(flightNumber, null, null, null, null);
        flights.put(flightNumber, flight);
        return flight;
    }
}
