package com.mike.remotecall.client;

import com.mike.remotecall.HelloService;
import com.mike.remotecall.ProxyFactory;

import java.util.Date;

public class SimpleNettyClient {
    public static void main(String[] args) throws Exception {

        //创建动态代理类实例
        HelloService helloService = (HelloService) ProxyFactory.getProxy(HelloService.class, "localhost", 8000);
        String hello = helloService.echo("hello");
        System.out.println(hello);
        Date time = helloService.getTime();
        System.out.println(time);
    }
}
