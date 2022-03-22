package com.mike.base;

public abstract class Shape {
    protected abstract void draw();
}


class Point1 extends Shape {
    private int x, y;

    public Point1(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point1() {
        this(0, 0);
    }

    @Override
    public void draw() {

        System.out.println("Point");
    }
}

class Line extends Shape {
    private Point1 start;
    private Point1 end;

    public Line(Point1 start, Point1 end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public void draw() {
        start.draw();
        end.draw();
        System.out.println("Line");

    }
}

class Graph {
    public static void main(String[] args) {
        Point1 point1 = new Point1(1, 1);
        Point1 point11 = new Point1(2, 2);
        Line line = new Line(point1, point11);
        line.draw();
    }
}
