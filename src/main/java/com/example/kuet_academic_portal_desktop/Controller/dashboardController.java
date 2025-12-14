package com.example.kuet_academic_portal_desktop.Controller;

import com.example.kuet_academic_portal_desktop.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class dashboardController {
    @FXML
    private Label name_label;


    public void initialize() {
        String user_name = Session.getInstance().getName();
        name_label.setText(user_name);
        System.out.println("Dashboard initialized for user: " + user_name);
    }
}