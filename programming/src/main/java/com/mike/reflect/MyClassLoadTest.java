package com.mike.reflect;

import java.lang.reflect.Constructor;

public class MyClassLoadTest {
    public static void main(String[] args) throws Exception {
        MyClassLoader myClassLoader = new MyClassLoader("");
        //把Person.class 改名为Person
        Class<?> clz = myClassLoader.loadClass("com.mike.reflect.Person");
        Constructor<?> constructor = clz.getConstructor();
        Object o = constructor.newInstance();
        System.out.println(clz);
        System.out.println(o);

    }
}
