package com.mike.io;

import java.io.Serializable;

public class Student implements Serializable {

    private int no;
    private String name;
    private float score;
    private Teacher teacher;

    public Student(int no, String name, float score, Teacher teacher) {
        this.no = no;
        this.name = name;
        this.score = score;
        this.teacher = teacher;
    }

    public int getNo() {
        return no;
    }

    public String getName() {
        return name;
    }

    public float getScore() {
        return score;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    @Override
    public String toString() {
        return "Student{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", score=" + score +
                ", teacher=" + teacher +
                '}';
    }


}

class Teacher implements Serializable {
    private String name;

    public Teacher(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
