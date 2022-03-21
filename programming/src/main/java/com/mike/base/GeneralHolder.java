package com.mike.base;

interface Iterator<T> {

  boolean hasNext();

  T next();
}

public class GeneralHolder<T> {

  private Object[] valuses;
  private int pos;


  public GeneralHolder(int length) {
    valuses = new Object[length];
    pos = 0;
  }

  public void put(T value) {
    valuses[pos++] = value;
  }

  public T get(int index) {
    return (T) this.valuses[index];
  }

  public Iterator<T> iterator() {
    return new Iterator<T>() {
      private int ipos = 0;

      @Override
      public boolean hasNext() {
        return ipos < valuses.length;
      }

      @Override
      public T next() {
        return (T) valuses[ipos++];
      }
    };
  }

  public static void main(String[] args) {
    GeneralHolder<Integer> gh = new GeneralHolder<>(10);
    for (int i = 0; i < 10; i++) {
      gh.put(i);
    }
    Iterator<Integer> iterator = gh.iterator();
    while (iterator.hasNext()) {
      System.out.println(iterator.next());
    }
  }
}
