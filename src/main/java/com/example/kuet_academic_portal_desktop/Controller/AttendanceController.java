package com.example.kuet_academic_portal_desktop.Controller;

import com.example.kuet_academic_portal_desktop.Model.Attendance;
import com.example.kuet_academic_portal_desktop.Session;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class AttendanceController {

    @FXML
    private VBox attendanceVBox;

    @FXML
    private Label noAttendanceLabel;

    @FXML
    private Label overallAttendanceLabel;

    public void initialize() {
        System.out.println("Attendance page opened for student: Roll=" + Session.getInstance().getRoll());
        loadAttendanceData();
    }

    private void loadAttendanceData() {
        Task<List<Attendance>> loadAttendanceTask = new Task<>() {
            @Override
            protected List<Attendance> call() throws Exception {
                databaseConnect db = new databaseConnect();

                // Get student information from session
                String studentRoll = String.valueOf(Session.getInstance().getRoll());
                int year = Integer.parseInt(Session.getInstance().getYear());
                int term = Integer.parseInt(Session.getInstance().getTerm());
                String department = Session.getInstance().getDepartment();
                String section = Session.getInstance().getSection();

                return db.loadAttendanceSummary(studentRoll, year, term, department, section);
            }

            @Override
            protected void succeeded() {
                List<Attendance> attendanceList = getValue();
                System.out.println("Attendance page loaded " + (attendanceList == null ? 0 : attendanceList.size()) + " courses");
                Platform.runLater(() -> displayAttendance(attendanceList));
            }

            @Override
            protected void failed() {
                System.err.println("Failed to load attendance: " + getException().getMessage());
                if (getException() != null) {
                    getException().printStackTrace();
                }
                Platform.runLater(() -> {
                    noAttendanceLabel.setText("Failed to load attendance data. Please try again later.");
                    noAttendanceLabel.setVisible(true);
                });
            }
        };

        Thread attendanceThread = new Thread(loadAttendanceTask);
        attendanceThread.setDaemon(true);
        attendanceThread.start();
    }

    private void displayAttendance(List<Attendance> attendanceList) {
        attendanceVBox.getChildren().clear();

        if (attendanceList == null || attendanceList.isEmpty()) {
            noAttendanceLabel.setText("No attendance records available.");
            noAttendanceLabel.setVisible(true);
            attendanceVBox.getChildren().add(noAttendanceLabel);
            return;
        }

        noAttendanceLabel.setVisible(false);

        // Calculate overall attendance
        int totalClasses = 0;
        int totalAttended = 0;
        for (Attendance attendance : attendanceList) {
            totalClasses += attendance.getTotalClasses();
            totalAttended += attendance.getAttendedClasses();
        }
        double overallPercentage = totalClasses > 0 ? (totalAttended * 100.0 / totalClasses) : 0.0;

        // Display overall attendance
        if (overallAttendanceLabel != null) {
            overallAttendanceLabel.setText(String.format("Overall Attendance: %.2f%% (%d/%d classes)",
                                                        overallPercentage, totalAttended, totalClasses));
            overallAttendanceLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: " +
                                          (overallPercentage >= 75 ? "#27AE60" : "#E74C3C") + ";");
        }

        // Display each course attendance
        for (Attendance attendance : attendanceList) {
            VBox courseCard = createAttendanceCard(attendance);
            attendanceVBox.getChildren().add(courseCard);
        }
    }

    private VBox createAttendanceCard(Attendance attendance) {
        VBox card = new VBox(15);
        card.setStyle("-fx-background-color: white; -fx-padding: 25; -fx-background-radius: 10; " +
                     "-fx-border-color: #E0E0E0; -fx-border-radius: 10; -fx-border-width: 1; " +
                     "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);");
        card.setPadding(new Insets(25));
        card.setMaxWidth(1100);

        // Course header
        HBox headerBox = new HBox(15);
        headerBox.setAlignment(Pos.CENTER_LEFT);

        VBox courseInfo = new VBox(5);
        Label courseNoLabel = new Label(attendance.getCourseNo());
        courseNoLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2C3E50;");

        Label courseNameLabel = new Label(attendance.getCourseName());
        courseNameLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #7F8C8D;");

        courseInfo.getChildren().addAll(courseNoLabel, courseNameLabel);

        // Attendance percentage badge
        double percentage = attendance.getAttendancePercentage();
        Label percentageLabel = new Label(String.format("%.1f%%", percentage));
        percentageLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: " +
                                (percentage >= 75 ? "#27AE60" : "#E74C3C") + ";");

        headerBox.getChildren().addAll(courseInfo, percentageLabel);
        HBox.setHgrow(courseInfo, javafx.scene.layout.Priority.ALWAYS);

        // Progress bar
        ProgressBar progressBar = new ProgressBar(percentage / 100.0);
        progressBar.setPrefWidth(1050);
        progressBar.setPrefHeight(15);
        progressBar.setStyle("-fx-accent: " + (percentage >= 75 ? "#27AE60" : "#E74C3C") + ";");

        // Stats box
        HBox statsBox = new HBox(30);
        statsBox.setAlignment(Pos.CENTER_LEFT);
        statsBox.setStyle("-fx-background-color: #F8F9FA; -fx-background-radius: 8; -fx-padding: 15;");

        VBox totalBox = new VBox(5);
        totalBox.setAlignment(Pos.CENTER);
        Label totalLabel = new Label("Total Classes");
        totalLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #7F8C8D;");
        Label totalValue = new Label(String.valueOf(attendance.getTotalClasses()));
        totalValue.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2C3E50;");
        totalBox.getChildren().addAll(totalLabel, totalValue);

        VBox attendedBox = new VBox(5);
        attendedBox.setAlignment(Pos.CENTER);
        Label attendedLabel = new Label("Classes Attended");
        attendedLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #7F8C8D;");
        Label attendedValue = new Label(String.valueOf(attendance.getAttendedClasses()));
        attendedValue.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #27AE60;");
        attendedBox.getChildren().addAll(attendedLabel, attendedValue);

        VBox missedBox = new VBox(5);
        missedBox.setAlignment(Pos.CENTER);
        Label missedLabel = new Label("Classes Missed");
        missedLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #7F8C8D;");
        int missed = attendance.getTotalClasses() - attendance.getAttendedClasses();
        Label missedValue = new Label(String.valueOf(missed));
        missedValue.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #E74C3C;");
        missedBox.getChildren().addAll(missedLabel, missedValue);

        // Warning message if below 75%
        if (percentage < 75) {
            VBox warningBox = new VBox(5);
            warningBox.setAlignment(Pos.CENTER);
            Label warningLabel = new Label("⚠ Warning");
            warningLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #E74C3C; -fx-font-weight: bold;");
            Label warningText = new Label("Below 75%");
            warningText.setStyle("-fx-font-size: 11px; -fx-text-fill: #E74C3C;");
            warningBox.getChildren().addAll(warningLabel, warningText);
            statsBox.getChildren().addAll(totalBox, attendedBox, missedBox, warningBox);
        } else {
            statsBox.getChildren().addAll(totalBox, attendedBox, missedBox);
        }

        card.getChildren().addAll(headerBox, progressBar, statsBox);
        return card;
    }

    @FXML
    private void goBackToDashboard(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/kuet_academic_portal_desktop/Student_Dashboard.fxml"));
            Parent dashboard = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(dashboard);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Failed to load dashboard: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

