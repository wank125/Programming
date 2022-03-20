package stack;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


class StackByArry {

  private int[] stack;
  private int top;

  public StackByArry(int stack_size) {
    stack = new int[stack_size];
    top = -1;
  }


  public boolean push(int data) {
    if (top >= stack.length) {
      System.out.println("堆栈已满");
      return false;
    } else {
      stack[++top] = data;
      return true;
    }
  }

  public boolean empty() {
    if (top == -1) {
      return true;
    } else {
      return false;
    }
  }

  public int pop() {
    if (empty()) {
      return -1;
    } else {
      return stack[top--];
    }
  }
}

public class Stack01 {

  public static void main(String[] args) throws IOException {
    BufferedReader buf;
    int value;
    StackByArry stack = new StackByArry(10);
    buf = new BufferedReader(new InputStreamReader(System.in));
    System.out.println("请按序输入10个数据");
    for (int i = 0; i < 10; i++) {
      value = Integer.valueOf(buf.readLine());
      stack.push(value);
    }
    System.out.println("==================================");
    while (!stack.empty())
      System.out.println("堆栈的弹出顺序是:" + stack.pop());

  }
}
