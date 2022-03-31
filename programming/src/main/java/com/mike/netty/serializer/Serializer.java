package com.mike.netty.serializer;

public interface Serializer {

    byte JSON_SERIALIZER = 1;
    Serializer DEFAULT = new JSONSerializer();

    /**
     * 序列化算法
     */
    byte getSerializerAlgorithm();

    /**
     * 对象转换成二进制
     */
    byte[] serialize(Object object);

    /**
     * 二进制转换成Java
     */
    <T> T deSerializer(Class<T> clazz, byte[] bytes);


}
