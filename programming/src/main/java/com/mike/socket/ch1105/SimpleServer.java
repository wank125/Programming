package com.mike.socket.ch1105;

import com.mike.socket.ch11.FlightFactory;
import com.mike.socket.ch11.FlightFactoryImpl;

import javax.naming.InitialContext;

public class SimpleServer {

    //rmiregistry 命令需要在target/classes目录下运行,要不然找不到注册类

    public static void main(String[] args) {
        try {
            System.setProperty("java.rmi.dgc.leaseValue", "3000");

            HelloServiceImpl service = new HelloServiceImpl();
            InitialContext namingContext = new InitialContext();

            namingContext.rebind("rmi:HelloService", service);
            System.out.println("服务器注册了HelloService对象");
            Thread.sleep(1000);
            namingContext.unbind("rmi:HelloService");
            System.out.println("服务器销毁了一个HelloService对象");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
