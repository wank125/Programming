package com.mike.socket.ch1204;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class PoolTester implements Runnable {
    ConnectionPool pool = new ConnectionPoolImpl1();

    public static void main(String[] args) throws InterruptedException, CloneNotSupportedException {
        PoolTester tester = new PoolTester();
        Thread[] threads = new Thread[3];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(tester);
            threads[i].start();
            Thread.sleep(30);
        }

        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
        }
        tester.close();
    }

    public void close() {
        pool.close();
    }

    @Override
    public void run() {
        Connection connection = null;
        try {
            connection = pool.getConnection();
            System.out.println(Thread.currentThread().getName() + "：从连接池取出一个连接" + connection);
            Statement statement = connection.createStatement();
            statement.executeUpdate("insert into CUSTOMERS(NAME,AGE,ADDRESS)" + "VALUES('小王',20,'上海')");
            statement.close();
            pool.releaseConnection(connection);
            System.out.println(Thread.currentThread().getName() + ":释放了连接" + connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
