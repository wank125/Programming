package com.mike.rpc;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Objects;

public class RPCClient {
    public static Object getRemoteProxyObject(Object serviceInterface, InetSocketAddress address) {
        System.out.println(serviceInterface.getClass());
        return Proxy.newProxyInstance(serviceInterface.getClass().getClassLoader(), serviceInterface.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                ObjectOutputStream output = null;
                ObjectInputStream input = null;
                try (
                        Socket socket = new Socket();
                ) {

                    socket.connect(address);
                    //服务编码发送到服务端
                    output = new ObjectOutputStream(socket.getOutputStream());
                    //实现了多个接口
                    output.writeUTF(serviceInterface.getClass().getInterfaces()[0].getName());
                    // output.writeUTF(serviceInterface.getClass().getName());
                    output.writeUTF(method.getName());
                    output.writeObject(method.getParameterTypes());
                    output.writeObject(args);



                    //阻塞等待服务器返回
                    input = new ObjectInputStream(socket.getInputStream());
                    return input.readObject();
                } finally {
                    if (output != null) {
                        output.close();
                    }
                    if (input != null) {
                        input.close();
                    }
                }

            }
        });
    }
}
