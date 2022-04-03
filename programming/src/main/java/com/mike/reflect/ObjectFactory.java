package com.mike.reflect;


import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

class Person {
    private String name;

    public Person(String name) {
        this.name = name;
    }

    public Person() {
        this("匿名");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void run(int meters) {
        System.out.printf("%s跑了%d米%n", name, meters);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                '}';
    }

    private void helper() {
        System.out.println("私有的辅助方法");
    }
}

public class ObjectFactory {
    public static void main(String[] args) {
//        if (args.length < 1) {
//            System.exit(1);
//        }
        try {
//            Class<?> clz = Class.forName("com.mike.reeflect.ObjectFactory");
//            Constructor noArgCons = clz.getConstructor();
//            Object obj = noArgCons.newInstance();
//            System.out.println(clz);
//            System.out.println(obj);

            //Class<Person> aClass = Person.class;

            //访问方法
//            Class<?> clz = Class.forName("com.mike.reeflect.Person");
//            Constructor<?> constructor = clz.getConstructor(String.class);
//            Object obj = constructor.newInstance("zhangsan");
//            Method mth = clz.getMethod("run", int.class);
//            mth.invoke(obj, 800);
//            mth = clz.getDeclaredMethod("helper");
//            mth.setAccessible(true);
//            mth.invoke(obj);

            //修改字段值

            Class<?> clz = Class.forName("com.mike.reflect.Person");
            Constructor<?> constructor = clz.getConstructor(String.class);
            Object obj = constructor.newInstance("zhangsan");
            System.out.println(obj);
            Field f = clz.getDeclaredField("name");
            f.setAccessible(true);
            f.set(obj, "lisi");
            System.out.println(obj);
            f.setAccessible(false);


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

    }
}
