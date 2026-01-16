package com.example.kuet_academic_portal_desktop.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.sql.*;

public class UpdateRoutineController {
    @FXML private TextField searchCourseField;
    @FXML private VBox updateFormContainer;
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
    private int routineId = -1;

    @FXML
    public void initialize() {
        dayCombo.getItems().addAll("Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday");
        yearCombo.getItems().addAll("1", "2", "3", "4");
        termCombo.getItems().addAll("1", "2");
        departmentCombo.getItems().addAll("CSE", "EEE", "ME", "CE", "IPE");
        sectionCombo.getItems().addAll("A", "B", "C");
        if (updateFormContainer != null) {
            updateFormContainer.setVisible(false);
            updateFormContainer.setManaged(false);
        }
    }

    @FXML
    public void searchRoutine() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String courseCode = searchCourseField.getText().trim();
            if (courseCode.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please enter course code to search");
                return;
            }

            conn = db.initialize();
            stmt = conn.prepareStatement("SELECT * FROM class_routine WHERE course_code = ?");
            stmt.setString(1, courseCode);
            System.out.println(courseCode);
            rs = stmt.executeQuery();

            if (rs.next()) {
                routineId = rs.getInt("id");
                dayCombo.setValue(rs.getString("day"));
                timeField.setText(rs.getString("time_slot"));
                courseNoField.setText(rs.getString("course_code"));
                courseNameField.setText(rs.getString("course_name"));
                teacherField.setText(rs.getString("teacher"));
                roomField.setText(rs.getString("room"));
                yearCombo.setValue(String.valueOf(rs.getInt("year")));
                termCombo.setValue(String.valueOf(rs.getInt("term")));
                departmentCombo.setValue(rs.getString("department"));
                sectionCombo.setValue(rs.getString("section"));
                updateFormContainer.setVisible(true);
                updateFormContainer.setManaged(true);
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Routine not found for course: " + courseCode);
                updateFormContainer.setVisible(false);
                updateFormContainer.setManaged(false);
            }
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
    public void updateRoutine() {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            if (routineId == -1) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please search for a routine first");
                return;
            }

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
            String sql = "UPDATE class_routine SET day=?, time_slot=?, course_code=?, course_name=?, teacher=?, room=?, year=?, term=?, section=?, department=? WHERE id=?";
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
            stmt.setInt(11, routineId);
            stmt.executeUpdate();

            showAlert(Alert.AlertType.INFORMATION, "Success", "Routine updated successfully!");
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
    public void deleteRoutine() {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            if (routineId == -1) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please search for a routine first");
                return;
            }

            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm Delete");
            confirmAlert.setHeaderText(null);
            confirmAlert.setContentText("Are you sure you want to delete this routine?");

            if (confirmAlert.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
                return;
            }

            conn = db.initialize();
            String sql = "DELETE FROM class_routine WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, routineId);
            stmt.executeUpdate();

            showAlert(Alert.AlertType.INFORMATION, "Success", "Routine deleted successfully!");
            clearForm();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Delete failed: " + e.getMessage());
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
        searchCourseField.clear();
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
        if (updateFormContainer != null) {
            updateFormContainer.setVisible(false);
            updateFormContainer.setManaged(false);
        }
        routineId = -1;
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
