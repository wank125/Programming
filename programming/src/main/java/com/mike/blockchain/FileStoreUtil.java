package com.mike.blockchain;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class FileStoreUtil {

  private static final int FILE_SIZE = 1024;


  public static void writeIntoTargeFile(String targerFile, String content) {

    File newFile = new File(targerFile);
    try {
      Files.write(content.getBytes(), newFile);
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public static void appendToTargetFile(String targerFile, String content) {
    try {
      FileWriter writer = new FileWriter(targerFile, true);
      writer.write(content);
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  public static void appendToTargetFileByGuava(String targerFile, String content) {
    try {
      File file = new File(targerFile);
      Files.append(content, file, Charsets.UTF_8);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void writeIntoFIle(String content) {
    File root = new File(".//");
    File[] files = root.listFiles();
    if (files == null) {
      String targerFileName = ".//blockchain-" + System.currentTimeMillis() + ".loging";
      appendToTargetFileByGuava(targerFileName, content);
      return;
    }

    boolean has = false;

    for (File file : files) {
      String name = file.getName();
      if ((name.endsWith(".loging")) && name.startsWith("blockchain-")) {

        System.out.println(file.getPath());
        appendToTargetFileByGuava(file.getPath(), content);
        has = true;
      }

      if (file.length() > FILE_SIZE) {
        String logPath = file.getPath().replace("loging", "log");
        File log = new File(logPath);
        try {
          Files.copy(file, log);
          file.delete();
        } catch (IOException e) {
          e.printStackTrace();
        }

      }
    }

    if (!has) {
      String targerFileName = ".//blockchain-" + System.currentTimeMillis() + ".loging";
      appendToTargetFileByGuava(targerFileName, content);
      return;
    }


  }

  public static void writeIntoBlockFile() {
    ArrayList<String> lists = new ArrayList<>();
    lists.add("AI");
    lists.add("BlockChain");
    lists.add("BrainScience");
    for (int i = 0; i < 20; i++) {
      //lists.add(generateVcode(6));
      //writeIntoFIle();
      //todo
    }
  }

}
