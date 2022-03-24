package com.mike.proxy;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GreetingProxy implements Greeting {
    Logger logger = Logger.getLogger(this.getClass().getName());
    private Greeting greetingObj;

    public GreetingProxy(Greeting greetingObj) {
        this.greetingObj = greetingObj;
    }

    @Override
    public void sayHello(String name) {
        logger.log(Level.INFO, "asyHello method starts..");
        greetingObj.sayHello(name);
        logger.log(Level.INFO, "sayHello method ends");
    }
}
