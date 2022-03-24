package com.mike.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogHandler implements InvocationHandler {
    Logger logger = Logger.getLogger(this.getClass().getName());
    private Object originalObj;

    public Object bind(Object obj) {
        this.originalObj = obj;
        return Proxy.newProxyInstance(originalObj.getClass().getClassLoader(), originalObj.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Object result = null;
        logger.log(Level.INFO, "method starts.. " + method);
        result = method.invoke(originalObj, args);
        logger.log(Level.INFO, "methos ends..." + method);
        return result;
    }
}
