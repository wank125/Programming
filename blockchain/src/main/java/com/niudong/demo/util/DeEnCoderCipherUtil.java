package com.niudong.demo.util;

import com.google.common.base.Strings;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;

public class DeEnCoderCipherUtil {

  private final static String CIPHER_MODE = "DES";
  public static String DEFAULT_DES_KEY = "区块链是分布式数据存储";

  public static String encrypt(String originalContent, String key) throws Exception {

    if (Strings.isNullOrEmpty(originalContent) || Strings.isNullOrEmpty(key)) {
      return null;
    }
    byte[] byteContent = encrypt(originalContent.getBytes(), key.getBytes());
    return new BASE64Encoder().encode(byteContent);
  }

  public static String decrypt(String ciphertext, String key) throws Exception {
    if (Strings.isNullOrEmpty(ciphertext) || Strings.isNullOrEmpty(key)) {
      return null;
    }

    BASE64Decoder decoder = new BASE64Decoder();
    byte[] bytes = decoder.decodeBuffer(ciphertext);
    byte[] contenByte = decrypt(bytes, key.getBytes());
    return new String(contenByte);
  }


  private static byte[] encrypt(byte[] originalContent, byte[] key) throws Exception {
    SecureRandom secureRandom = new SecureRandom();
    DESKeySpec desKeySpec = new DESKeySpec(key);

    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(CIPHER_MODE);
    SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

    Cipher cipher = Cipher.getInstance(CIPHER_MODE);
    cipher.init(Cipher.ENCRYPT_MODE, secretKey, secureRandom);
    return cipher.doFinal(originalContent);
  }

  private static byte[] decrypt(byte[] chiphertextByte, byte[] key) throws Exception {
    SecureRandom secureRandom = new SecureRandom();
    DESKeySpec desKeySpec = new DESKeySpec(key);
    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(CIPHER_MODE);
    SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

    Cipher chiper = Cipher.getInstance(CIPHER_MODE);

    chiper.init(Cipher.DECRYPT_MODE, secretKey, secureRandom);
    return chiper.doFinal(chiphertextByte);
  }

}
