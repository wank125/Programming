package com.mike.generic;

import java.util.ArrayList;

interface Mapper<E> {
  E map(E vale);
}

public class MappableList<T> extends ArrayList<T> {

  public MappableList<T> map(Mapper<T> mapper) {
    MappableList<T> ts = new MappableList<>();
    for (T e : this) {
      ts.add(mapper.map(e));
    }
    return ts;
  }

}

class MapTest {
  public static void main(String[] args) {
    MappableList<Integer> ml = new MappableList<>();
    MappableList<Integer> res;

    for (int i = 0; i < 5; i++) {
      ml.add(i);
    }

    ml.map(new Mapper<Integer>() {
      @Override
      public Integer map(Integer vale) {
        return vale * 10;
      }
    });

    System.out.println(ml);
  }
}
