package com.mike.generic;

interface Builder<T> {
  T generate();
}

public class GenBuilder implements Builder<String> {
  @Override
  public String generate() {
    return "String";
  }

  public static void main(String[] args) {
    GenBuilder genBuilder = new GenBuilder();
    for (int i = 0; i < 3; i++) {
      System.out.println(genBuilder.generate());

    }
  }
}
