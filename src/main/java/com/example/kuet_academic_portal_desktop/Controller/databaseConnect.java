package com.example.kuet_academic_portal_desktop.Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class databaseConnect {

    private static Connection conn;

    public static Connection getConn() {
        if(conn == null) {
            databaseConnect dbConnect = new databaseConnect();
            try {
                conn = dbConnect.initialize();
                System.out.println("Database connected successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }

    public Connection connect() throws SQLException {
        if (conn == null || conn.isClosed()) {
            String url = "jdbc:mysql://localhost:3306/StudentDB";
            String username = "root";
            String password = "123456";
            conn = DriverManager.getConnection(url, username, password);
        }
        return conn;
    }

    public void disconnect() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }

    public Connection initialize() throws SQLException {
        Connection connTemp = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/", "root", "123456"
        );

        connTemp.createStatement().executeUpdate("CREATE DATABASE IF NOT EXISTS StudentDB");

        Connection dbConn = connect();

        dbConn.createStatement().executeUpdate(
                "CREATE TABLE IF NOT EXISTS students (" +
                        "id INT PRIMARY KEY AUTO_INCREMENT," +
                        "name VARCHAR(100) NOT NULL," +
                        "email VARCHAR(100) NOT NULL," +
                        "roll INT NOT NULL UNIQUE," +
                        "section VARCHAR(10) NOT NULL," +
                        "department VARCHAR(50) NOT NULL," +
                        "year INT NOT NULL," +
                        "term INT NOT NULL," +
                        "phone VARCHAR(15) NOT NULL," +
                        "password VARCHAR(100) NOT NULL)"
        );

        dbConn.createStatement().executeUpdate(
                "CREATE TABLE IF NOT EXISTS users (" +
                        "id INT PRIMARY KEY AUTO_INCREMENT," +
                        "email VARCHAR(50) NOT NULL UNIQUE," +
                        "password VARCHAR(100) NOT NULL," +
                        "role VARCHAR(20) NOT NULL DEFAULT 'user')"
        );

        return dbConn;
    }
}
