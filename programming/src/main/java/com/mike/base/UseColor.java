package com.mike.base;

public class UseColor {
    public static void main(String[] args) {
        Color color;
        color = Color.GREEN;
        switch (color) {
            case RED:
                System.out.println("RED");
                break;
            case BLUE:
                System.out.println("BLUE");
                break;
            case GREEN:
                System.out.println("GREEN");
                break;
        }
    }
}
