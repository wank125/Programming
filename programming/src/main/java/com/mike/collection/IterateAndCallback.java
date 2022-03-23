package com.mike.collection;

import java.util.ArrayList;
import java.util.Arrays;

interface Functor<E> {
    void hadle(E e);
}


class MyList<E> extends ArrayList<E> {
    void each(Functor<E> functor) {
        for (E e : this) {
            functor.hadle(e);
        }
    }
}

public class IterateAndCallback {

    public static void main(String[] args) {
        MyList<Integer> list = new MyList<>();
        list.addAll(Arrays.asList(1, 2, 3, 4, 7));
        list.each(new Functor<Integer>() {
            @Override
            public void hadle(Integer integer) {
                System.out.println(integer);
            }
        });
    }

}
