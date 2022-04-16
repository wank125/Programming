package com.mike.socket.ch13;

public class StoreException extends Exception {
    public StoreException() {
        this("StoreException");
    }

    public StoreException(String message) {
        super(message);
    }
}
