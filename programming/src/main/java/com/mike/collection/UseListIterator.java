package com.mike.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ListIterator;

public class UseListIterator {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        list.addAll(Arrays.asList(11, 22, 33, 44, 55));
        ListIterator<Integer> listIterator = list.listIterator();

        System.out.println("Use next method: ");
        while (listIterator.hasNext()) {
            System.out.println(listIterator.next());
        }


        System.out.println("Use previous method: ");
        while (listIterator.hasPrevious()){
            System.out.println(listIterator.previous());
        }
    }
}
