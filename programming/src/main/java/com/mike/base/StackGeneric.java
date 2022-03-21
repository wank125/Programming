package com.mike.base;

public class StackGeneric<T> {
  /**
   * 范型
   */

  private class Node {
    private T value;
    private Node next;
    //private HashMap<K, V> fist;

    public Node(T value, Node next) {
      this.value = value;
      this.next = next;
      // this.fist = new HashMap<K, V>();
    }

    public Node() {
      this(null, null);
    }

    public boolean end() {
      return next == null;
    }
//
//    public void setFirst(K key, V value) {
//      this.fist.put(key, value);
//    }
  }

  private Node root = new Node();


  public void push(T value) {
    this.root = new Node(value, this.root);
  }

  public T pop() {
    Node val = this.root;
    if (!val.end()) {
      this.root = val.next;
      return val.value;
    }
    return null;
  }


}

class StackTest1 {
  public static void main(String[] args) {
    StackGeneric<String> stack = new StackGeneric<>();
    stack.push("one");
    stack.push("two");

    System.out.println(stack.pop());
    System.out.println(stack.pop());

  }

}