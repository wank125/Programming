package com.mike.remotecall;

import java.io.*;
import java.net.Socket;

public class Connector {

    private String host;
    private int port;

    private Socket skt;
    private InputStream is;
    private ObjectInputStream ois;
    private ObjectOutput out;
    private ObjectOutputStream oos;

    public Connector(String host, int port) throws IOException {
        this.host = host;
        this.port = port;
        connect(host, port);
    }

    public void connect(String host, int port) throws IOException {
        skt = new Socket(host, port);
        OutputStream outputStream = skt.getOutputStream();
        InputStream inputStream = skt.getInputStream();

        oos = new ObjectOutputStream(outputStream);
        ois = new ObjectInputStream(inputStream);
    }

    public void send(Object obj) throws IOException {
        oos.writeObject(obj);
    }

    public Object receive() throws Exception {
        return ois.readObject();
    }

    public void connect() throws IOException {
        connect(host, port);
    }

    public void close() {
        try {

        } finally {
            try {
                ois.close();
                oos.close();
                skt.close();
            } catch (Exception e) {
                System.out.println("Connector.close:" + e);
            }
        }
    }
}
