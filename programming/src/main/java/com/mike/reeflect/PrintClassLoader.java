package com.mike.reeflect;

public class PrintClassLoader {

    public static void main(String[] args) throws ClassNotFoundException {

        //获取系统类加载器
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        System.out.println("系统类加载器：" + systemClassLoader);
        System.out.println("系统类加载器的父级: " + systemClassLoader.getParent());

        //获取String类的类加载器

        ClassLoader classLoader = String.class.getClassLoader();
        System.out.println("String类的类加载器" + classLoader);

        //获取当前类的类加载器
        Class<?> clz = Class.forName("com.mike.reeflect.PrintClassLoader");
        ClassLoader classLoader1 = clz.getClassLoader();
        System.out.println("当前类的类加载器：" + classLoader);

    }
}
