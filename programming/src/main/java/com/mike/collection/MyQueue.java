package com.mike.collection;

import java.util.LinkedList;

public class MyQueue<E> {

    private LinkedList<E> ll = new LinkedList<>();

    public void offer(E obj) {
        ll.offerLast(obj);
    }

    public E poll() {
        return ll.pollFirst();
    }

    public boolean empty() {
        return ll.isEmpty();
    }

    public E peek() {
        return ll.peekFirst();
    }

    public int size() {
        return ll.size();
    }
}
