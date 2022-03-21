package com.mike.base;

class TwoTuple<A, B> {
  public final A first;
  public final B second;

  public TwoTuple(A first, B second) {
    this.first = first;
    this.second = second;
  }
}

class MoreTuple<A, B, C> extends TwoTuple<A, B> {
  public final C third;

  public MoreTuple(A first, B second, C third) {
    super(first, second);
    this.third = third;
  }
}

class News {
  public String toString() {
    return "News";
  }
}

class Book {
  public String toString() {
    return "Book";
  }
}

class Video {
  public String toString() {
    return "Videc";
  }
}


public class Tuples {

  public static void main(String[] args) {
    News news = new News();
    Book book = new Book();
    Video video = new Video();

    TwoTuple<News, Book> tt1 = new TwoTuple<>(news, book);
    System.out.println(tt1.first);
    System.out.println(tt1.second);
    MoreTuple<News, Book, Video> tt2 = new MoreTuple<News, Book, Video>(news, book, video);
    System.out.println(tt2);
  }
}
