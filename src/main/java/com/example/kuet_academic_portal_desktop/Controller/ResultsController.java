package com.example.kuet_academic_portal_desktop.Controller;

import com.example.kuet_academic_portal_desktop.Session;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;

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
}