package com.mike.collection;

import java.util.HashSet;

public class UseHashSet {
    public static void main(String[] args) {
        HashSet<Integer> hs = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            hs.add(i);
        }
        hs.add(5);
        hs.add(6);
        System.out.println("3 in set?" + hs.contains(3));
        for (int i : hs) {
            System.out.println(i);
        }
    }
}
