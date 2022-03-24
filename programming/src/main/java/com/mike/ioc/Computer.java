package com.mike.ioc;

import com.mike.computer.Mainboard;

public class Computer {
    public static void main(String[] args) {
        //  Mainboard mb = MainboardFactory.getMainboard();
        //  mb.run();
        String path = "/Users/wangkai/sourceFromGit/Programming/programming/src/main/java/com/mike/ioc/";
        BeanFactory beanFactory = new BeanFactory(path + "beans.xml");
        Mainboard mainboard = (Mainboard) beanFactory.getBean("mainboard");
        mainboard.run();

    }
}
