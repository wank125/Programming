package com.mike.exception;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class PreviousExcepHandling {


    public static void main(String[] args) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("1.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
