package com.mike.collection;

import java.util.LinkedList;

public class Usequeue {
    public static void main(String[] args) {
        LinkedList<Integer> q = new LinkedList<>();
        q.offer(11);
        q.offerLast(22);
        q.offer(33);

        System.out.println(q);
    }
}
