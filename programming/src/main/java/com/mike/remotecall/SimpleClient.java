package com.mike.remotecall;

public class SimpleClient {


    public static void main(String[] args) throws Exception {

        //创建动态代理类实例
        HelloService helloService = (HelloService) ProxyFactory.getProxy(HelloService.class, "127.0.0.1", 8000);
        System.out.println(helloService.echo("Hello"));
        System.out.println(helloService.getTime());
    }
}
