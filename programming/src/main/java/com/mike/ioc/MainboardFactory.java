package com.mike.ioc;

import com.mike.computer.CPU;
import com.mike.computer.GraphicsCard;
import com.mike.computer.Mainboard;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public class MainboardFactory {

    public static Mainboard getMainboard() {
        Mainboard mb = new Mainboard();
        FileInputStream in = null;
        try {
            String path = "/Users/wangkai/sourceFromGit/Programming/programming/src/main/java/com/mike/ioc/";
            in = new FileInputStream(path + "computer.properties");
            Properties pps = new Properties();
            pps.load(in);
            String cpuClass = pps.getProperty("cpu");
            String gCardClass = pps.getProperty("graphicsCard");

            Class<?> cpuClz = Class.forName(cpuClass);
            Class<?> gCardClz = Class.forName(gCardClass);

            if (CPU.class.isAssignableFrom(cpuClz)) {
                CPU cpu = (CPU) cpuClz.getConstructor().newInstance();
                mb.setCpu(cpu);
            }

            if (GraphicsCard.class.isAssignableFrom(gCardClz)) {
                GraphicsCard gpu = (GraphicsCard) gCardClz.getConstructor().newInstance();
                mb.setGraphicsCard(gpu);
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return mb;
    }


}
