package com.mike.base;

public class Person {
    private final String name;
    private final int age;
    private final boolean gender;
    private final Person partner;

    public Person(String name, int age, boolean gender, Person partner) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.partner = partner;
    }

    public boolean marry(Person p) {

        if (this.age < 22 || p.age < 22) {
            return false;
        }

        if (p.isGender() == this.isGender()) {
            return false;
        } else if (p.getPartner() != null) {
            return false;
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public boolean isGender() {
        return gender;
    }

    public Person getPartner() {
        return partner;
    }
}

class PersonTest {
    public static void main(String[] args) {
        Person a = new Person("a", 17, true, null);
        Person b = new Person("b", 22, true, null);
        System.out.println(a.marry(b));
    }
}
