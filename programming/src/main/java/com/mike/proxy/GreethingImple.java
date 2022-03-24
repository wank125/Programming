package com.mike.proxy;

public class GreethingImple implements Greeting {
    @Override
    public void sayHello(String name) {
        System.out.println("hello," + name);
    }
}
