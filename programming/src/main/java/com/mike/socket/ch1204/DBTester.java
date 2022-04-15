package com.mike.socket.ch1204;

import com.mysql.jdbc.Driver;

import java.io.PrintWriter;
import java.sql.*;

public class DBTester {

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
        stmt = con.createStatement();

        // String name1 = new String("小王".getBytes("GB2312"), "ISO-8859-1");
        //String address1 = new String("上海".getBytes("GB2312"), "ISO-8859-1");
        String name1 = new String("小王");
        String address1 = new String("上海");
        stmt.executeUpdate("insert into CUSTOMERS(NAME,AGE,ADDRESS)" + "VALUES('" + name1 + "',20,'" + address1 + "')");
        rs = stmt.executeQuery("SELECT * FROM CUSTOMERS");

//        while (rs.next()) {
//            long id = rs.getLong(1);
//            String name = rs.getString(2);
//            int age = rs.getInt(3);
//            String address = rs.getString(4);
//
//            // if (name != null) name = new String(name.getBytes("GB2312"), "ISO-8859-1");
//            // if (address != null) address = new String(address.getBytes("GB2312"), "ISO-8859-1");
//            System.out.println("id=" + id + ",name=" + name + ",age=" + age + ",address=" + address);
//            //     stmt.executeUpdate("delete form CUSTOMERS WHERE name='" + name1 + "'");
//        }
        showResultSet(rs);
    }

    public static void showResultSet(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            if (i > 1) System.out.print(",");
            System.out.print(metaData.getColumnLabel(i));
        }
        System.out.println();
        while (rs.next()) {
            for (int i = 1; i <= columnCount; i++) {
                if (i > 1) System.out.print(",");
                System.out.print(rs.getString(i));
            }
            System.out.println();
        }
        rs.close();
    }
}
