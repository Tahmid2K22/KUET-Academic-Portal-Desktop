package com.example.kuet_academic_portal_desktop.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.sql.*;

public class UpdateStudentController {
    @FXML private TextField searchRollField;
    @FXML private VBox updateFormContainer;
    @FXML private TextField nameField;
    @FXML private TextField rollField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private ComboBox<String> yearCombo;
    @FXML private ComboBox<String> termCombo;
    @FXML private ComboBox<String> sectionCombo;
    @FXML private ComboBox<String> deptCombo;

    private final databaseConnect db = new databaseConnect();
    private int studentId = -1;

    @FXML
    public void initialize() {
        yearCombo.getItems().addAll("1", "2", "3", "4");
        termCombo.getItems().addAll("1", "2");
        sectionCombo.getItems().addAll("A", "B", "C");
        deptCombo.getItems().addAll("CSE", "EEE", "ME", "CE", "IPE");
        if (updateFormContainer != null) {
            updateFormContainer.setVisible(false);
            updateFormContainer.setManaged(false);
        }
    }

    @FXML
    public void searchStudent() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String rollText = searchRollField.getText().trim();
            if (rollText.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please enter roll number");
                return;
            }

            int roll = Integer.parseInt(rollText);
            conn = db.initialize();
            stmt = conn.prepareStatement("SELECT * FROM students WHERE roll = ?");
            stmt.setInt(1, roll);
            rs = stmt.executeQuery();

            if (rs.next()) {
                studentId = rs.getInt("id");
                nameField.setText(rs.getString("name"));
                rollField.setText(String.valueOf(rs.getInt("roll")));
                emailField.setText(rs.getString("email"));
                phoneField.setText(rs.getString("phone") != null ? rs.getString("phone") : "");
                yearCombo.setValue(String.valueOf(rs.getInt("year")));
                termCombo.setValue(String.valueOf(rs.getInt("term")));
                sectionCombo.setValue(rs.getString("section"));
                deptCombo.setValue(rs.getString("department"));
                updateFormContainer.setVisible(true);
                updateFormContainer.setManaged(true);
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Student not found");
                updateFormContainer.setVisible(false);
                updateFormContainer.setManaged(false);
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid roll number");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Search failed: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void updateStudent() {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            if (studentId == -1) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please search for a student first");
                return;
            }

            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();
            String yearVal = yearCombo.getValue();
            String termVal = termCombo.getValue();
            String section = sectionCombo.getValue();
            String dept = deptCombo.getValue();

            if (name.isEmpty() || email.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Name and email are required");
                return;
            }

            if (yearVal == null || termVal == null || section == null || dept == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please select all dropdown values");
                return;
            }

            int year = Integer.parseInt(yearVal);
            int term = Integer.parseInt(termVal);

            conn = db.initialize();
            String sql = "UPDATE students SET name=?, email=?, phone=?, year=?, term=?, section=?, department=? WHERE id=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, phone);
            stmt.setInt(4, year);
            stmt.setInt(5, term);
            stmt.setString(6, section);
            stmt.setString(7, dept);
            stmt.setInt(8, studentId);
            stmt.executeUpdate();

            showAlert(Alert.AlertType.INFORMATION, "Success", "Student updated successfully!");
            clearForm();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid number format");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Update failed: " + e.getMessage());
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
        searchRollField.clear();
        nameField.clear();
        rollField.clear();
        emailField.clear();
        phoneField.clear();
        yearCombo.setValue(null);
        termCombo.setValue(null);
        sectionCombo.setValue(null);
        deptCombo.setValue(null);
        if (updateFormContainer != null) {
            updateFormContainer.setVisible(false);
            updateFormContainer.setManaged(false);
        }
        studentId = -1;
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
