package com.mike.outer;

/**
 * 回调
 */
interface ClickHandler {
    void onClick();
}

class Button {
    private String name;
    private ClickHandler clickHandler = null;

    public Button() {
        this.name = "NoName";
    }

    public Button(String name) {
        this.name = name;
    }

    public void click() {
        if (clickHandler != null) {
            clickHandler.onClick();
        }
    }

    public void registeHandler(ClickHandler handler) {
        clickHandler = handler;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

public class MyWindow {
    public static String arg = "This is my window";
    private Button bt1 = new Button("Button 1");
    private Button bt2 = new Button("Button 2");

    private class Btn1ClickHandler implements ClickHandler {
        @Override
        public void onClick() {
            System.out.println(bt1.getName());
        }
    }

    private class Btn2ClickHandler implements ClickHandler {
        @Override
        public void onClick() {
            System.out.println(bt2.getName());
        }
    }

    public MyWindow() {
        bt1.registeHandler(new Btn1ClickHandler());
        bt2.registeHandler(new Btn2ClickHandler());
    }

    public void run() {
        bt1.click();
        bt2.click();
        System.out.println(bt1.getName());
        System.out.println(bt2.getName());
    }

    public static void main(String[] args) {
        MyWindow myWindow = new MyWindow();
        myWindow.run();
    }
}
