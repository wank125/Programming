package com.mike.socket.ch04.noblock;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.LinkedList;

class Target {

    InetSocketAddress address;
    SocketChannel channel;
    Exception failure;
    long connectStart;
    long connectFinish = 0;
    boolean shown = false;

    public Target(String host) {
        try {
            address = new InetSocketAddress(InetAddress.getByName(host), 80);
        } catch (UnknownHostException e) {
            failure = e;
        }
    }

    void show() {
        String result;
        if (connectFinish != 0) {
            result = Long.toString(connectFinish - connectStart) + "ms";
        } else if (failure != null) {
            result = failure.toString();
        } else result = "Time out";

        System.out.println(address + ":" + result);
        shown = true;
    }
}

public class PingClient {

    private Selector selector;
    private LinkedList<Target> targets = new LinkedList();
    private LinkedList<Target> finishedTargets = new LinkedList();

    public PingClient() throws IOException {

        selector = Selector.open();
        Connector connector = new Connector();
        connector.start();

        Printer printer = new Printer();
        printer.start();

        receiveTargets();
    }

    public static void main(String[] args) throws IOException {
        new PingClient();
    }

    public void addTargets(Target target) {
        SocketChannel socketChannel = null;
        try {
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(target.address);

            target.channel = socketChannel;
            target.connectStart = System.currentTimeMillis();
            synchronized (targets) {
                targets.add(target);
            }

            selector.wakeup();

        } catch (IOException e) {
            if (socketChannel != null) {
                try {
                    socketChannel.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            target.failure = e;
            addFinishedTarget(target);
        }
    }

    private void addFinishedTarget(Target target) {
        synchronized (finishedTargets) {
            finishedTargets.notify();
            finishedTargets.add(target);
        }
    }


    private void printFinishTargets() {
        try {


            for (; ; ) {
                Target target = null;
                synchronized (finishedTargets) {
                    while (finishedTargets.size() == 0) {
                        finishedTargets.wait();
                    }
                    target = finishedTargets.removeFirst();
                }
                target.show();
            }
        } catch (InterruptedException e) {
            return;
        }
    }

    private void registerTargets() {
        synchronized (targets) {
            while (targets.size() > 0) {
                Target target = targets.removeFirst();
                try {
                    target.channel.register(selector, SelectionKey.OP_CONNECT,target);
                } catch (ClosedChannelException e) {
                    try {
                        target.channel.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    target.failure = e;
                    addFinishedTarget(target);
                }
            }
        }
    }

    private void processKeys() throws IOException {

        for (Iterator<SelectionKey> iterator = selector.selectedKeys().iterator(); iterator.hasNext(); ) {
            SelectionKey selectionKey = iterator.next();
            iterator.remove();
            Target target = (Target) selectionKey.attachment();
            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
            try {
                if (socketChannel.finishConnect()) {
                    selectionKey.channel();
                    target.connectFinish = System.currentTimeMillis();
                    socketChannel.close();
                    addFinishedTarget(target);
                }
            } catch (IOException e) {
                socketChannel.close();
                target.failure = e;
                addFinishedTarget(target);
            }
        }
    }

    private void receiveTargets() {
        try {
            BufferedReader localReader = new BufferedReader(new InputStreamReader(System.in));
            String msg = null;
            while ((msg = localReader.readLine()) != null) {
                if (!msg.equals("bye")) {
                    Target target = new Target(msg);
                    addTargets(target);
                } else {
                    shutDown = true;
                    selector.wakeup();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    class Printer extends Thread {
        public Printer() {
            setDaemon(true);
        }

        @Override
        public void run() {
            // super.run();
            printFinishTargets();
        }
    }

    private boolean shutDown = false;

    class Connector extends Thread {

        @Override
        public void run() {
            while (!shutDown) {
                try {
                    registerTargets();
                    if (selector.select() > 0) {
                        processKeys();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}






