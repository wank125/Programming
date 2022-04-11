package com.mike.socket.ch06;

import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

public class EchoURLStreamHandlerFactory implements URLStreamHandlerFactory {
    @Override
    public URLStreamHandler createURLStreamHandler(String protocol) {
        if (protocol.equals("echo")) {
            return new EchoURLStreamHandler();
        } else
            return null;
    }
}
