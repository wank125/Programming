package com.mike.generic;

public class SuperMath {

  public <T extends Comparable<T>> Num<T> getMaxMin(T[] num) {
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

    return new Num<T>(min, max);

  }

  public static void main(String[] args) {
    SuperMath superMath = new SuperMath();
    Integer[] num = new Integer[]{1, 2, 3, 4, 5};
    Num<Integer> maxMin = superMath.getMaxMin(num);
    System.out.println(maxMin);
  }
}

class Num<T> {

  private T min;
  private T max;

  public Num() {
  }

  public Num(T min, T max) {
    this.min = min;
    this.max = max;
  }

  public T getMax() {
    return max;
  }

  public T getMin() {
    return min;
  }

  public void setMin(T min) {
    this.min = min;
  }

  public void setMax(T max) {
    this.max = max;
  }

  @Override
  public String toString() {

    return "Num [min=" + min + ",max=" + max + "]";
  }
}