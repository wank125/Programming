package com.mike.base;


import java.util.ArrayList;

abstract class Shap {
  public abstract void draw(Canvas canvas);
}

class Circle extends Shap {

  @Override
  public void draw(Canvas canvas) {
    System.out.println("draw circle");
  }
}

class Rectangle extends Shap {

  @Override
  public void draw(Canvas canvas) {
    System.out.println("draw rectangle");
  }
}


public class Canvas {

  public void drawAll(ArrayList<? extends Shap> shapes) {

    for (Shap shap : shapes) {
      shap.draw(this);
    }

  }

  public static void main(String[] args) {
    Circle circle = new Circle();
    ArrayList<Circle> circles = new ArrayList<>();
    circles.add(circle);

    Rectangle rectangle = new Rectangle();
    ArrayList<Rectangle> rectangles = new ArrayList<>();
    rectangles.add(rectangle);

    Canvas canvas = new Canvas();
    canvas.drawAll(circles);
    canvas.drawAll(rectangles);
  }
}
