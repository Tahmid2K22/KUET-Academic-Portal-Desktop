package com.example.kuet_academic_portal_desktop.Model;

public class Contact {
    private int contactId;
    private String name;
    private String role;
    private String rollId;
    private String phone;
    private String email;
    private String department;
    private String designation;

    public Contact(int contactId, String name, String role, String rollId,
                   String phone, String email, String department, String designation) {
        this.contactId = contactId;
        this.name = name;
        this.role = role;
        this.rollId = rollId;
        this.phone = phone;
        this.email = email;
        this.department = department;
        this.designation = designation;
    }

    // Getters
    public int getContactId() { return contactId; }
    public String getName() { return name; }
    public String getRole() { return role; }
    public String getRollId() { return rollId; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getDepartment() { return department; }
    public String getDesignation() { return designation; }

    // Setters
    public void setContactId(int contactId) { this.contactId = contactId; }
    public void setName(String name) { this.name = name; }
    public void setRole(String role) { this.role = role; }
    public void setRollId(String rollId) { this.rollId = rollId; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setEmail(String email) { this.email = email; }
    public void setDepartment(String department) { this.department = department; }
    public void setDesignation(String designation) { this.designation = designation; }

    @Override
    public String toString() {
        return "Contact{" +
                "contactId=" + contactId +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}

