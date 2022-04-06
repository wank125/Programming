package com.mike.remotecall;

import com.mike.remotecall.client.NettyClientConnector;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.rmi.RemoteException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ProxyFactory {

    public static Object getProxy(final Class classType, final String host, final int port) {

        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                NettyClientConnector connector = new NettyClientConnector(host,port);
                try {
                    connector.connect();
                    Call call = new Call(classType.getName(), method.getName(), method.getParameterTypes(), args);
                    connector.send(call);
                    call = (Call) connector.receive();
                    Object result = call.getResult();
                    if (result instanceof Throwable) {
                        throw new RemoteException("", (Throwable) result);
                    }
                    return result;
                } finally {
                    if (connector != null) {
                        connector.close();
                    }
                }
            }
        };

        return Proxy.newProxyInstance(classType.getClassLoader(), new Class[]{classType}, handler);
    }
}
