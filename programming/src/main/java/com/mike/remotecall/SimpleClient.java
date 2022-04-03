package com.mike.remotecall;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SimpleClient {

    public void invoke() throws Exception {
        Socket socket = new Socket("localhost", 8000);
        OutputStream out = socket.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(out);
        InputStream in = socket.getInputStream();

        ObjectInputStream ois = new ObjectInputStream(in);
        Call call = new Call("com.mike.remotecall.HelloService", "echo",
                new Class[]{String.class}, new Object[]{"Hello"});
        oos.writeObject(call);
        call = (Call) ois.readObject();
        System.out.println(call.getResult());

        oos.close();
        ois.close();
        socket.close();
    }

    public static void main(String[] args) throws Exception {
        new SimpleClient().invoke();
    }
}
