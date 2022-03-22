package com.mike.generic;

public class StackObject {
  private class Node {
    private Object value;
    private Node next;

    public Node() {
      this(null, null);
    }

    public Node(Object value, Node next) {
      this.value = value;
      this.next = next;
    }

    public boolean end() {
      return next == null;
    }
  }

  private Node root = new Node();

  public void push(Object value) {
    this.root = new Node(value, this.root);
  }

  public Object pop() {
    Node val = this.root;
    if (!val.end()) {
      this.root = val.next;
      return val.value;
    }
    return null;
  }

  public boolean empty() {
    return root.end();
  }
}

class StackTest {
  public static void main(String[] args) {
    StackObject stack = new StackObject();
    stack.push("one");
    stack.push("two");
    stack.push("three");

    System.out.println(stack.pop());
    System.out.println(stack.pop());
    System.out.println(stack.pop());

    stack.push("one");
    stack.push(11);
    System.out.println(stack.pop());
    System.out.println(stack.pop());

  }

}
