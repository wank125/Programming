package com.mike.socket.ch06;

import java.net.ContentHandler;
import java.net.ContentHandlerFactory;

public class EchoContentHandlerFactory implements ContentHandlerFactory {
    @Override
    public ContentHandler createContentHandler(String mimetype) {
        if (mimetype.equals("text/plain")) {
            return new EchoContentHandler();
        } else {
            return null;
        }
    }
}
