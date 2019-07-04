package cn.com.mysnake.dao;

import java.sql.*;

public class DaoHepler {

    public DaoHepler() {
        try {
//            com.mysql.jdbc.Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 属性 --------------------------
    private static Connection conn;
    private Statement stm;
    private PreparedStatement pstm;
    private ResultSet rs;
//&useUnicode=true&characterEncoding=UTF-8 ?useSSL=false
    // 获得连接--------------------------
    public static Connection getConn() {
        try {
            System.out.println("getConnection（）开始------");
            conn = DriverManager.getConnection(
                    "jdbc:mysql://111.230.27.166:3306/snake?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false",
                    "snake", "KbMR6KES6sEAM2y8");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getConnection（）错误");
        }
        return conn;
    }

    // 执行增、删、改SQL语句--------------------
    public int executeUpdate(String sql) throws SQLException {
        if (getConn() == null) {
            System.out.println("与数据库连接失败!");
            return -1;
        }
        stm = conn.createStatement();
        return stm.executeUpdate(sql);
    }

    // 重载执行增、删、改SQL语句--------------------
    public int executeUpdate(String sql, Object[] obj) throws SQLException {
        if (getConn() == null) {
            System.out.println("与数据库连接失败!");
            return -1;
        }
        pstm = conn.prepareStatement(sql);
        if (obj != null) {
            for (int i = 0; i < obj.length; i++) {
                pstm.setObject(i + 1, obj[i]);
            }
        }
        return pstm.executeUpdate();
    }

    public static PreparedStatement getPreparedStatement(String sql)
            throws SQLException {

        return getConn().prepareStatement(sql);
    }


    public static PreparedStatement setPreparedStatementParam(
            PreparedStatement statement, Object obj[]) throws SQLException {

        for (int i = 0; i < obj.length; i++) {
            statement.setObject(i + 1, obj[i]);
        }
        return statement;
    }

    public static boolean openAutoCommit() throws SQLException {
        conn=getConn();
        if(conn.getAutoCommit()) {
            conn.setAutoCommit(false);
        }
        return true;
    }

    // 执行查询SQL语句----------------------------
    public ResultSet executeQuery(String sql) throws SQLException {
        if (getConn() == null) {
            System.out.println("与数据库连接失败!");
            return null;
        }
        stm = conn.createStatement();
        rs = stm.executeQuery(sql);
        return rs;
    }

    // 重载执行查询SQL语句----------------------------
    public ResultSet executeQuery(String sql, Object[] obj) throws SQLException {
        if (getConn() == null) {
            System.out.println("与数据库连接失败!");
            return null;
        }
        pstm = conn.prepareStatement(sql);
        if (obj != null) {
            for (int i = 0; i < obj.length; i++) {
                pstm.setObject(i + 1, obj[i]);
            }
        }
        rs = pstm.executeQuery();
        return rs;
    }

    // 关闭ResultSet
    public void closeResultSet() {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {

                e.printStackTrace();
            }
        }
    }

    // 关闭Connection
    public void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 关闭Statement和PreparedStatement
    public void closeStatement() {
        if (stm != null) {
            try {
                stm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (pstm != null) {
            try {
                pstm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
