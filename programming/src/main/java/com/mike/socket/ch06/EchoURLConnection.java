package com.mike.socket.ch06;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;

public class EchoURLConnection extends URLConnection {
    /**
     * Constructs a URL connection to the specified URL. A connection to
     * the object referenced by the URL is not created.
     *
     * @param url the specified URL.
     */
    protected EchoURLConnection(URL url) {
        super(url);
    }


    private Socket connection = null;
    private final static int DEFAULT_PORT = 8000;


    public synchronized InputStream getInputStream() throws IOException {
        if (!connected) connect();
        return connection.getInputStream();
    }

    @Override
    public synchronized OutputStream getOutputStream() throws IOException {
        if (!connected)
            connect();
        return connection.getOutputStream();
    }

    @Override
    public synchronized void connect() throws IOException {
        if (!connected) {
            int port = url.getPort();
            if (port < 0 || port > 65535) {
                port = DEFAULT_PORT;
            }
            this.connection = new Socket(url.getHost(), port);
            this.connected = true;
        }
    }

    @Override
    public String getContentType() {
        return "text/plain";
    }

    public synchronized void disconnect() throws IOException {
        if (connected) {
            this.connection.close();
            this.connected = false;
        }
    }
}
