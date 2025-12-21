package com.example.kuet_academic_portal_desktop.Controller;

import com.example.kuet_academic_portal_desktop.Model.Notice;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class NoticesController {

    @FXML
    private VBox noticesVBox;

    @FXML
    private Label noNoticesLabel;

    public void initialize() {
        loadAllNotices();
    }

    private void loadAllNotices() {
        Task<List<Notice>> loadNoticesTask = new Task<>() {
            @Override
            protected List<Notice> call() throws Exception {
                // Load all notices from database
                databaseConnect db = new databaseConnect();
                List<Notice> notices = db.loadNoticeData();
                return notices;
            }

            @Override
            protected void succeeded() {
                List<Notice> notices = getValue();
                Platform.runLater(() -> displayAllNotices(notices));
            }

            @Override
            protected void failed() {
                System.err.println("Failed to load notices: " + getException().getMessage());
                if (getException() != null) {
                    getException().printStackTrace();
                }
                Platform.runLater(() -> {
                    noNoticesLabel.setText("Failed to load notices. Please try again later.");
                    noNoticesLabel.setVisible(true);
                });
            }
        };

        Thread noticeThread = new Thread(loadNoticesTask);
        noticeThread.setDaemon(true);
        noticeThread.start();
    }

    private void displayAllNotices(List<Notice> notices) {
        noticesVBox.getChildren().clear();

        if (notices == null || notices.isEmpty()) {
            noNoticesLabel.setText("No notices available at the moment.");
            noNoticesLabel.setVisible(true);
            noticesVBox.getChildren().add(noNoticesLabel);
            return;
        }


        noNoticesLabel.setVisible(false);


        for (Notice notice : notices) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/kuet_academic_portal_desktop/notice_card_full.fxml"));
                VBox noticeCard = loader.load();
                NoticeCardController controller = loader.getController();
                controller.setNoticeData(notice);
                noticeCard.setMaxWidth(Double.MAX_VALUE);
                noticesVBox.getChildren().add(noticeCard);
            } catch (IOException e) {
                System.err.println("Failed to load notice card: " + e.getMessage());
                e.printStackTrace();
            }
        }
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

