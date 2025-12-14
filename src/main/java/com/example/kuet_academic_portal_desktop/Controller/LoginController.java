package com.example.kuet_academic_portal_desktop.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class LoginController {
    @FXML
    private TextField email;
    @FXML
    private PasswordField pass;
    @FXML
    public void login() throws SQLException {
        String emailIn = email.getText();
        String passIn = pass.getText();

        if (emailIn.isEmpty() || passIn.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setContentText("Please enter all the fields");
            return;
        }

        databaseConnect db = new databaseConnect();
        Connection connection = db.connect();
        if (connection != null) {
            System.out.println("Connected to database: " + connection);
        } else {
            System.out.println("Failed to connect to database.");
            return;
        }

        String query ="SELECT * FROM students WHERE email=? AND password=?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1,emailIn);
        stmt.setString(2,passIn);
        ResultSet rs =stmt.executeQuery();
        if(rs.next()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login Successful");
            alert.setContentText("Welcome, " + rs.getString("name") + "!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setContentText("Invalid email or password.");
            alert.showAndWait();
        }


    }


}
