package com.example.kuet_academic_portal_desktop.Model;

import java.sql.Timestamp;

public class Assignment {
    private int id;
    private String title;
    private String description;
    private String courseNo;
    private String courseName;
    private Timestamp dueDate;
    private Timestamp assignedDate;
    private String status;
    private int year;
    private int term;
    private String department;
    private String section;
    private String teacherName;

    public Assignment(int id, String title, String description, String courseNo,
                     String courseName, Timestamp dueDate, Timestamp assignedDate,
                     String status, int year, int term, String department,
                     String section, String teacherName) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.courseNo = courseNo;
        this.courseName = courseName;
        this.dueDate = dueDate;
        this.assignedDate = assignedDate;
        this.status = status;
        this.year = year;
        this.term = term;
        this.department = department;
        this.section = section;
        this.teacherName = teacherName;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getCourseNO() { return courseNo; }
    public String getCourseName() { return courseName; }
    public Timestamp getDueDate() { return dueDate; }
    public Timestamp getAssignedDate() { return assignedDate; }
    public String getStatus() { return status; }
    public int getYear() { return year; }
    public int getTerm() { return term; }
    public String getDepartment() { return department; }
    public String getSection() { return section; }
    public String getTeacherName() { return teacherName; }

    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setCourseCode(String courseNo) { this.courseNo = courseNo; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public void setDueDate(Timestamp dueDate) { this.dueDate = dueDate; }
    public void setAssignedDate(Timestamp assignedDate) { this.assignedDate = assignedDate; }
    public void setStatus(String status) { this.status = status; }
    public void setYear(int year) { this.year = year; }
    public void setTerm(int term) { this.term = term; }
    public void setDepartment(String department) { this.department = department; }
    public void setSection(String section) { this.section = section; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }

    public String getFormattedDueDate() {
        if (dueDate == null) return "N/A";
        return dueDate.toString().substring(0, 16).replace("T", " ");
    }

    public String getFormattedAssignedDate() {
        if (assignedDate == null) return "N/A";
        return assignedDate.toString().substring(0, 16).replace("T", " ");
    }

    public boolean isOverdue() {
        if (dueDate == null) return false;
        return dueDate.before(new Timestamp(System.currentTimeMillis()));
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", courseNo='" + courseNo + '\'' +
                ", dueDate=" + dueDate +
                ", status='" + status + '\'' +
                '}';
    }
}
