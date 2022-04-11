package com.mike.socket.ch06;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class HttpClient {
    public static void main(String[] args) throws IOException {

        URL.setURLStreamHandlerFactory(new EchoURLStreamHandlerFactory());
        URLConnection.setContentHandlerFactory(new EchoContentHandlerFactory());

        URL url = new URL("echo://localhost:8000");
        EchoURLConnection connection = (EchoURLConnection) url.openConnection();
        connection.setDoOutput(true);
        PrintWriter pw = new PrintWriter(connection.getOutputStream(), true);

        while (true) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String msg = br.readLine();
            pw.println(msg);
            String echoMsg = (String) connection.getContent();
            System.out.printf(echoMsg);

            if (echoMsg.equals("echo:byte")) {
                connection.disconnect();
            }
        }

//        URL url = new URL("https://www.baidu.com");
//        InputStream in = url.openStream();
//        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
//        byte[] buff = new byte[1024];
//        int len = -1;
//
//        while ((len = in.read(buff)) != -1) {
//            buffer.write(buff, 0, len);
//        }
//        System.out.print(new String(buffer.toByteArray()));
    }
}
