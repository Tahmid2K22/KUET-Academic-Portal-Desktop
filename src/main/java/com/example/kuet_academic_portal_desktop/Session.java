package com.example.kuet_academic_portal_desktop;

public class Session {
    private static Session instance;

    private String email;
    private int userId;
    private String role;
    private String department;
    private String year;
    private String section;
    private String roll;
    private String term;
    private String name;


    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getDepartment() { return department; } public void setDepartment(String department) { this.department = department; }

    public String getYear() { return year; } public void setYear(String year) { this.year = year; }

    public String getSection() { return section; } public void setSection(String section) { this.section = section; }

    public String getRoll() { return roll; } public void setRoll(String roll) { this.roll = roll; }

    public String getTerm() { return term; } public void setTerm(String term) { this.term = term; }
}
