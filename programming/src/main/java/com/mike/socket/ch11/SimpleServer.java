package com.mike.socket.ch11;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.rmi.RemoteException;

public class SimpleServer {

    //rmiregistry 命令需要在target/classes目录下运行,要不然找不到注册类

    public static void main(String[] args) {
        try {
             // HelloService service1 = new HelloServiceImpl("service1");
            //  HelloService service2 = new HelloServiceImpl("service2");
            // InitialContext namingContext = new InitialContext();
            // namingContext.rebind("rmi:HelloService1", service1);
            // namingContext.rebind("rmi:HelloService2", service2);
          //  System.out.println("服务器注册了两个HelloService对象");
           //工厂方式

            FlightFactory factory = new FlightFactoryImpl();
            InitialContext namingContext = new InitialContext();
            namingContext.rebind("rmi:FlightFactory", factory);
            System.out.println("服务器注册了FlightFactory对象");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
