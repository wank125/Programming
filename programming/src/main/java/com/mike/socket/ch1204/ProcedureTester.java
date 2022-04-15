package com.mike.socket.ch1204;


import java.io.*;
import java.sql.*;

/**
 * CREATE  PROCEDURE demoSp(IN inputParam VARCHAR(255),INOUT inOutParam INT)
 * BEGIN
 * DECLARE z INT;
 * SET z= inOutParam +1;
 * SET inOutParam=z;
 * SELECT CONCAT("hello",inputParam) ;
 * END
 */


public class ProcedureTester {
    public static void main(String[] args) throws Exception {

        Class.forName("com.mysql.cj.jdbc.Driver");

        //获取日志
        DriverManager.setLogWriter(new PrintWriter(System.out, true));

        String dbUrl = "jdbc:mysql://192.168.165.44:3306/STOREDB";
        String dbUser = "root";
        String dbPwd = "1q2w3eROOT!";
        Connection con = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
        CallableStatement statement = con.prepareCall("{call demoSp(?,?)}");
        statement.setString(1, "Tom");
        statement.registerOutParameter(2, Types.INTEGER);
        statement.setInt(2, 1);
        boolean hadResults = statement.execute();

        if (hadResults) {
            ResultSet rs = statement.getResultSet();
            DBTester.showResultSet(rs);
        }
        int anInt = statement.getInt(2);
        con.close();
    }

    private void test(Blob blob) throws SQLException, IOException {
        InputStream binaryStream = blob.getBinaryStream();
        FileOutputStream fout = new FileOutputStream("aa");
        int cout;
        byte[] buffer = new byte[1024];
        while ((cout = binaryStream.read(buffer)) != -1) {
            fout.write(buffer, 0, cout);
        }
        fout.close();
        binaryStream.close();

    }

    private void setBinaryStream(int parameterIndex, InputStream in, int length) {

    }
}
