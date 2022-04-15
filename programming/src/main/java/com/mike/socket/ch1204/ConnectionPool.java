package com.mike.socket.ch1204;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionPool {

    public Connection getConnection() throws SQLException;

    public void releaseConnection(Connection con) throws SQLException;

    public void close();

}
