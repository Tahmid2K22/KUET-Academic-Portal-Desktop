package com.example.kuet_academic_portal_desktop.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.*;

public class AddStudentController {
    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private TextField rollField;
    @FXML private ComboBox<String> departmentCombo;
    @FXML private ComboBox<String> yearCombo;
    @FXML private ComboBox<String> termCombo;
    @FXML private ComboBox<String> sectionCombo;
    @FXML private TextField cgpaField;
    @FXML private TextField passwordField;

    private final databaseConnect db = new databaseConnect();

    @FXML
    public void initialize() {
        departmentCombo.getItems().addAll("CSE", "EEE", "ME", "CE", "IPE");
        yearCombo.getItems().addAll("1", "2", "3", "4");
        termCombo.getItems().addAll("1", "2");
        sectionCombo.getItems().addAll("A", "B", "C");
    }

    @FXML
    public void handleSubmit() {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String rollText = rollField.getText().trim();
            String dept = departmentCombo.getValue();
            String yearVal = yearCombo.getValue();
            String termVal = termCombo.getValue();
            String section = sectionCombo.getValue();
            String cgpaText = cgpaField.getText().trim();
            String password = passwordField.getText().trim();

            if (name.isEmpty() || email.isEmpty() || rollText.isEmpty() || password.isEmpty() || cgpaText.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please fill all required fields");
                return;
            }

            if (dept == null || yearVal == null || termVal == null || section == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please select all dropdown values");
                return;
            }

            int roll = Integer.parseInt(rollText);
            int year = Integer.parseInt(yearVal);
            int term = Integer.parseInt(termVal);
            double cgpa = Double.parseDouble(cgpaText);

            conn = db.initialize();
            String sql = "INSERT INTO students (name, email, roll, department, year, term, section, CGPA, phone, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, '', ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setInt(3, roll);
            stmt.setString(4, dept);
            stmt.setInt(5, year);
            stmt.setInt(6, term);
            stmt.setString(7, section);
            stmt.setDouble(8, cgpa);
            stmt.setString(9, password);
            stmt.executeUpdate();

            showAlert(Alert.AlertType.INFORMATION, "Success", "Student added successfully!");
            clearForm();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid number format");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Database error: " + e.getMessage());
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to add student");
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
        nameField.clear();
        emailField.clear();
        rollField.clear();
        passwordField.clear();
        cgpaField.clear();
        departmentCombo.setValue(null);
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
