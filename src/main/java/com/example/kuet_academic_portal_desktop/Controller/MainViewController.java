package com.example.kuet_academic_portal_desktop.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainViewController {
    @FXML
    public Button goToLoginButton;

    @FXML
    public void handleGoToLogin(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/kuet_academic_portal_desktop/Login.fxml")));
        Stage s = (Stage) goToLoginButton.getScene().getWindow();
        s.setScene(new Scene(root));
        s.setTitle("Login");
        s.show();
    }
}
