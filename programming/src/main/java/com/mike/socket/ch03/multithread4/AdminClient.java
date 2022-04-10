package com.mike.socket.ch03.multithread4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class AdminClient {
    public static void main(String[] args) {
        Socket socket = null;
        try {
            socket = new Socket("localhost", 8001);
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write("shutdown\r\n".getBytes());

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String msg = null;

            while ((msg = reader.readLine()) != null) {
                System.out.printf(msg);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
