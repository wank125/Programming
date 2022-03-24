package com.mike.reeflect;

import java.io.*;

public class MyClassLoader extends ClassLoader {

    private String filePath;

    public MyClassLoader(String filePath) {
        this.filePath = filePath;
    }


    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Class clz = null;
        byte[] data = loadData();
        if (data != null) {
            clz = defineClass(name, data, 0, data.length);
        }
        return super.findClass(name);
    }

    private byte[] loadData() {
        File file = new File(filePath);
        if (file == null) {
            return null;
        }
        byte[] data = null;
        try (FileInputStream in = new FileInputStream(file);
             ByteArrayOutputStream out = new ByteArrayOutputStream();
        ) {
            byte[] buf = new byte[1024];
            int size = 0;
            while ((size = in.read(buf)) != -1) {
                out.write(buf, 0, size);
            }
            data = out.toByteArray();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
