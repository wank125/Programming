package com.mike.collection;

import java.util.LinkedList;

public class MyStack<E> {

    private LinkedList<E> ll = new LinkedList<>();

    public E pop() {
        return ll.removeLast();
    }

    public void push(E obj) {
        ll.addLast(obj);
    }

    public boolean empty() {
        return ll.isEmpty();
    }

    public E peek() {
        return ll.getLast();
    }
}
