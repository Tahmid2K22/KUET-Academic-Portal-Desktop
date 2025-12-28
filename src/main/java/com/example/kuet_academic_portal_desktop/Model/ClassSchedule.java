package com.example.kuet_academic_portal_desktop.Model;

public class ClassSchedule {
    private int id;
    private String timeSlot;
    private String courseCode;
    private String courseName;
    private String teacher;
    private String room;
    private String type;
    private String day;
    private String week;

    // Constructor for display (without id, day, week)
    public ClassSchedule(String timeSlot, String courseCode, String courseName, String teacher, String room, String type) {
        this.timeSlot = timeSlot;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.teacher = teacher;
        this.room = room;
        this.type = type;
    }

    // Full constructor
    public ClassSchedule(int id, String timeSlot, String courseCode, String courseName, String teacher,
                        String room, String type, String day, String week) {
        this.id = id;
        this.timeSlot = timeSlot;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.teacher = teacher;
        this.room = room;
        this.type = type;
        this.day = day;
        this.week = week;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }
}
