package stack;

public final class Test {
  public static int top;
  public final int bt;
  public int c;

  public static int getTop() {
    return top;
  }

  public static void setTop(int top) {
    Test.top = top;
  }

  public int getBt() {
    return bt;
  }

  public int getC() {
    return c;
  }

  public void setC(int c) {
    this.c = c;
  }

  public Test(int a) {
    this.bt = a;
  }

  public static void main(String[] args) {
    Test test = new Test(10);
    System.out.println(test.getC());


//    System.out.println();
//
//    Test.getTop();
//    System.out.println(Test.getTop());
//    Test.setTop(10);
//    System.out.println(Test.getTop());
//    Test.setTop(20);
//    System.out.println(Test.getTop());

  }
}
