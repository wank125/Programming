package com.mike.enumes;

enum Week {
  Sunday(70), Monday(10), Tuesday(20), Wednesday(30), Thursday(40), Friday(50), Saturday(60);

  private final int number;

  Week(int num) {
    number = num;
  }

  public int serialNumber() {
    return number;
  }
}
