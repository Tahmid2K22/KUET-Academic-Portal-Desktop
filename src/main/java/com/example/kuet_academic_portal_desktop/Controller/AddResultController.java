package com.example.kuet_academic_portal_desktop.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.*;

public class AddResultController {
    @FXML private TextField rollField;
    @FXML private TextField courseNameField;
    @FXML private TextField ctMarksField;
    @FXML private TextField attendanceMarksField;
    @FXML private ComboBox<String> yearCombo;
    @FXML private ComboBox<String> termCombo;

    private final databaseConnect db = new databaseConnect();

    @FXML
    public void initialize() {
        yearCombo.getItems().addAll("1", "2", "3", "4");
        termCombo.getItems().addAll("1", "2");
    }

    @FXML
    public void handleSubmit() {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            String rollText = rollField.getText().trim();
            String courseName = courseNameField.getText().trim();
            String ctMarksText = ctMarksField.getText().trim();
            String totalMarksText = attendanceMarksField.getText().trim();
            String yearVal = yearCombo.getValue();
            String termVal = termCombo.getValue();

            if (rollText.isEmpty() || courseName.isEmpty() || ctMarksText.isEmpty() || totalMarksText.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please fill all required fields");
                return;
            }

            if (yearVal == null || termVal == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please select all dropdown values");
                return;
            }

            int roll = Integer.parseInt(rollText);
            double ctMarks = Double.parseDouble(ctMarksText);
            double totalMarks = Double.parseDouble(totalMarksText);
            int year = Integer.parseInt(yearVal);
            int term = Integer.parseInt(termVal);

            conn = db.initialize();
            String sql = "INSERT INTO results (roll, title, mark, total_mark, term, year) VALUES (?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, roll);
            stmt.setString(2, courseName);
            stmt.setDouble(3, ctMarks);
            stmt.setDouble(4, totalMarks);
            stmt.setInt(5, term);
            stmt.setInt(6, year);
            stmt.executeUpdate();

            showAlert(Alert.AlertType.INFORMATION, "Success", "Result added!");
            clearForm();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid number format");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to add result: " + e.getMessage());
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
        rollField.clear();
        courseNameField.clear();
        ctMarksField.clear();
        attendanceMarksField.clear();
        yearCombo.setValue(null);
        termCombo.setValue(null);
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
