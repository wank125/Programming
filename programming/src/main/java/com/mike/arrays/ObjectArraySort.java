package com.mike.arrays;


import java.util.Arrays;
import java.util.Comparator;

class Student implements Comparable {
    private int no;
    private String name;

    public Student(int no, String name) {
        this.no = no;
        this.name = name;
    }


    @Override
    public int compareTo(Object o) {
        Student stu = (Student) o;
        return no > stu.no ? 1 : (no < stu.no ? -1 : 0);
    }

    @Override
    public String toString() {
        return "Student{" +
                "no=" + no +
                ", name='" + name + '\'' +
                '}';
    }

    static class StudentComparator implements Comparator {
        @Override
        public int compare(Object o1, Object o2) {
            Student stu1 = (Student) o1;
            Student stu2 = (Student) o2;
            if (stu1.no > stu2.no) {
                return 1;
            } else if (stu1.no < stu2.no) {
                return -1;
            } else {
                return 0;
            }
        }
    }
}

public class ObjectArraySort {

    public static void main(String[] args) {
        Student[] stus = new Student[3];
        stus[0] = new Student(3, "zhangsan");
        stus[1] = new Student(1, "lisi");
        stus[2] = new Student(2, "wangwu");
        Arrays.sort(stus);
        for (Student s : stus) {
            System.out.println(s);
        }
        stus[2] = new Student(2, "wangwu");
        Arrays.sort(stus, new Student.StudentComparator());
        System.out.println(Arrays.toString(stus));

        int pos = Arrays.binarySearch(stus, new Student(2, "wangwu"), new Student.StudentComparator());
        System.out.println(pos);
    }
}
