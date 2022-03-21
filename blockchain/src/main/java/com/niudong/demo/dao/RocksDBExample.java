package com.niudong.demo.dao;

import com.google.common.base.Charsets;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;

import java.nio.charset.Charset;

public class RocksDBExample {

  private static final String dbPath = ".//";
  private static final String cfdbPath = "";

  static {
    RocksDB.loadLibrary();
  }

  public void testDefaultCF() {
    System.out.println("begin");
    try (final Options options = new Options().setCreateIfMissing(true)) {
      RocksDB rocksDB = RocksDB.open(options, dbPath);
      byte[] key = "hello".getBytes(Charsets.UTF_8);
      rocksDB.put(key, "world".getBytes(Charsets.UTF_8));
      System.out.println(new String(rocksDB.get(key)));

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    RocksDBExample rocksDBExample = new RocksDBExample();
    rocksDBExample.testDefaultCF();
  }
}
