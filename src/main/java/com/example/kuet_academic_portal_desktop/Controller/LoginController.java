package com.example.kuet_academic_portal_desktop.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Objects;

public class LoginController {
    @FXML
    public Button Login_btn;
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
            alert.showAndWait();
            return;
        }

        databaseConnect db = new databaseConnect();
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            connection = db.initialize();
            if (connection != null) {
                System.out.println("Connected to database: " + connection);
            } else {
                System.out.println("Failed to connect to database.");
                return;
            }

            String query = "SELECT * FROM mysql.users WHERE email=? AND password=?";
            stmt = connection.prepareStatement(query);
            stmt.setString(1, emailIn);
            stmt.setString(2, passIn);
            rs = stmt.executeQuery();

            if(rs.next()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Login Successful");
                alert.setContentText("Welcome, " + rs.getString("email") + "!");
                alert.showAndWait();
                Parent l = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/kuet_academic_portal_desktop/Student_Dashboard.fxml")));
                Stage s = (Stage) Login_btn.getScene().getWindow();
                s.setTitle("Student Dashboard");
                s.setScene(new Scene(l));
                s.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Failed");
                alert.setContentText("Invalid email or password.");
                alert.showAndWait();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (connection != null) db.disconnect();
        }

    }


}
