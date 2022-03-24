package com.mike.proxy;

public class Client {
    public static void welcome(Greeting greeting, String name) {
        greeting.sayHello(name);
    }

    public static void main(String[] args) {
        GreethingImple greething = new GreethingImple();
        //不使用代理
        welcome(greething, "zhangsan");
        System.out.println("-----------");

        //使用代理
        GreetingProxy greetingProxy = new GreetingProxy(greething);
        welcome(greetingProxy, "lisi");


        Greeting bind = (Greeting) new LogHandler().bind(greething);
        bind.sayHello("wangwu");

    }
}
