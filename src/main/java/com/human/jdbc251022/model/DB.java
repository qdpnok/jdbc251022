package com.human.jdbc251022.model;

import java.sql.*;

public class DB {

    // 연결 정보
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String USER = "user_name";
    private static final String PASSWORD = "password";

    // DB 연결
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver"); // 드라이버 로드
        } catch (ClassNotFoundException e) {
            System.err.println("Oracle JDBC 드라이버를 찾을 수 없습니다.");
            throw new SQLException(e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // 자원 해제
    public static void close(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
