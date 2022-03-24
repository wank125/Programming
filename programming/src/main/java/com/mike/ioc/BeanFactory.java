package com.mike.ioc;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * beans 注入
 */
public class BeanFactory {

    private Map<String, Object> beans = new HashMap<String, Object>();

    public BeanFactory(String fileName) {
        SAXReader saxReader = new SAXReader();
        ClassLoader classLoader = this.getClass().getClassLoader();
        InputStream is = classLoader.getResourceAsStream(fileName);
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Document doc = saxReader.read(inputStream);
            Element root = doc.getRootElement();
            List<Element> beanList = root.elements("bean");
            for (Element beanELt : beanList) {
                String id = beanELt.attributeValue("id");
                String className = beanELt.attributeValue("class");
                Class<?> cls = null;
                try {
                    cls = Class.forName(className);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                List<Element> conList = beanELt.elements("constructor");
                if (conList.isEmpty()) {
                    Object obj = null;
                    obj = cls.getConstructor().newInstance();
                    beans.put(id, obj);
                } else {
                    int i = 0;
                    Class[] argsCls = new Class[conList.size()];
                    Object[] args = new Object[conList.size()];
                    for (Iterator it = conList.iterator(); it.hasNext(); i++) {
                        Element consElt = (Element) it.next();
                        argsCls[i] = String.class;
                        args[i] = consElt.element("value").getText();
                    }
                    Constructor<?> cons = cls.getConstructor(argsCls);
                    Object obj = cons.newInstance(args);
                    beans.put(id, obj);
                }
                List<Element> propList = beanELt.elements("property");
                for (Element proElt : propList) {
                    String name = proElt.attributeValue("name");
                    StringBuffer sb = new StringBuffer();
                    sb.append("set")
                            .append(name.substring(0, 1).toUpperCase())
                            .append(name.substring(1));
                    String objName = proElt.element("ref").attributeValue("bean");
                    Object obj2 = beans.get(objName);
                    Method mth = cls.getMethod(sb.toString(), obj2.getClass().getInterfaces()[0]);
                    mth.invoke(beans.get(id), obj2);
                }
            }

        } catch (DocumentException e) {
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

    }

    public Object getBean(String name) {
        return beans.get(name);
    }

}
