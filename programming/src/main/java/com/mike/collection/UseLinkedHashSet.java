package com.mike.collection;

import java.util.HashSet;
import java.util.LinkedHashSet;

public class UseLinkedHashSet {
    public static void main(String[] args) {
        HashSet<String> hs = new HashSet<>();
        hs.add("one");
        hs.add("two");
        hs.add("tree");
        hs.add("four");

        for (String str : hs) {
            System.out.println(str);
        }

        LinkedHashSet<String> lhs = new LinkedHashSet<>();
        lhs.add("one");
        lhs.add("two");
        lhs.add("tree");
        lhs.add("four");
        for (String str : lhs) {
            System.out.println(str);
        }
    }
}
