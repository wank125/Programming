package com.mike.remotecall.client;

import com.mike.remotecall.HelloService;
import com.mike.remotecall.ProxyFactory;

public class SimpleNettyClient {
    public static void main(String[] args) throws Exception {

        //创建动态代理类实例
        HelloService helloService = (HelloService) ProxyFactory.getProxy(HelloService.class, "localhost", 8000);
        System.out.println(helloService.echo("Hello"));
        System.out.println(helloService.getTime());
    }
}
