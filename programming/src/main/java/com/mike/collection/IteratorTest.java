package com.mike.collection;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class IteratorTest {
    public static void printCollection(Collection<?> cl) {
        Iterator<?> iterator = cl.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }


    public static void main(String[] args) {
        ArrayList<Integer> integers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            integers.add(new Integer(i));
        }
        System.out.println("Start");
        printCollection(integers);
    }
}
