package com.mike.socket.ch11;

import javax.naming.InitialContext;


/**
 * 并发访问
 */
public class SimpleServer3 {

    //rmiregistry 命令需要在target/classes目录下运行,要不然找不到注册类

    public static void main(String[] args) {
        try {

            StackImpl stack = new StackImpl("a stack");
            InitialContext context = new InitialContext();
            context.rebind("rmi:MyStack", stack);
            System.out.println("服务器注册了一个Stack对象");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
