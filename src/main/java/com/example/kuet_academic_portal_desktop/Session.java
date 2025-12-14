package com.example.kuet_academic_portal_desktop;

public class Session {
    private static Session instance;

    private String email;
    private int userId;
    private String role;


    private String name;

    public Session() { }

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
}
