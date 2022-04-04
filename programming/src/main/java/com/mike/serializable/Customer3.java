package com.mike.serializable;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Customer3 implements Serializable {
    private static int count;
    private static final int MAX_COUNT = 1000;
    private String name;

    private transient String password;

    static {
        System.out.println("调用Customer3类的静态代码块");
    }

    public Customer3() {
        System.out.println("调用Customer3类的不带参数的构造方法");
    }

    public Customer3(String name, String password) {
        System.out.println("调用Customer3类的带参数的构造方法");
        this.name = name;
        this.password = password;
        count++;
    }

    private byte[] change(byte[] buff) {
        for (int i = 0; i < buff.length; i++) {
            int b = 0;
            for (int j = 0; j < 8; j++) {
                int bit = (buff[i] << j & 1) == 0 ? 1 : 0;
                b += (1 << j) * bit;
            }
            buff[i] = (byte) b;
        }
        return buff;
    }


    private void writeObject(ObjectOutputStream stream) throws Exception {
        stream.defaultWriteObject();
        stream.writeObject(change(password.getBytes()));
        stream.write(count);
    }

    private void readObject(ObjectInputStream stream) throws Exception {
        stream.defaultReadObject();
        byte[] buff = (byte[]) stream.readObject();
        password = new String(change(buff));
        count = stream.readInt();
    }

    public String toString() {
        return "count=" + count + "MAX_COUNT=" + MAX_COUNT
                + "name=" + name
                + "password" + password;
    }

}
