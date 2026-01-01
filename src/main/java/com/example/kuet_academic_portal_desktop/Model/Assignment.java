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

    public Assignment(int id, String title, String description, String courseNo, String courseName,
                     Timestamp dueDate, Timestamp assignedDate, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.courseNo = courseNo;
        this.courseName = courseName;
        this.dueDate = dueDate;
        this.assignedDate = assignedDate;
        this.status = status;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getCourseNO() { return courseNo; }
    public String getCourseName() { return courseName; }
    public Timestamp getDueDate() { return dueDate; }
    public Timestamp getAssignedDate() { return assignedDate; }
    public String getStatus() { return status; }

    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setStatus(String status) { this.status = status; }

    // Helper methods
    public boolean isOverdue() {
        if (dueDate == null) return false;
        return dueDate.before(new Timestamp(System.currentTimeMillis()));
    }

    public String getTeacherName() {
        return "N/A"; // Default value if not available
    }

    public String getFormattedAssignedDate() {
        if (assignedDate == null) return "N/A";
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMM dd, yyyy hh:mm a");
        return sdf.format(assignedDate);
    }

    public String getFormattedDueDate() {
        if (dueDate == null) return "N/A";
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMM dd, yyyy hh:mm a");
        return sdf.format(dueDate);
    }
}
