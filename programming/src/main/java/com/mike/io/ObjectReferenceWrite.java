package com.mike.io;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ObjectReferenceWrite {
    public static void main(String[] args) {
        Teacher wangwu = new Teacher("wangwu");
        Student zhangsan = new Student(1, "zhangsan", 98, wangwu);
        Student lisi = new Student(2, "lisi", 75, wangwu);

        try (
                FileOutputStream fos = new FileOutputStream("student");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
        ) {
            oos.writeObject(zhangsan);
            oos.writeObject(lisi);
            oos.writeObject(wangwu);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
