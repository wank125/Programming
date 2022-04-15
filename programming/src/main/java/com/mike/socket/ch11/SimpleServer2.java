package com.mike.socket.ch11;

import javax.naming.InitialContext;

/**
 * 服务端回调
 */
public class SimpleServer2 {

    //rmiregistry 命令需要在target/classes目录下运行,要不然找不到注册类

    public static void main(String[] args) {
        try {

            StockQuoteRegistryImpl registry = new StockQuoteRegistryImpl();
            InitialContext namingContext = new InitialContext();
            namingContext.rebind("rmi:StockQuoteRegistry", registry);
            new Thread(registry).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
