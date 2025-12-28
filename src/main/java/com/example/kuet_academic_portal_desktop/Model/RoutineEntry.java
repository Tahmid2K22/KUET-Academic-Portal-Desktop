package com.example.kuet_academic_portal_desktop.Model;

import java.sql.Time;

public class RoutineEntry {
    private int id;
    private String section;
    private String courseNo;
    private String day;
    private Time startTime;
    private Time endTime;
    private String roomNumber;
    private String teacher;
    private int year;
    private int term;
    private String department;

    public RoutineEntry(int id, String section, String courseNo, String day,
                       Time startTime, Time endTime, String roomNumber,
                       String teacher, int year, int term, String department) {
        this.id = id;
        this.section = section;
        this.courseNo = courseNo;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.roomNumber = roomNumber;
        this.teacher = teacher;
        this.year = year;
        this.term = term;
        this.department = department;
    }

    // Getters
    public int getId() { return id; }
    public String getSection() { return section; }
    public String getCourseNo() { return courseNo; }
    public String getDay() { return day; }
    public Time getStartTime() { return startTime; }
    public Time getEndTime() { return endTime; }
    public String getRoomNumber() { return roomNumber; }
    public String getTeacher() { return teacher; }
    public int getYear() { return year; }
    public int getTerm() { return term; }
    public String getDepartment() { return department; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setSection(String section) { this.section = section; }
    public void setCourseNo(String courseNo) { this.courseNo = courseNo; }
    public void setDay(String day) { this.day = day; }
    public void setStartTime(Time startTime) { this.startTime = startTime; }
    public void setEndTime(Time endTime) { this.endTime = endTime; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
    public void setTeacher(String teacher) { this.teacher = teacher; }
    public void setYear(int year) { this.year = year; }
    public void setTerm(int term) { this.term = term; }
    public void setDepartment(String department) { this.department = department; }

    // Helper methods
    public String getTimeSlot() {
        return startTime.toString().substring(0, 5) + " - " + endTime.toString().substring(0, 5);
    }

    public String getFormattedInfo() {
        return courseNo + "\n" + teacher + "\nRoom: " + roomNumber;
    }

    @Override
    public String toString() {
        return "RoutineEntry{" +
                "id=" + id +
                ", section='" + section + '\'' +
                ", courseNo='" + courseNo + '\'' +
                ", day='" + day + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", roomNumber='" + roomNumber + '\'' +
                ", teacher='" + teacher + '\'' +
                ", year=" + year +
                ", term=" + term +
                ", department='" + department + '\'' +
                '}';
    }
}

