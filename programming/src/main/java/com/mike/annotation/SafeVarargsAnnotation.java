package com.mike.annotation;

public class SafeVarargsAnnotation<T> {
  private T[] arrary;

  public SafeVarargsAnnotation(T... arrary) {
    this.arrary = arrary;
  }

  public void forEach(T... varAgrs) {
    for (T arg : varAgrs) {
      System.out.println(arg);
    }
  }
}

