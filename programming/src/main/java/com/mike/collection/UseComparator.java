package com.mike.collection;

import java.util.ArrayList;
import java.util.Comparator;

class Person {
    private String name;
    private int age;
    private int salary;

    public Person(String name, int age, int salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                '}';
    }
}

public class UseComparator {
    public static void main(String[] args) {
        ArrayList<Person> list = new ArrayList<>();
        list.add(new Person("zhangsan", 25, 6000));
        list.add(new Person("lisi", 30, 5000));
        list.add(new Person("wangwu", 22, 3000));
        list.add(new Person("zhaoliu", 26, 4000));

        list.sort(Comparator.comparingInt(Person::getAge).thenComparingInt(Person::getSalary));
        System.out.println(list);
    }

}
