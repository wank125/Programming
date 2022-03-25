package com.mike.io;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ObjectReferenceRead {


    public static void main(String[] args) {
        try (
                FileInputStream fis = new FileInputStream("student");
                ObjectInputStream ois = new ObjectInputStream(fis);
        ) {
            Student zhangsan = (Student) ois.readObject();
            Student lisi = (Student) ois.readObject();
            Teacher wangwu = (Teacher) ois.readObject();

            System.out.println(zhangsan);
            System.out.println(lisi);

            System.out.println(zhangsan.getTeacher() == lisi.getTeacher());
            System.out.println(zhangsan.getTeacher() == wangwu);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
