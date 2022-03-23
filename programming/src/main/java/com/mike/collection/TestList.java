package com.mike.collection;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

interface Test {
    void test();
}

class Tester {
    public static long runTest(Test test) {
        long start = System.nanoTime();
        test.test();
        long end = System.nanoTime();
        return end - start;
    }
}

public class TestList {

    public static <E> void testSuite(List<Integer> list) {
        long addTime, getTime, setTime, removeTime;
        System.out.println("count\taddTime\tgetTime\tsetTime\tremoveTime");
        addTime = Tester.runTest(new Test() {
            @Override
            public void test() {
                for (int i = 0; i < 1000; i++) {
                    list.add(0, i);
                }
            }
        });

        getTime = Tester.runTest(new Test() {
            @Override
            public void test() {
                for (int i = 0; i < 1000; i++) {
                    list.get(i);
                }
            }
        });
        setTime = Tester.runTest(new Test() {
            @Override
            public void test() {
                for (int i = 0; i < 1000; i++) {
                    list.set(i, 8888);
                }
            }
        });

        removeTime = Tester.runTest(new Test() {
            @Override
            public void test() {
                for (int i = 0; i < 1000; i++) {
                    list.remove(0);
                }
            }
        });

        System.out.printf("%d\t%d\t%d\t%d\t%d", 1000, addTime, getTime, setTime, removeTime);
    }

    public static void main(String[] args) {
        ArrayList<Integer> al = new ArrayList<>();
        LinkedList<Integer> ll = new LinkedList<>();
        System.out.println("ArrayList");
        testSuite(al);
        System.out.println("LinkedList");
        testSuite(ll);
    }
}
