package com.example.kuet_academic_portal_desktop.Controller;

import com.example.kuet_academic_portal_desktop.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

public class AdminDashboardController {

    @FXML private Label adminNameLabel;
    @FXML private StackPane contentArea;
    @FXML private VBox homeView;
    @FXML private Button homeBtn;
    @FXML private Button addStudentBtn;
    @FXML private Button updateStudentBtn;
    @FXML private Button addAssignmentBtn;
    @FXML private Button addAttendanceBtn;
    @FXML private Button addNoticeBtn;
    @FXML private Button addResultBtn;
    @FXML private Button addRoutineBtn;
    @FXML private Button updateRoutineBtn;

    private final databaseConnect db = new databaseConnect();
    private Button activeButton = null;

    @FXML
    public void initialize() {
        Session session = Session.getInstance();
        if (session.getName() != null) {
            adminNameLabel.setText(session.getName());
        }
        showHome();
    }

    private void setActiveButton(Button button) {
        if (activeButton != null) {
            activeButton.getStyleClass().remove("sidebar-button-active");
        }
        if (button != null) {
            button.getStyleClass().add("sidebar-button-active");
            activeButton = button;
        }
    }

    @FXML
    public void showHome() {
        contentArea.getChildren().clear();
        contentArea.getChildren().add(homeView);
        setActiveButton(homeBtn);
    }

    @FXML
    public void showAddStudent() {
        loadView("/com/example/kuet_academic_portal_desktop/Admin_Add_Student.fxml");
        setActiveButton(addStudentBtn);
    }

    @FXML
    public void showUpdateStudent() {
        loadView("/com/example/kuet_academic_portal_desktop/Admin_Update_Student.fxml");
        setActiveButton(updateStudentBtn);
    }

    @FXML
    public void showAddAssignment() {
        loadView("/com/example/kuet_academic_portal_desktop/Admin_Add_Assignment.fxml");
        setActiveButton(addAssignmentBtn);
    }

    @FXML
    public void showAddAttendance() {
        loadView("/com/example/kuet_academic_portal_desktop/Admin_Add_Attendance.fxml");
        setActiveButton(addAttendanceBtn);
    }

    @FXML
    public void showAddNotice() {
        loadView("/com/example/kuet_academic_portal_desktop/Admin_Add_Notice.fxml");
        setActiveButton(addNoticeBtn);
    }

    @FXML
    public void showAddResult() {
        loadView("/com/example/kuet_academic_portal_desktop/Admin_Add_Result.fxml");
        setActiveButton(addResultBtn);
    }

    @FXML
    public void showAddRoutine() {
        loadView("/com/example/kuet_academic_portal_desktop/Admin_Add_Routine.fxml");
        setActiveButton(addRoutineBtn);
    }

    @FXML
    public void showUpdateRoutine() {
        loadView("/com/example/kuet_academic_portal_desktop/Admin_Update_Routine.fxml");
        setActiveButton(updateRoutineBtn);
    }

    private void loadView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            VBox view = loader.load();
            contentArea.getChildren().clear();
            contentArea.getChildren().add(view);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load view: " + e.getMessage());
        }
    }

    @FXML
    public void logout() {
        try {
            Session.getInstance().clearSession();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/kuet_academic_portal_desktop/Login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) adminNameLabel.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Login - KUET Academic Portal");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to logout");
        }
    }

    public void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public Connection getConnection() throws SQLException {
        return db.initialize();
    }
}
