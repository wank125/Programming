package com.niudong.demo.dao;

import com.google.common.base.Strings;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

@Component
public class RocksdbDAO {

  static {
    RocksDB.loadLibrary();
  }

  private Logger logger = LoggerFactory.getLogger(RocksdbDAO.class);

  @Resource
  private RocksDB rocksDB;

  private static final String CHARSSET = "utf-8";

  public void put(String key, String value) {
    if (Strings.isNullOrEmpty(key) || Strings.isNullOrEmpty(value)) {
      return;
    }
    try {
      rocksDB.put(key.getBytes(CHARSSET), value.getBytes(CHARSSET));
    } catch (RocksDBException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
  }

  public String get(String key) {
    if (Strings.isNullOrEmpty(key)) {
      return null;
    }

    byte[] bytes = new byte[0];
    try {
      bytes = rocksDB.get(key.getBytes(CHARSSET));
      if (bytes != null) {
        return new String(bytes);
      }
    } catch (RocksDBException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return null;
  }

  public void delete(String key) {
    if (Strings.isNullOrEmpty(key)) {
      return;
    }
    try {
      rocksDB.delete(rocksDB.get(key.getBytes(CHARSSET)));
    } catch (RocksDBException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
  }

}
