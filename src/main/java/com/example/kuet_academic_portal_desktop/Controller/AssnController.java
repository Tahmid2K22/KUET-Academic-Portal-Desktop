package com.example.kuet_academic_portal_desktop.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.*;
import java.time.LocalDate;

public class AssnController {

    @FXML private TextField titleFld;
    @FXML private TextField courseNoFld;
    @FXML private TextField courseNameFld;
    @FXML private TextArea descArea;
    @FXML private DatePicker assignedPicker;
    @FXML private DatePicker duePicker;
    @FXML private ComboBox<String> yearBox;
    @FXML private ComboBox<String> termBox;
    @FXML private ComboBox<String> sectionBox;

    @FXML
    public void initialize() {
        // Initialize combo boxes with options
        yearBox.getItems().addAll("1", "2", "3", "4");
        termBox.getItems().addAll("1", "2");
        sectionBox.getItems().addAll("A", "B", "C", "All");

        // Set default values
        yearBox.setValue("2");
        termBox.setValue("2");
        sectionBox.setValue("A");
        assignedPicker.setValue(LocalDate.now());
    }

    @FXML
    public void addAssn() {
        String title = titleFld.getText().trim();
        String courseNo = courseNoFld.getText().trim();
        String courseName = courseNameFld.getText().trim();
        String description = descArea.getText().trim();
        LocalDate assignedDate = assignedPicker.getValue();
        LocalDate dueDate = duePicker.getValue();
        String yearText = yearBox.getValue();
        String termText = termBox.getValue();
        String section = sectionBox.getValue();

        // Validation
        if (title.isEmpty() || courseNo.isEmpty() || courseName.isEmpty() || assignedDate == null || dueDate == null) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Please fill all required fields");
            return;
        }

        if (dueDate.isBefore(assignedDate)) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Due date cannot be before assigned date");
            return;
        }

        Connection conn;
        PreparedStatement stmt = null;

        try {
            int year = Integer.parseInt(yearText);
            int term = Integer.parseInt(termText);

            conn = databaseConnect.getConn();
            String query = "INSERT INTO assignments (course_no, course_name, title, description, assigned_date, due_date, status, year, term, department, section, teacher_name) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, courseNo);
            stmt.setString(2, courseName);
            stmt.setString(3, title);
            stmt.setString(4, description);
            stmt.setTimestamp(5, Timestamp.valueOf(assignedDate.atStartOfDay()));
            stmt.setTimestamp(6, Timestamp.valueOf(dueDate.atStartOfDay()));
            stmt.setString(7, "Active");
            stmt.setInt(8, year);
            stmt.setInt(9, term);
            stmt.setString(10, "CSE");
            stmt.setString(11, section);
            stmt.setString(12, "");
            stmt.executeUpdate();

            showAlert(Alert.AlertType.INFORMATION, "Success", "Assignment added successfully!");
            clearForm();

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.err.println("Error closing statement: " + e.getMessage());
            }
        }
    }

    @FXML
    public void clearForm() {
        titleFld.clear();
        courseNoFld.clear();
        courseNameFld.clear();
        descArea.clear();
        assignedPicker.setValue(LocalDate.now());
        duePicker.setValue(null);
        yearBox.setValue("2");
        termBox.setValue("2");
        sectionBox.setValue("A");
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
