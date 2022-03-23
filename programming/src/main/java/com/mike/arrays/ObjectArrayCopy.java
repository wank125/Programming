package com.mike.arrays;

class Foo {
    public int value;

    public Foo(int value) {
        this.value = value;
    }
}

public class ObjectArrayCopy {

    public static void main(String[] args) {
        Foo[] arr1 = new Foo[5];
        Foo[] arr2 = new Foo[5];

        for (int i = 0; i < 5; i++) {
            arr1[i] = new Foo(i);
        }

        System.arraycopy(arr1, 0, arr2, 0, arr2.length);
        arr2[0].value = 10;
        System.out.println(arr1[0].value);
    }
}
