package com.mike.rpc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServiceCenter implements Server {
    private static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private static final HashMap<String, Class> serviceRegistry = new HashMap<>();


    private boolean isRunning = false;
    private int port;

    public ServiceCenter(int port) {
        this.port = port;
    }

    @Override
    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(port));
        while (true) {
            executor.execute(new ServiceTask(serverSocket.accept()));
        }
    }

    @Override
    public void stop() {
        isRunning = false;
        executor.shutdown();
    }

    @Override
    public void register(Class serviceInterface, Class imple) {
        serviceRegistry.put(serviceInterface.getName(), imple);
    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public int getPort() {
        return port;
    }

    class ServiceTask implements Runnable {
        private final Socket client;

        public ServiceTask(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {

            try (ObjectOutputStream outputStream = new ObjectOutputStream(client.getOutputStream());
                 ObjectInputStream input = new ObjectInputStream(client.getInputStream());
            ) {
                //  input = new ObjectInputStream(client.getInputStream());
                String serviceName = input.readUTF();
                String methodName = input.readUTF();
                Class<?>[] parameterTypes = (Class<?>[]) input.readObject();
                Object[] arguments = (Object[]) input.readObject();
                Class serviceClass = serviceRegistry.get(serviceName);

                if (serviceClass == null) {
                    throw new ClassNotFoundException(serviceName);
                }

                Method method = serviceClass.getMethod(methodName, parameterTypes);
                Object result = method.invoke(serviceClass.newInstance(), arguments);
                //  ObjectOutputStream outputStream = new ObjectOutputStream(client.getOutputStream());
                outputStream.writeObject(result);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } finally {
                //
            }
        }
    }
}
