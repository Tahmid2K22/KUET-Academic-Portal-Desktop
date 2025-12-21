package com.example.kuet_academic_portal_desktop.Controller;

import com.example.kuet_academic_portal_desktop.Model.Notice;
import com.example.kuet_academic_portal_desktop.Session;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class databaseConnect {

    private static Connection conn;

    public static Connection getConn() {
        try {
            if (conn == null || conn.isClosed()) {
                databaseConnect dbConnect = new databaseConnect();
                conn = dbConnect.initialize();
                System.out.println("Database connected successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
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

        dbConn.createStatement().executeUpdate(
                "CREATE TABLE IF NOT EXISTS notices (" +
                        "id INT PRIMARY KEY AUTO_INCREMENT," +
                        "title VARCHAR(200) NOT NULL," +
                        "description TEXT NOT NULL," +
                        "date TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                        "term INT NOT NULL," +
                        "year INT NOT NULL)"


        );

        return dbConn;
    }

    public Object loadDashboardData() {
        conn = getConn();
        String query = "SELECT * FROM dashboard_data ";
        try (Statement stmt = conn.createStatement();
             var rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                return rs.getObject("data");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Notice> loadNoticeData() {
        List<Notice> notices = new ArrayList<>();
        conn = getConn();
        String query = "SELECT title, description, date FROM notices WHERE term=? and year=? ORDER BY date DESC";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, Session.getInstance().getTerm());
            stmt.setString(2, Session.getInstance().getYear());
            System.out.println("Loading notices for term: " + Session.getInstance().getTerm() + ", year: " + Session.getInstance().getYear());
            var rs = stmt.executeQuery();

            while (rs.next()) {
                Notice notice = new Notice(
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("date")
                );
                System.out.println("Notice   loaded: " + notice.getTitle());
                notices.add(notice);
            }
        } catch (SQLException e) {
            System.err.println("Error loading notices from database: " + e.getMessage());
            e.printStackTrace();
        }

        return notices;
    }

}
