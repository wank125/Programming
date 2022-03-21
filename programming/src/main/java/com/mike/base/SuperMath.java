package com.mike.base;

public class SuperMath {

  public <T extends Comparable<T>> T getMaxMin(T[] num) {
    T max = num[0];
    T min = num[0];

    for (int i = 0; i < num.length; i++) {
      if (max.compareTo(num[i]) < 0) {
        max = num[i];
      }
    }

    for (int i = 0; i < num.length; i++) {
      if (min.compareTo(num[i]) > 0) {
        min = num[i];
      }
    }

    return max;

  }
}
