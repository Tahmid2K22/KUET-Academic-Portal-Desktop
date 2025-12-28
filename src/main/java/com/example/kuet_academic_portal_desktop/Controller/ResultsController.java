package com.example.kuet_academic_portal_desktop.Controller;

import com.example.kuet_academic_portal_desktop.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class ResultsController {

    @FXML
    private Label cgpaLabel;

    @FXML
    private Label termYearLabel;

    @FXML
    private Label emptyLabel;

    @FXML
    private LineChart<String, Number> ctMarksChart;

    @FXML
    public void initialize() {
        Session session = Session.getInstance();

        cgpaLabel.setText(String.format("%.2f", session.getCgpa()));

        String year = session.getYear();
        String term = session.getTerm();
        termYearLabel.setText("Year " + (year == null ? "-" : year) + ", Term " + (term == null ? "-" : term));
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