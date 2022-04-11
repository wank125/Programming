package com.mike.socket.ch06;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class HttpClient1 {
    public static void main(String[] args) throws IOException {
        URL url = new URL("https://www.baidu.com");
        InputStream in = url.openStream();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] buff = new byte[1024];
        int len = -1;

        while ((len = in.read(buff)) != -1) {
            buffer.write(buff, 0, len);
        }
        System.out.print(new String(buffer.toByteArray()));
    }
}
