package com.mike.socket.ch13;

import java.io.Serializable;

public class Customer implements Serializable {

    private long id;
    private String name = "";
    private String addr = "";

    private int age;

    public Customer(long id, String name, String addr, int age) {

        this.id = id;
        this.name = name;
        this.addr = addr;
        this.age = age;
    }

    public Customer(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddr() {
        return addr;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", addr='" + addr + '\'' +
                ", age=" + age +
                '}';
    }
}

