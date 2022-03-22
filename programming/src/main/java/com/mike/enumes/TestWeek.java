package com.mike.enumes;

public class TestWeek {
  static void UseWeek(Week day) {
    switch (day) {
      case Sunday:
        System.out.println(7);
        break;
      case Monday:
        System.out.println(1);
        break;
      case Tuesday:
        System.out.println(2);
        break;
      case Friday:
        System.out.println(5);
        break;
    }
  }

  public static void main(String[] args) {
    TestWeek.UseWeek(Week.Friday);
    Week[] values = Week.values();

    for (Week value : values) {
      System.out.print(value + "");
    }

    System.out.println();
    System.out.println(Week.valueOf("Monday"));


    System.out.println(Week.Saturday.serialNumber());
  }
}
