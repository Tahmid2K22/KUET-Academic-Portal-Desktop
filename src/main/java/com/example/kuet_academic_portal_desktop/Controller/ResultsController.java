package com.example.kuet_academic_portal_desktop.Controller;

import com.example.kuet_academic_portal_desktop.Session;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultsController {

    @FXML
    private Label cgpaLabel;

    @FXML
    private Label termYearLabel;

    @FXML
    private Label totalResultsLabel;

    @FXML
    private Label averageScoreLabel;

    @FXML
    private Label emptyLabel;

    @FXML
    private Label emptyHistogramLabel;

    @FXML
    private BarChart<String, Number> resultsHistogram;

    @FXML
    private VBox resultsContainer;

    @FXML
    public void initialize() {
        Session session = Session.getInstance();

        cgpaLabel.setText(String.format("%.2f", session.getCgpa()));

        String year = session.getYear();
        String term = session.getTerm();
        String roll = session.getRoll();

        termYearLabel.setText("Year " + (year == null ? "-" : year) + ", Term " + (term == null ? "-" : term));

        // Load results in background
        loadResultsData(roll, year, term);
    }

    private void loadResultsData(String roll, String year, String term) {
        Task<Void> loadTask = new Task<>() {
            int totalResults = 0;
            double totalPercentage = 0.0;
            XYChart.Series<String, Number> series = new XYChart.Series<>();

            @Override
            protected Void call() throws Exception {
                Connection conn = databaseConnect.getConn();

                String query = "SELECT * FROM results WHERE roll = ? AND year = ? AND term = ? ORDER BY id";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, roll);
                stmt.setString(2, year);
                stmt.setString(3, term);

                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    String title = rs.getString("title");
                    double obtainedMarks = rs.getDouble("mark");
                    double totalMarks = rs.getDouble("total_mark");

                    double percentage = (obtainedMarks / totalMarks) * 100;
                    totalPercentage += percentage;
                    totalResults++;

                    // Add to histogram
                    String shortTitle = title.length() > 15 ? title.substring(0, 12) + "..." : title;
                    series.getData().add(new XYChart.Data<>(shortTitle, percentage));

                    // Create result card on JavaFX thread
                    final String fTitle = title;
                    final double fObtained = obtainedMarks;
                    final double fTotal = totalMarks;
                    final double fPercentage = percentage;

                    Platform.runLater(() -> {
                        try {
                            addResultCard(fTitle, fObtained, fTotal, fPercentage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }

                rs.close();
                stmt.close();

                return null;
            }

            @Override
            protected void succeeded() {
                if (totalResults > 0) {
                    totalResultsLabel.setText(String.valueOf(totalResults));
                    averageScoreLabel.setText(String.format("%.1f%%", totalPercentage / totalResults));

                    resultsHistogram.getData().add(series);
                    emptyHistogramLabel.setVisible(false);
                    emptyLabel.setVisible(false);
                } else {
                    totalResultsLabel.setText("0");
                    averageScoreLabel.setText("0%");
                    emptyHistogramLabel.setVisible(true);
                    emptyLabel.setVisible(true);
                }
            }

            @Override
            protected void failed() {
                System.err.println("Failed to load results: " + getException().getMessage());
                if (getException() != null) {
                    getException().printStackTrace();
                }
                totalResultsLabel.setText("0");
                averageScoreLabel.setText("0%");
                emptyHistogramLabel.setVisible(true);
                emptyLabel.setVisible(true);
            }
        };

        Thread thread = new Thread(loadTask);
        thread.setDaemon(true);
        thread.start();
    }

    private void addResultCard(String title, double obtainedMarks, double totalMarks, double percentage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/kuet_academic_portal_desktop/result_card.fxml"));
        VBox card = loader.load();

        // Get labels from the card
        Label courseTitleLabel = (Label) card.lookup("#courseTitleLabel");
        Label percentageLabel = (Label) card.lookup("#percentageLabel");
        Label obtainedMarksLabel = (Label) card.lookup("#obtainedMarksLabel");
        Label totalMarksLabel = (Label) card.lookup("#totalMarksLabel");
        Label gradeLabel = (Label) card.lookup("#gradeLabel");

        // Set values
        courseTitleLabel.setText(title);
        percentageLabel.setText(String.format("%.1f%%", percentage));
        obtainedMarksLabel.setText(String.format("%.1f", obtainedMarks));
        totalMarksLabel.setText(String.format("%.1f", totalMarks));

        // Calculate and set grade
        String grade = calculateGrade(percentage);
        gradeLabel.setText(grade);

        // Set color based on percentage
        String color = getColorForPercentage(percentage);
        percentageLabel.setStyle(percentageLabel.getStyle().replaceAll("#[0-9a-fA-F]{6}", color));
        gradeLabel.setStyle(gradeLabel.getStyle().replaceAll("#[0-9a-fA-F]{6}", color));

        resultsContainer.getChildren().add(card);
    }

    private String calculateGrade(double percentage) {
        if (percentage >= 90) return "A+";
        else if (percentage >= 85) return "A";
        else if (percentage >= 80) return "A-";
        else if (percentage >= 75) return "B+";
        else if (percentage >= 70) return "B";
        else if (percentage >= 65) return "B-";
        else if (percentage >= 60) return "C+";
        else if (percentage >= 55) return "C";
        else if (percentage >= 50) return "D";
        else return "F";
    }

    private String getColorForPercentage(double percentage) {
        if (percentage >= 80) return "#27ae60"; // Green
        else if (percentage >= 60) return "#3498db"; // Blue
        else if (percentage >= 50) return "#f39c12"; // Orange
        else return "#e74c3c"; // Red
    }

    @FXML
    private void goBackToDashboard(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/kuet_academic_portal_desktop/Student_Dashboard.fxml"));
            Parent dashboard = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(dashboard, 1200, 800);
            stage.setScene(scene);
            stage.setTitle("Student Dashboard - KUET Academic Portal");
            stage.show();
        } catch (IOException e) {
            System.err.println("Failed to load dashboard: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

