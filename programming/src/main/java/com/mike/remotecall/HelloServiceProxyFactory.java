package com.mike.remotecall;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class HelloServiceProxyFactory {

    public static HelloService getHelloServiceProxy(final HelloService helloService) {
        InvocationHandler handler = new InvocationHandler() {

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("before calling" + method);
                Object result = method.invoke(helloService, args);
                System.out.println("after calling" + method);
                return result;
            }
        };
        Class<HelloService> classType = HelloService.class;
        return (HelloService) Proxy.newProxyInstance(classType.getClassLoader(),
                new Class[]{classType}, handler);
    }

}
