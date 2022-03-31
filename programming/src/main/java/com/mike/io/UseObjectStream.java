package com.mike.io;

import java.io.*;

public class UseObjectStream {
    public static <ois> void main(String[] args) {

        try (
                FileOutputStream fos = new FileOutputStream("student");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
        ) {
            Student zhangsan = new Student(1, "zhangsan", 98, null);
            Student lisi = new Student(2, "lisi", 75, null);

            oos.writeObject(zhangsan);
            oos.writeObject(lisi);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (
                FileInputStream fis = new FileInputStream("student");
                ObjectInputStream ois = new ObjectInputStream(fis);
        ) {
            Student zhangsan = (Student) ois.readObject();
            Student lisi = (Student) ois.readObject();
            System.out.println(zhangsan);
            System.out.println(lisi);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}
