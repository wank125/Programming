package com.mike.socket.ch11;

import javax.naming.*;
import java.rmi.RemoteException;

public class SimpleClient2 {
    public static void showRemoteObjects(Context nameContext) throws NamingException {
        NamingEnumeration<NameClassPair> e = nameContext.list("rmi:");
        while (e.hasMore()) {
            System.out.println(e.next().getName());
        }
    }

    public static void main(String[] args) {
        String url = "rmi://localhost/";
        try {
            InitialContext namingContext = new InitialContext();
            StockQuoteRegistry registry = (StockQuoteRegistry) namingContext.lookup(url + "StockQuoteRegistry");
            StockQuoteImpl client = new StockQuoteImpl();
            registry.registerClient(client);
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
