package com.mike.socket.ch06;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

public class EchoURLStreamHandler extends URLStreamHandler {

    public int getDefaultPort() {
        return 8000;
    }

    @Override
    protected URLConnection openConnection(URL u) throws IOException {
        return new EchoURLConnection(u);
    }
}
