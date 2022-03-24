package com.mike.rpc;

import java.io.IOException;
import java.net.InetSocketAddress;

public class RPCTest {

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                ServiceCenter serviceCenter = new ServiceCenter(8080);
                serviceCenter.register(HelloService.class, HelloServiceImpl.class);
                try {
                    serviceCenter.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        HelloService localhost = (HelloService) RPCClient.getRemoteProxyObject(HelloService.class, new InetSocketAddress("localhost", 8080));
        System.out.println(localhost.sayHi("test"));

    }
}
