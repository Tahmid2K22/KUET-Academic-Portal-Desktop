package com.example.kuet_academic_portal_desktop.Controller;

import com.example.kuet_academic_portal_desktop.Session;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class dashboardController {
    
    @FXML
    public Label cyclesLabel;
    public Label tf_remaining;
    @FXML
    private Label name_label;


    public void initialize() {
        String user_name = Session.getInstance().getName();
        name_label.setText(user_name);
        System.out.println("Dashboard initialized for user: " + user_name);


        Task<Void> loadDataTask = new Task<>() {
            String data;
            JsonNode n;
            @Override
            protected Void call() throws JsonProcessingException {
                databaseConnect db = new databaseConnect();
                data = (String) (db.loadDashboardData());
                ObjectMapper mapper = new ObjectMapper();
                n = mapper.readTree(data);


                return null;
            }

            @Override
            protected void succeeded() {
                cyclesLabel.setText("Week: "+n.get("week").asText());
                tf_remaining.setText(n.get("tf_remain").asText());
            }

            @Override
            protected void failed() {
                System.err.println("Failed to load dashboard data: " + getException().getMessage());
                if (getException() != null) {
                    getException().printStackTrace();
                }
            }
        };


        Thread thread = new Thread(loadDataTask);
        thread.start();
    }
}