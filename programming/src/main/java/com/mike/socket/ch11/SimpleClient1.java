package com.mike.socket.ch11;

import javax.naming.*;
import java.rmi.RemoteException;

public class SimpleClient1 {
    public static void showRemoteObjects(Context nameContext) throws NamingException {
        NamingEnumeration<NameClassPair> e = nameContext.list("rmi:");
        while (e.hasMore()) {
            System.out.println(e.next().getName());
        }
    }

    public static void main(String[] args) {
        String url = "rmi://localhost/";
        try {
            //         InitialContext namingContext = new InitialContext();
            // HelloService service1 = (HelloService) namingContext.lookup(url + "HelloService1");
            //  HelloService service2 = (HelloService) namingContext.lookup(url + "HelloService2");

//            //测试存根对象所属的类
//            Class<? extends HelloService> stubClass = service1.getClass();
//            System.out.println("service1 是" + stubClass.getName() + "的实例");
//            Class<?>[] interfaces = stubClass.getInterfaces();
//            for (Class i : interfaces) {
//                System.out.println("存根类实现了" + i.getName());
//            }
//            System.out.println(service1.echo("hello"));
//            System.out.println(service1.getTime());
//
//            System.out.println(service2.echo("hello"));
//            System.out.println(service2.getTime());
//
//            showRemoteObjects(namingContext);

            InitialContext namingContext = new InitialContext();
            FlightFactory factory = (FlightFactory) (namingContext.lookup(url + "FlightFactory"));
            Flight flight1 = factory.getFlight("795");
            flight1.setOrigin("Shanghai");
            flight1.setDestination("Beijing");
            System.out.println("Flight" + flight1.getFlightNumber() + ":");
            System.out.println("From " + flight1.getOrigin() + " to " + flight1.getDestination());



            Flight flight2 = factory.getFlight("795");
            flight2.setOrigin("Shanghai");
            flight2.setDestination("Beijing");
            System.out.println("Flight" + flight2.getFlightNumber() + ":");
            System.out.println("From " + flight2.getOrigin() + " to " + flight2.getDestination());

            System.out.println("flight1是"+flight1.getClass().getName()+"的实例");
            System.out.println("flight2是"+flight2.getClass().getName()+"的实例");

            System.out.println(flight1.equals(flight2));

        } catch (NamingException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
