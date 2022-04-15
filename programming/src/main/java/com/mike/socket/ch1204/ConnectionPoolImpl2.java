package com.mike.socket.ch1204;

import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

public class ConnectionPoolImpl2 implements ConnectionPool {

    private ConnectionProvider provider = new ConnectionProvider();

    private final ArrayList<Connection> pool = new ArrayList<>();
    private int poolSize = 5;

    public ConnectionPoolImpl2() {
    }

    public ConnectionPoolImpl2(int poolSize) {
        this.poolSize = poolSize;
    }

    @Override
    public Connection getConnection() throws SQLException {
        synchronized (pool) {
            if (!pool.isEmpty()) {
                int last = pool.size() - 1;
                Connection connection = pool.remove(last);
                return connection;
            }
            Connection connection = provider.getConnection();
            return getProxy(connection, this);
        }
    }

    @Override
    public void releaseConnection(Connection con) throws SQLException {
        synchronized (pool) {
            int currentSize = pool.size();
            if (currentSize < poolSize) {
                pool.add(con);
                return;
            }
        }
        closeJdbcConnection(con);
    }

    protected void finalize() {
        close();
    }

    private void closeJdbcConnection(Connection connection) throws SQLException {
        ConnectionP conP = (ConnectionP) connection;
        conP.getJdbcConnection().close();
    }

    @Override
    public void close() {
        Iterator<Connection> iterator = pool.iterator();
        while (iterator.hasNext()) {
            try {
                closeJdbcConnection(iterator.next());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private Connection getProxy(final Connection con, final ConnectionPool pool) {
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (method.getName().equals("close")) {
                    pool.releaseConnection((Connection) proxy);
                    return null;
                } else if (method.getName().equals("getJdbcConnection")) {
                    return con;
                } else {
                    return method.invoke(con, args);
                }
            }
        };

        return (Connection) Proxy.newProxyInstance(ConnectionP.class.getClassLoader(), new Class[]{ConnectionP.class}, handler);
    }

    class ConnectionProvider {
        private final String dbUrl = "jdbc:mysql://192.168.165.44:3306/STOREDB";
        private final String dbUser = "root";
        private final String dbPwd = "1q2w3eROOT!";

        public ConnectionProvider() {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            //获取日志
            DriverManager.setLogWriter(new PrintWriter(System.out, true));
        }

        public Connection getConnection() throws SQLException {
            Connection con = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
            return con;
        }
    }

    interface ConnectionP extends Connection {
        public Connection getJdbcConnection();
    }
}
