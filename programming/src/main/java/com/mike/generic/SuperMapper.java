package com.mike.generic;

import java.util.ArrayList;

/**
 * 范型接口
 */

public interface SuperMapper<T, U> {
  U map(T value);
}

class SuperMapperList<T> extends ArrayList<T> {
  public <U> SuperMapperList<U> map(SuperMapper<T, U> mapper) {
    SuperMapperList<U> res = new SuperMapperList<>();
    for (T val : this) {
      res.add(mapper.map(val));
    }
    return res;
  }
}

class SuperMapperTest {
  public static void main(String[] args) {
    SuperMapperList<Integer> sml = new SuperMapperList<>();
    SuperMapperList<String> res;
    for (int i = 0; i < 5; i++) {
      sml.add(i);
    }
    res = sml.map(new SuperMapper<Integer, String>() {
      @Override
      public String map(Integer value) {
        return "String" + value;
      }
    });
    System.out.println(res);
  }

}
