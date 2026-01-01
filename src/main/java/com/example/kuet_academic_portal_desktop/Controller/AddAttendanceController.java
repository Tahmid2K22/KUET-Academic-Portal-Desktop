package com.example.kuet_academic_portal_desktop.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.*;
import java.time.LocalDate;

public class AddAttendanceController {
    @FXML private TextField courseNoField;
    @FXML private TextField courseNameField;
    @FXML private TextField rollField;
    @FXML private DatePicker datePicker;
    @FXML private ComboBox<String> statusCombo;
    @FXML private ComboBox<String> yearCombo;
    @FXML private ComboBox<String> termCombo;
    @FXML private ComboBox<String> departmentCombo;
    @FXML private ComboBox<String> sectionCombo;

    private final databaseConnect db = new databaseConnect();

    @FXML
    public void initialize() {
        statusCombo.getItems().addAll("Present", "Absent");
        yearCombo.getItems().addAll("1", "2", "3", "4");
        termCombo.getItems().addAll("1", "2");
        departmentCombo.getItems().addAll("CSE", "EEE", "ME", "CE", "IPE");
        sectionCombo.getItems().addAll("A", "B", "C");
        datePicker.setValue(LocalDate.now());
    }

    @FXML
    public void handleSubmit() {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            String courseNo = courseNoField.getText().trim();
            String courseName = courseNameField.getText().trim();
            String roll = rollField.getText().trim();
            LocalDate date = datePicker.getValue();
            String status = statusCombo.getValue();
            String yearVal = yearCombo.getValue();
            String termVal = termCombo.getValue();
            String dept = departmentCombo.getValue();
            String section = sectionCombo.getValue();

            if (courseNo.isEmpty() || courseName.isEmpty() || roll.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please fill all required fields");
                return;
            }

            if (date == null || status == null || yearVal == null || termVal == null || dept == null || section == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please select all dropdown values");
                return;
            }

            conn = db.initialize();
            String sql = "INSERT INTO attendance (course_no, course_name, date, status, year, term, department, section, student_roll) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, courseNo);
            stmt.setString(2, courseName);
            stmt.setDate(3, Date.valueOf(date));
            stmt.setString(4, status);
            stmt.setInt(5, Integer.parseInt(yearVal));
            stmt.setInt(6, Integer.parseInt(termVal));
            stmt.setString(7, dept);
            stmt.setString(8, section);
            stmt.setString(9, roll);
            stmt.executeUpdate();

            showAlert(Alert.AlertType.INFORMATION, "Success", "Attendance added!");
            clearForm();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to add attendance: " + e.getMessage());
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
        courseNoField.clear();
        courseNameField.clear();
        rollField.clear();
        datePicker.setValue(LocalDate.now());
        statusCombo.setValue(null);
        yearCombo.setValue(null);
        termCombo.setValue(null);
        departmentCombo.setValue(null);
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
