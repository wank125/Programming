package com.mike.netty.protocol;


import lombok.Data;

import java.io.Serializable;
import java.util.Arrays;

@Data
public class Call implements Serializable {
    private String className;
    private String methodName;
    private Class[] paramTypes;

    private Object[] params;

    private Object result;

    public Call() {
    }

    public Call(String className, String methodName, Class[] paramTypes, Object[] params) {
        this.className = className;
        this.methodName = methodName;
        this.paramTypes = paramTypes;
        this.params = params;
    }

    @Override
    public String toString() {
        return "Call{" +
                "className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", paramTypes=" + Arrays.toString(paramTypes) +
                ", params=" + Arrays.toString(params) +
                ", result=" + result +
                '}';
    }
}
