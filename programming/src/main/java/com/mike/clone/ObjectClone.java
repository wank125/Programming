package com.mike.clone;


import java.util.Arrays;

class Person implements Cloneable {
    private String name;
    private int age;

    public Person getPartner() {
        return partner;
    }

    public void setPartner(Person partner) {
        this.partner = partner;
    }

    private Person partner;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", partner=" + partner +
                '}';
    }

    public Person copy() {
        return new Person(this.name, this.age);
    }

    @Override
    protected Person clone() {
        Person p = null;
        try {
            p = (Person) super.clone();
            if (partner != null) {
                p.partner = partner.clone();
            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return p;
    }
}

public class ObjectClone {

    public static void main(String[] args) {
        Person p1 = new Person("Jhon", 18);
        p1.setPartner(new Person("Mary", 18));
        // Person p2 = p1;
        // Person p2 = p1.copy();
        Person p2 = p1.clone();

        p2.setAge(22);
        System.out.println(p1);
        System.out.println(p2);

        p2.getPartner().setName("Julie");
        System.out.println(p1);
        System.out.println(p2);


//        char[] strs = "aaahhifaaaajf".toCharArray();
//        Arrays.sort(strs);
//        System.out.println(Arrays.toString(strs));
    }
}
