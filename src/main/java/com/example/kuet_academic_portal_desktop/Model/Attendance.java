package com.example.kuet_academic_portal_desktop.Model;

import java.sql.Date;

public class Attendance {
    private int id;
    private String courseNo;
    private String courseName;
    private Date date;
    private String status; // Present, Absent, Late
    private int year;
    private int term;
    private String department;
    private String section;
    private String studentRoll;
    private int totalClasses;
    private int attendedClasses;
    private double attendancePercentage;

    // Constructor for individual attendance record
    public Attendance(int id, String courseNo, String courseName, Date date, String status,
                     int year, int term, String department, String section, String studentRoll) {
        this.id = id;
        this.courseNo = courseNo;
        this.courseName = courseName;
        this.date = date;
        this.status = status;
        this.year = year;
        this.term = term;
        this.department = department;
        this.section = section;
        this.studentRoll = studentRoll;
    }

    // Constructor for attendance summary
    public Attendance(String courseNo, String courseName, int totalClasses, int attendedClasses) {
        this.courseNo = courseNo;
        this.courseName = courseName;
        this.totalClasses = totalClasses;
        this.attendedClasses = attendedClasses;
        this.attendancePercentage = totalClasses > 0 ? (attendedClasses * 100.0 / totalClasses) : 0.0;
    }

    // Getters
    public int getId() { return id; }
    public String getCourseNo() { return courseNo; }
    public String getCourseName() { return courseName; }
    public Date getDate() { return date; }
    public String getStatus() { return status; }
    public int getYear() { return year; }
    public int getTerm() { return term; }
    public String getDepartment() { return department; }
    public String getSection() { return section; }
    public String getStudentRoll() { return studentRoll; }
    public int getTotalClasses() { return totalClasses; }
    public int getAttendedClasses() { return attendedClasses; }
    public double getAttendancePercentage() { return attendancePercentage; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setCourseNo(String courseNo) { this.courseNo = courseNo; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public void setDate(Date date) { this.date = date; }
    public void setStatus(String status) { this.status = status; }
    public void setYear(int year) { this.year = year; }
    public void setTerm(int term) { this.term = term; }
    public void setDepartment(String department) { this.department = department; }
    public void setSection(String section) { this.section = section; }
    public void setStudentRoll(String studentRoll) { this.studentRoll = studentRoll; }
    public void setTotalClasses(int totalClasses) { this.totalClasses = totalClasses; }
    public void setAttendedClasses(int attendedClasses) { this.attendedClasses = attendedClasses; }
    public void setAttendancePercentage(double attendancePercentage) { this.attendancePercentage = attendancePercentage; }

    @Override
    public String toString() {
        return "Attendance{" +
                "courseNo='" + courseNo + '\'' +
                ", courseName='" + courseName + '\'' +
                ", attendancePercentage=" + String.format("%.2f", attendancePercentage) + "%" +
                '}';
    }
}

