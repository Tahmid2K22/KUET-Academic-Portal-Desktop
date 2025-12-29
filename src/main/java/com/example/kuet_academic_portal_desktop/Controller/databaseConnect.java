package com.example.kuet_academic_portal_desktop.Controller;

import com.example.kuet_academic_portal_desktop.Model.Notice;
import com.example.kuet_academic_portal_desktop.Model.RoutineEntry;
import com.example.kuet_academic_portal_desktop.Session;
import com.example.kuet_academic_portal_desktop.Model.Assignment;
import com.example.kuet_academic_portal_desktop.Model.Contact;
import com.example.kuet_academic_portal_desktop.Model.Attendance;

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

        dbConn.createStatement().executeUpdate(
                "CREATE TABLE IF NOT EXISTS class_routine (" +
                        "id INT PRIMARY KEY AUTO_INCREMENT," +
                        "time_slot VARCHAR(50) NOT NULL," +
                        "course_no VARCHAR(20) NOT NULL," +
                        "course_name VARCHAR(200) NOT NULL," +
                        "teacher VARCHAR(100) NOT NULL," +
                        "room VARCHAR(50) NOT NULL," +
                        "type VARCHAR(20) NOT NULL," +
                        "day VARCHAR(20) NOT NULL," +
                        "week VARCHAR(10)," +
                        "year INT NOT NULL," +
                        "term INT NOT NULL," +
                        "section VARCHAR(10) NOT NULL," +
                        "department VARCHAR(50) NOT NULL)"
        );

        dbConn.createStatement().executeUpdate(
                "CREATE TABLE IF NOT EXISTS routine (" +
                        "id INT PRIMARY KEY AUTO_INCREMENT," +
                        "section VARCHAR(10) NOT NULL," +
                        "course_no VARCHAR(20) NOT NULL," +
                        "day VARCHAR(20) NOT NULL," +
                        "start_time TIME NOT NULL," +
                        "room_number VARCHAR(50) NOT NULL," +
                        "teacher VARCHAR(100) NOT NULL," +
                        "year INT NOT NULL," +
                        "term INT NOT NULL," +
                        "department VARCHAR(50) NOT NULL," +
                        "end_time TIME NOT NULL)"
        );
        dbConn.createStatement().executeUpdate(
                "CREATE TABLE IF NOT EXISTS assignments (" +
                        "id INT PRIMARY KEY AUTO_INCREMENT," +
                        "title VARCHAR(200) NOT NULL," +
                        "description TEXT NOT NULL," +
                        "course_no VARCHAR(20) NOT NULL," +
                        "course_name VARCHAR(200) NOT NULL," +
                        "due_date TIMESTAMP NOT NULL," +
                        "assigned_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                        "status VARCHAR(20) NOT NULL," +
                        "year INT NOT NULL," +
                        "term INT NOT NULL," +
                        "department VARCHAR(50) NOT NULL," +
                        "section VARCHAR(10) NOT NULL," +
                        "teacher_name VARCHAR(100) NOT NULL)"
        );

        dbConn.createStatement().executeUpdate(
                "CREATE TABLE IF NOT EXISTS contacts (" +
                        "id INT PRIMARY KEY AUTO_INCREMENT," +
                        "name VARCHAR(100) NOT NULL," +
                        "role VARCHAR(10) NOT NULL," +
                        "roll_id VARCHAR(20)," +
                        "phone VARCHAR(20)," +
                        "email VARCHAR(100)," +
                        "department VARCHAR(50)," +
                        "designation VARCHAR(50))"
        );

        dbConn.createStatement().executeUpdate(
                "CREATE TABLE IF NOT EXISTS attendance (" +
                        "id INT PRIMARY KEY AUTO_INCREMENT," +
                        "course_no VARCHAR(20) NOT NULL," +
                        "course_name VARCHAR(200) NOT NULL," +
                        "date DATE NOT NULL," +
                        "status VARCHAR(20) NOT NULL," +
                        "year INT NOT NULL," +
                        "term INT NOT NULL," +
                        "department VARCHAR(50) NOT NULL," +
                        "section VARCHAR(10) NOT NULL," +
                        "student_roll VARCHAR(20) NOT NULL," +
                        "UNIQUE KEY unique_attendance (course_no, date, student_roll))"
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

    public List<com.example.kuet_academic_portal_desktop.Model.ClassSchedule> loadClassRoutineData(String day, String week) {
        List<com.example.kuet_academic_portal_desktop.Model.ClassSchedule> routineList = new ArrayList<>();
        conn = getConn();

        StringBuilder query = new StringBuilder(
            "SELECT time_slot, course_no, course_name, teacher, room, type FROM class_routine " +
            "WHERE term=? AND year=? AND section=? AND department=?"
        );

        // Add day filter if not "All Days"
        if (day != null && !day.equals("All Days")) {
            query.append(" AND day=?");
        }

        // Add week filter if not "All Weeks"
        if (week != null && !week.equals("All Weeks")) {
            query.append(" AND (week=? OR week='Both')");
        }

        query.append(" ORDER BY time_slot");

        try (PreparedStatement stmt = conn.prepareStatement(query.toString())) {
            int paramIndex = 1;
            stmt.setString(paramIndex++, Session.getInstance().getTerm());
            stmt.setString(paramIndex++, Session.getInstance().getYear());
            stmt.setString(paramIndex++, Session.getInstance().getSection());
            stmt.setString(paramIndex++, Session.getInstance().getDepartment());

            if (day != null && !day.equals("All Days")) {
                stmt.setString(paramIndex++, day);
            }

            if (week != null && !week.equals("All Weeks")) {
                stmt.setString(paramIndex++, week);
            }

            var rs = stmt.executeQuery();

            while (rs.next()) {
                com.example.kuet_academic_portal_desktop.Model.ClassSchedule schedule =
                    new com.example.kuet_academic_portal_desktop.Model.ClassSchedule(
                        rs.getString("time_slot"),
                        rs.getString("course_no"),
                        rs.getString("course_name"),
                        rs.getString("teacher"),
                        rs.getString("room"),
                        rs.getString("type")
                    );
                routineList.add(schedule);
            }
        } catch (SQLException e) {
            System.err.println("Error loading class routine from database: " + e.getMessage());
            e.printStackTrace();
        }

        return routineList;
    }

    public boolean addClassToRoutine(String courseNo, String day, String startTime, String endTime,
                                     String roomNumber, String teacher) {
        conn = getConn();
        String query = "INSERT INTO routine (section, course_no, day, start_time, end_time, room_number, teacher, year, term, department) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, Session.getInstance().getSection());
            stmt.setString(2, courseNo);
            stmt.setString(3, day);
            stmt.setTime(4, Time.valueOf(startTime));
            stmt.setTime(5, Time.valueOf(endTime));
            stmt.setString(6, roomNumber);
            stmt.setString(7, teacher);
            stmt.setInt(8, Integer.parseInt(Session.getInstance().getYear()));
            stmt.setInt(9, Integer.parseInt(Session.getInstance().getTerm()));
            stmt.setString(10, Session.getInstance().getDepartment());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error adding class to routine: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteClassFromRoutine(int id) {
        conn = getConn();
        String query = "DELETE FROM routine WHERE id=?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting class from routine: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<RoutineEntry> loadRoutineData(String department, String year, String section) {
        List<RoutineEntry> routineList = new ArrayList<>();
        conn = getConn();

        String query = "SELECT id, section, course_no, day, start_time, end_time, room_number, teacher, year, term, department " +
                       "FROM routine WHERE department=? AND year=? AND section=? " +
                       "ORDER BY FIELD(day, 'Saturday', 'Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday'), start_time";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            // Extract year number from string like "1st Year" -> 1
            int yearNum = extractYearNumber(year);

            stmt.setString(1, department);
            stmt.setInt(2, yearNum);
            stmt.setString(3, section);

            System.out.println("Loading routine for: Department=" + department + ", Year=" + yearNum + ", Section=" + section);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                RoutineEntry entry = new RoutineEntry(
                    rs.getInt("id"),
                    rs.getString("section"),
                    rs.getString("course_no"),
                    rs.getString("day"),
                    rs.getTime("start_time"),
                    rs.getTime("end_time"),
                    rs.getString("room_number"),
                    rs.getString("teacher"),
                    rs.getInt("year"),
                    rs.getInt("term"),
                    rs.getString("department")
                );
                routineList.add(entry);
            }

            System.out.println("Loaded " + routineList.size() + " routine entries from database");

        } catch (SQLException e) {
            System.err.println("Error loading routine from database: " + e.getMessage());
            e.printStackTrace();
        }

        return routineList;
    }

    public List<Assignment> loadAssignmentData(String department, int year, int term, String section) {
        List<Assignment> assignmentList = new ArrayList<>();
        conn = getConn();

        String query = "SELECT id, title, description, course_no, course_name, due_date, assigned_date, " +
                       "status, year, term, department, section, teacher_name " +
                       "FROM assignments WHERE department=? AND year=? AND term=? AND section=? " +
                       "ORDER BY due_date ASC";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, department);
            stmt.setInt(2, year);
            stmt.setInt(3, term);
            stmt.setString(4, section);

            System.out.println("Loading assignments for: Department=" + department +
                             ", Year=" + year + ", Term=" + term + ", Section=" + section);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Assignment assignment = new Assignment(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("course_no"),
                    rs.getString("course_name"),
                    rs.getTimestamp("due_date"),
                    rs.getTimestamp("assigned_date"),
                    rs.getString("status"),
                    rs.getInt("year"),
                    rs.getInt("term"),
                    rs.getString("department"),
                    rs.getString("section"),
                    rs.getString("teacher_name")
                );
                assignmentList.add(assignment);
            }

            System.out.println("Loaded " + assignmentList.size() + " assignments from database");

        } catch (SQLException e) {
            System.err.println("Error loading assignments from database: " + e.getMessage());
            e.printStackTrace();
        }

        return assignmentList;
    }

    private int extractYearNumber(String yearStr) {
        if (yearStr == null) return 1;

        if (yearStr.startsWith("1")) return 1;
        if (yearStr.startsWith("2")) return 2;
        if (yearStr.startsWith("3")) return 3;
        if (yearStr.startsWith("4")) return 4;

        return 1;
    }

    public List<Contact> loadContactData() {
        List<Contact> contacts = new ArrayList<>();
        conn = getConn();
        String query = "SELECT id, name, role, roll_id, phone, email, department, designation FROM contacts ORDER BY name";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Contact contact = new Contact(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("role"),
                    rs.getString("roll_id"),
                    rs.getString("phone"),
                    rs.getString("email"),
                    rs.getString("department"),
                    rs.getString("designation")
                );
                contacts.add(contact);
            }

            System.out.println("Loaded " + contacts.size() + " contacts from database");

        } catch (SQLException e) {
            System.err.println("Error loading contacts from database: " + e.getMessage());
            e.printStackTrace();
        }

        return contacts;
    }

    public List<Attendance> loadAttendanceSummary(String studentRoll, int year, int term, String department, String section) {
        List<Attendance> attendanceSummary = new ArrayList<>();
        conn = getConn();

        String query = "SELECT " +
                       "course_no, " +
                       "course_name, " +
                       "COUNT(*) as total_classes, " +
                       "SUM(CASE WHEN status = 'Present' THEN 1 ELSE 0 END) as attended_classes " +
                       "FROM attendance " +
                       "WHERE student_roll = ? AND year = ? AND term = ? AND department = ? AND section = ? " +
                       "GROUP BY course_no, course_name " +
                       "ORDER BY course_no";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, studentRoll);
            stmt.setInt(2, year);
            stmt.setInt(3, term);
            stmt.setString(4, department);
            stmt.setString(5, section);

            System.out.println("Loading attendance for: Roll=" + studentRoll + ", Year=" + year +
                             ", Term=" + term + ", Dept=" + department + ", Section=" + section);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Attendance attendance = new Attendance(
                    rs.getString("course_no"),
                    rs.getString("course_name"),
                    rs.getInt("total_classes"),
                    rs.getInt("attended_classes")
                );
                attendanceSummary.add(attendance);
            }

            System.out.println("Loaded " + attendanceSummary.size() + " attendance records from database");

        } catch (SQLException e) {
            System.err.println("Error loading attendance from database: " + e.getMessage());
            e.printStackTrace();
        }

        return attendanceSummary;
    }

}
