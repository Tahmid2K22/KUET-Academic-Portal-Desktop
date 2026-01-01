package com.example.kuet_academic_portal_desktop.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.*;

public class AddRoutineController {
    @FXML private ComboBox<String> dayCombo;
    @FXML private TextField timeField;
    @FXML private TextField courseNoField;
    @FXML private TextField courseNameField;
    @FXML private TextField teacherField;
    @FXML private TextField roomField;
    @FXML private ComboBox<String> yearCombo;
    @FXML private ComboBox<String> termCombo;
    @FXML private ComboBox<String> departmentCombo;
    @FXML private ComboBox<String> sectionCombo;

    private final databaseConnect db = new databaseConnect();

    @FXML
    public void initialize() {
        dayCombo.getItems().addAll("Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday");
        yearCombo.getItems().addAll("1", "2", "3", "4");
        termCombo.getItems().addAll("1", "2");
        departmentCombo.getItems().addAll("CSE", "EEE", "ME", "CE", "IPE");
        sectionCombo.getItems().addAll("A", "B", "C");
    }

    @FXML
    public void handleSubmit() {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            String day = dayCombo.getValue();
            String time = timeField.getText().trim();
            String courseNo = courseNoField.getText().trim();
            String courseName = courseNameField.getText().trim();
            String teacher = teacherField.getText().trim();
            String room = roomField.getText().trim();
            String yearVal = yearCombo.getValue();
            String termVal = termCombo.getValue();
            String dept = departmentCombo.getValue();
            String section = sectionCombo.getValue();

            if (time.isEmpty() || courseNo.isEmpty() || courseName.isEmpty() || teacher.isEmpty() || room.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please fill all required fields");
                return;
            }

            if (day == null || yearVal == null || termVal == null || dept == null || section == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please select all dropdown values");
                return;
            }

            conn = db.initialize();
            String sql = "INSERT INTO class_routine (day, time_slot, course_code, course_name, teacher, room, type, year, term, section, department) VALUES (?, ?, ?, ?, ?, ?, 'Lecture', ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, day);
            stmt.setString(2, time);
            stmt.setString(3, courseNo);
            stmt.setString(4, courseName);
            stmt.setString(5, teacher);
            stmt.setString(6, room);
            stmt.setInt(7, Integer.parseInt(yearVal));
            stmt.setInt(8, Integer.parseInt(termVal));
            stmt.setString(9, section);
            stmt.setString(10, dept);
            stmt.executeUpdate();

            showAlert(Alert.AlertType.INFORMATION, "Success", "Routine added!");
            clearForm();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to add routine: " + e.getMessage());
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
        dayCombo.setValue(null);
        timeField.clear();
        courseNoField.clear();
        courseNameField.clear();
        teacherField.clear();
        roomField.clear();
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
