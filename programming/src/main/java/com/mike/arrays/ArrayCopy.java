package com.mike.arrays;

import java.util.Arrays;

public class ArrayCopy {

    public static void main(String[] args) {
        int[] arr1 = new int[]{1, 2, 3, 4, 5};
        int[] arr2 = new int[5];
        int[] arr3 = new int[5];

        System.arraycopy(arr1, 0, arr2, 0, arr2.length);
        arr2[0] = 10;
        System.arraycopy(arr1, 1, arr3, 1, 3);

        System.out.println(Arrays.toString(arr1));
        System.out.println(Arrays.toString(arr2));
        System.out.println(Arrays.toString(arr3));

    }
}
