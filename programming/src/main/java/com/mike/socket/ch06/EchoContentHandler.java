package com.mike.socket.ch06;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ContentHandler;
import java.net.URLConnection;

public class EchoContentHandler extends ContentHandler {
    @Override
    public Object getContent(URLConnection urlc) throws IOException {

        InputStream in = urlc.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        return br.readLine();
    }

    @Override
    public Object getContent(URLConnection urlc, Class[] classes) throws IOException {
        InputStream in = urlc.getInputStream();
        for (int i = 0; i < classes.length; i++) {
            if (classes[i] == InputStream.class) {
                return in;
            } else if (classes[i] == String.class) {
                return getContent(urlc);
            }
        }
        return null;
    }
}
