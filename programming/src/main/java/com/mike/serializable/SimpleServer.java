package com.mike.serializable;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServer {

    public void send(Object object) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8000);
        while (true) {
            Socket socket = serverSocket.accept();
            OutputStream out = socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(out);
            oos.writeObject(object);
            oos.writeObject(object);
            oos.close();
            socket.close();
        }
    }

    public static void main(String[] args) throws IOException {
        Object object = null;
        object = new Customer3();

        System.out.println("待发送的对象的信息:" + object);
        new SimpleServer().send(object);
    }
}
