package com.mike.clone;

import java.util.Arrays;

public class LeetCode {
    public static void main(String[] args) {
        User[] users = new User[]{new User(1, "1", new Person1("p1")), new User(2, "2", new Person1("p2"))};
        //User[] user2 = new User[2];
        //System.arraycopy(users, 0, user2, 0, users.length);

        User[] user1 = users.clone();
        users[0].setAge(33);
        users[0].setId("33");
        users[0].getPerson().setName("p33");

        System.out.println(Arrays.toString(users));

        System.out.println(Arrays.toString(user1));
        // System.out.println(Arrays.toString(user2));
    }


}

class User implements Cloneable {
    public User(int age, String id, Person1 person) {
        this.age = age;
        this.id = id;
        this.person = person;
    }

    private int age;
    private String id;
    private Person1 person;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Person1 getPerson() {
        return person;
    }

    public void setPerson(Person1 person) {
        this.person = person;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        User clone = (User) super.clone();
        if (person != null) {
            clone.setPerson(person);
        }
        return clone;
    }

    @Override
    public String toString() {
        return "User{" +
                "age=" + age +
                ", id='" + id + '\'' +
                ", person=" + person +
                '}';
    }
}

class Person1 {
    @Override
    public String toString() {
        return "Person1{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Person1(String name) {
        this.name = name;
    }

    private String name;
}