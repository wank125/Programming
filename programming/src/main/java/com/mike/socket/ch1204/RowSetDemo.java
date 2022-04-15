package com.mike.socket.ch1204;

import com.sun.rowset.CachedRowSetImpl;

import javax.sql.rowset.CachedRowSet;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class RowSetDemo {

    private CachedRowSet rowSet;

    public static void main(String[] args) throws Exception {
        Connection con;
        Statement stmt;
        ResultSet rs;

        Class.forName("com.mysql.cj.jdbc.Driver");
        //获取日志
        DriverManager.setLogWriter(new PrintWriter(System.out, true));

        String dbUrl = "jdbc:mysql://192.168.165.44:3306/STOREDB";
        String dbUser = "root";
        String dbPwd = "1q2w3eROOT!";

        CachedRowSetImpl rowSet = new CachedRowSetImpl();
        rowSet.setUrl(dbUrl);
        rowSet.setUsername(dbUser);
        rowSet.setPassword(dbPwd);
        rowSet.setCommand("SELECT * FROM CUSTOMERS");
        rowSet.execute();

        ResultSet original = rowSet.getOriginal();

        DBTester.showResultSet(original);
    }
}
