package com.example.kuet_academic_portal_desktop.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.*;
import java.time.LocalDate;

public class AddNoticeController {
    @FXML private TextField titleField;
    @FXML private TextArea descriptionArea;
    @FXML private DatePicker datePicker;
    @FXML private ComboBox<String> yearCombo;
    @FXML private ComboBox<String> termCombo;

    private final databaseConnect db = new databaseConnect();

    @FXML
    public void initialize() {
        yearCombo.getItems().addAll("All", "1", "2", "3", "4");
        termCombo.getItems().addAll("All", "1", "2");
        datePicker.setValue(LocalDate.now());
    }

    @FXML
    public void handleSubmit() {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            String title = titleField.getText().trim();
            String description = descriptionArea.getText().trim();
            LocalDate date = datePicker.getValue();
            String yearVal = yearCombo.getValue();
            String termVal = termCombo.getValue();

            if (title.isEmpty() || description.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please fill all required fields");
                return;
            }

            if (date == null || yearVal == null || termVal == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please select all dropdown values");
                return;
            }

            conn = db.initialize();
            String sql = "INSERT INTO notices (title, description, date, year, term) VALUES (?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, title);
            stmt.setString(2, description);
            stmt.setTimestamp(3, Timestamp.valueOf(date.atStartOfDay()));
            stmt.setObject(4, yearVal.equals("All") ? null : Integer.parseInt(yearVal));
            stmt.setObject(5, termVal.equals("All") ? null : Integer.parseInt(termVal));
            stmt.executeUpdate();

            showAlert(Alert.AlertType.INFORMATION, "Success", "Notice published!");
            clearForm();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to add notice: " + e.getMessage());
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
        descriptionArea.clear();
        datePicker.setValue(LocalDate.now());
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
