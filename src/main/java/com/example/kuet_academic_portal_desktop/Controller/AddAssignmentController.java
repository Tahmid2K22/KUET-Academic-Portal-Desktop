package com.example.kuet_academic_portal_desktop.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.*;
import java.time.LocalDate;

public class AddAssignmentController {
    @FXML private TextField titleField;
    @FXML private TextField courseNoField;
    @FXML private TextField courseNameField;
    @FXML private TextArea descriptionArea;
    @FXML private DatePicker assignedDatePicker;
    @FXML private DatePicker dueDatePicker;
    @FXML private ComboBox<String> yearCombo;
    @FXML private ComboBox<String> termCombo;
    @FXML private ComboBox<String> sectionCombo;

    private final databaseConnect db = new databaseConnect();

    @FXML
    public void initialize() {
        yearCombo.getItems().addAll("1", "2", "3", "4");
        termCombo.getItems().addAll("1", "2");
        sectionCombo.getItems().addAll("A", "B", "C");
        assignedDatePicker.setValue(LocalDate.now());
    }

    @FXML
    public void handleSubmit() {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            String title = titleField.getText().trim();
            String courseNo = courseNoField.getText().trim();
            String courseName = courseNameField.getText().trim();
            String description = descriptionArea.getText().trim();
            LocalDate assignedDate = assignedDatePicker.getValue();
            LocalDate dueDate = dueDatePicker.getValue();
            String yearVal = yearCombo.getValue();
            String termVal = termCombo.getValue();
            String section = sectionCombo.getValue();

            if (title.isEmpty() || courseNo.isEmpty() || courseName.isEmpty() || description.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please fill all required fields");
                return;
            }

            if (assignedDate == null || dueDate == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please select dates");
                return;
            }

            if (yearVal == null || termVal == null || section == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please select all dropdown values");
                return;
            }

            conn = db.initialize();
            String sql = "INSERT INTO assignments (course_no, course_name, title, description, assigned_date, due_date, status, year, term, department, section, teacher_name) VALUES (?, ?, ?, ?, ?, ?, 'Active', ?, ?, 'CSE', ?, '')";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, courseNo);
            stmt.setString(2, courseName);
            stmt.setString(3, title);
            stmt.setString(4, description);
            stmt.setTimestamp(5, Timestamp.valueOf(assignedDate.atStartOfDay()));
            stmt.setTimestamp(6, Timestamp.valueOf(dueDate.atStartOfDay()));
            stmt.setInt(7, Integer.parseInt(yearVal));
            stmt.setInt(8, Integer.parseInt(termVal));
            stmt.setString(9, section);
            stmt.executeUpdate();

            showAlert(Alert.AlertType.INFORMATION, "Success", "Assignment added!");
            clearForm();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to add assignment: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void clearForm() {
        titleField.clear();
        courseNoField.clear();
        courseNameField.clear();
        descriptionArea.clear();
        assignedDatePicker.setValue(LocalDate.now());
        dueDatePicker.setValue(null);
        yearCombo.setValue(null);
        termCombo.setValue(null);
        sectionCombo.setValue(null);
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
