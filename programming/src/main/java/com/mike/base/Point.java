package com.mike.base;

import java.util.HashMap;

public class Point {
  int x;
  int y;
  static int count;
  final int sum;

  public Point(int sum) {
    this.sum = sum;
    //this(2, 2);
  }

//  Point(int x, int y) {
//    this.init(x, y);
//    count++;
//  }

  void init(int a, int b) {
    this.x = a;
    this.y = b;
  }

  void show() {
    System.out.println(x);
    System.out.println(y);
  }

  public int getSum() {
    return sum;
  }

  public static int getCount() {
    return count;
  }

  public static void setCount(int count) {
    Point.count = count;
  }

  public static void main(String[] args) {
//    Point point = new Point(5, 5);
//    System.out.println(point.count);
//
//    Point point1 = new Point();
//    System.out.println(point1.count);


    Point point = new Point(3);
    System.out.println(point.getSum());
    Point point1 = new Point(4);
    System.out.println(point1.getSum());

    HashMap<String, String> map = new HashMap<>();
  }

}


