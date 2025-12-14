package com.example.kuet_academic_portal_desktop.Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class databaseConnect {

    public Connection connect() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/StudentDB";
        String username = "root";
        String password = "123456";
        return DriverManager.getConnection(url, username, password);
    }
    public void disconnect(Connection connection) throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public void initialize() {
        try{
            try (Connection conn = connect()) {
                String sql = "CREATE DATABASE IF NOT EXISTS StudentDB";
                conn.createStatement().executeUpdate(sql);
                String sql2 = "USE StudentDB";
                String sql3 = "CREATE TABLE IF NOT EXISTS students (" +
                        "id INT PRIMARY KEY AUTO_INCREMENT," +
                        "name VARCHAR(100) NOT NULL," +
                        "email VARCHAR(100) NOT NULL," +
                        "roll INT NOT NULL UNIQUE," +
                        "section VARCHAR(10) NOT NULL," +
                        "department VARCHAR(50) NOT NULL," +
                        "year INT NOT NULL," +
                        "term INT NOT NULL," +
                        "phone VARCHAR(15) NOT NULL," +
                        "password VARCHAR(100) NOT NULL" +
                        ")";

                conn.createStatement().executeUpdate(sql2);
                conn.createStatement().executeUpdate(sql3);
            }
        } catch (SQLException e) {
            System.out.println("Database initialization error: " + e.getMessage());
        }
    }
}



