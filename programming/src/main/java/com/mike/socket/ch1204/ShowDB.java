package com.mike.socket.ch1204;

import java.io.PrintWriter;
import java.sql.*;

public class ShowDB {
    public static void main(String[] args) throws Exception {
        Connection con;
        Statement stmt;
        ResultSet rs;

        Class.forName("com.mysql.cj.jdbc.Driver");
        //  DriverManager.registerDriver(new Driver());

        //获取日志
        DriverManager.setLogWriter(new PrintWriter(System.out, true));

        String dbUrl = "jdbc:mysql://192.168.165.44:3306/STOREDB";
        String dbUser = "root";
        String dbPwd = "1q2w3eROOT!";
        con = java.sql.DriverManager.getConnection(dbUrl, dbUser, dbPwd);
        //   stmt = con.createStatement();
        DatabaseMetaData metaData = con.getMetaData();
        System.out.println("允许的最大连接数为：" + metaData.getMaxConnections());
        System.out.println("一个连接允许同时打开的Statement对象的数目为：" + metaData.getMaxStatements());

        ResultSet tables = metaData.getTables(null, null, null, new String[]{"TABLE"});
        DBTester.showResultSet(tables);
        con.close();

    }
}
