package com.mike.collection;


import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeSet;

class Student implements Comparable<Student> {
    private int no;
    private String name;

    public Student(int no, String name) {
        this.no = no;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "no=" + no +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int compareTo(Student o) {
        return no > o.no ? 1 : (no < o.no ? -1 : 0);
    }

    public static class StudentComparator implements Comparator<Student> {

        @Override
        public int compare(Student o1, Student o2) {
            return o1.no > o2.no ? -1 : (o1.no < o2.no ? 1 : 0);
        }
    }
}

public class UseTreeSet {

    public static void main(String[] args) {
        TreeSet<Integer> ts = new TreeSet<>(Arrays.asList(5, 3, 1, 8, 9, 6, 2, 7));
        System.out.println("所有的元素" + ts);
        System.out.println("TreeSet中的第一个元素:" + ts.first());
        System.out.println("TreeSet中的最后一个元素" + ts.last());


        TreeSet<Student> students = new TreeSet<>();
        Student zhangsan = new Student(2, "zhangsan");
        Student lisi = new Student(1, "lisi");
        Student wagnwu = new Student(3, "wagnwu");
        students.add(zhangsan);
        students.add(lisi);
        students.add(wagnwu);


        System.out.println(students);

    }


}
