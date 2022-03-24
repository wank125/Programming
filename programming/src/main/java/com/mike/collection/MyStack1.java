package com.mike.collection;

import java.util.ArrayList;

public class MyStack1<E> {

    private ArrayList<E> stack = new ArrayList<>();

    public void push(E e) {
        stack.add(e);
    }

    public E pop() {
        if (!isEmpty()) {
            E value = stack.get(stack.size() - 1);
            stack.remove(stack.size() - 1);
            return value;
        }
        return null;
    }

    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    public int size() {
        return this.stack.size();
    }

    public static void main(String[] args) {
        MyStack1<Integer> integerMyStack1 = new MyStack1<>();

        integerMyStack1.push(1);
        integerMyStack1.push(2);
        for (int i = 0; i < 2; i++) {
            System.out.println(integerMyStack1.pop());
        }
    }
}
