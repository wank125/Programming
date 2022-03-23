package com.mike.exception;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Java7ExcepHandling {
    public static void main(String[] args) {
        try (FileInputStream fis = new FileInputStream("1.txt")) {
            //
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
