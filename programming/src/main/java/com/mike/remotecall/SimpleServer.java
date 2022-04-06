package com.mike.remotecall;

import com.mike.remotecall.struct.Call;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class SimpleServer {
    private Map<String, Object> remoteObjects = new HashMap();

    public void register(String className, Object remoteObject) {
        remoteObjects.put(className, remoteObject);
    }

    public void service() throws Exception {
        ServerSocket serverSocket = new ServerSocket(8000);
        System.out.println("服务器启动");

        while (true) {
            Socket socket = serverSocket.accept();
            InputStream in = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(in);
            OutputStream out = socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(out);

            Call call = (Call) ois.readObject();
            System.out.println(call);
            call = invoke(call);

            oos.writeObject(call);
            ois.close();
            oos.close();
            socket.close();
        }
    }

    public Call invoke(Call call) {
        Object result = null;
        try {
            String className = call.getClassName();
            String methodName = call.getMethodName();
            Object[] params = call.getParams();
            Class[] paramTypes = call.getParamTypes();

            Class<?> classType = Class.forName(className);
            Method method = classType.getMethod(methodName, paramTypes);
            Object remoteObject = remoteObjects.get(className);
            if (remoteObject == null) {
                throw new Exception(className + "的远程对象不存在");
            } else {
                result = method.invoke(remoteObject, params);
            }
        } catch (Exception e) {
            result = e;
        }

        call.setResult(result);
        return call;
    }

    public static void main(String[] args) throws Exception {
        SimpleServer simpleServer = new SimpleServer();
        simpleServer.register("com.mike.remotecall.HelloService", new HelloServiceImpl());
        simpleServer.service();
    }
}
