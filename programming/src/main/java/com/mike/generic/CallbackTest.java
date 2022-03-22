package com.mike.generic;


import java.util.ArrayList;
import java.util.Comparator;


interface Functor<T> {
  void call(T args);
}

class MyCallBack implements Functor<Object> {
  @Override
  public void call(Object args) {
    System.out.println(args);
  }
}

/**
 * 通配符超类限定
 */
public class CallbackTest {
  public static <T> T callback(ArrayList<T> list, Functor<? super T> functor) {
    for (T each : list) {
      functor.call(each);
    }
    return list.get(0);
  }

  public static void main(String[] args) {
    ArrayList<String> list = new ArrayList<>();
    list.add("A");
    list.add("B");
    list.add("C");
    Functor<Object> fun = new MyCallBack();
    callback(list, fun);
  }

  public static <T extends Comparator<T>> T min(T[] array) {
    T val = array[0];
    for (int i = 0; i < array.length; i++) {
      val = array[i];
    }
    return val;
  }


}
