package com.mike.annotation;

class A {
  @Deprecated
  public void fn() {
  }
}

@Deprecated
interface B {
}

@SuppressWarnings("deprecation")
class C implements B {

}

public class DeprecatedAnnotation {

  @SuppressWarnings("deprecation")
  public static void main(String[] args) {
    A a = new A();
    a.fn();
  }
}
