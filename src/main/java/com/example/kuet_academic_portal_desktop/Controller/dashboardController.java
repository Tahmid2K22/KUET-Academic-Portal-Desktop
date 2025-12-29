package com.example.kuet_academic_portal_desktop.Controller;

import com.example.kuet_academic_portal_desktop.Model.Notice;
import com.example.kuet_academic_portal_desktop.Session;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class dashboardController {
    
    @FXML
    public Label cyclesLabel;
    @FXML
    public Label tf_remaining;
    @FXML
    private Label name_label;
    @FXML
    private HBox noticeContainer;


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
        loadNotices();
    }

    private void loadNotices() {
        Task<List<Notice>> loadNoticesTask = new Task<>() {
            @Override
            protected List<Notice> call() throws Exception {
                databaseConnect db = new databaseConnect();
                return db.loadNoticeData();
            }

            @Override
            protected void succeeded() {
                List<Notice> notices = getValue();
                Platform.runLater(() -> displayNotices(notices));
            }

            @Override
            protected void failed() {
                System.err.println("Failed to load notices: " + getException().getMessage());
                if (getException() != null) {
                    getException().printStackTrace();
                }
            }
        };

        Thread noticeThread = new Thread(loadNoticesTask);
        noticeThread.setDaemon(true);
        noticeThread.start();
    }

    private void displayNotices(List<Notice> notices) {
        noticeContainer.getChildren().clear();

        int displayCount = Math.min(notices.size(), 4);
        for (int i = 0; i < displayCount; i++) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/kuet_academic_portal_desktop/notice_card.fxml"));
                VBox noticeCard = loader.load();
                NoticeCardController controller = loader.getController();
                controller.setNoticeData(notices.get(i));
                noticeContainer.getChildren().add(noticeCard);
            } catch (IOException e) {
                System.err.println("Failed to load notice card: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void openNoticesPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/kuet_academic_portal_desktop/Notices.fxml"));
            Parent noticesPage = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(noticesPage, 1200, 800);
            stage.setScene(scene);
            stage.setTitle("Notice Board - KUET Academic Portal");
            stage.show();
        } catch (IOException e) {
            System.err.println("Failed to load notices page: " + e.getMessage());
            e.printStackTrace();
        }

    }

    @FXML
    private void openResultsPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/kuet_academic_portal_desktop/results.fxml"));
            Parent resultsPage = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(resultsPage);
            stage.setScene(scene);
            stage.setTitle("Results - KUET Academic Portal");
            stage.show();
        } catch (IOException e) {
            System.err.println("Failed to load results page: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void openClassRoutinePage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/kuet_academic_portal_desktop/Class_Routine.fxml"));
            Parent classRoutinePage = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(classRoutinePage);
            stage.setScene(scene);
            stage.setTitle("Class Routine - KUET Academic Portal");
            stage.show();
        } catch (IOException e) {
            System.err.println("Failed to load class routine page: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void openAssignmentPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/kuet_academic_portal_desktop/Assignments.fxml"));
            Parent assignmentPage = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(assignmentPage);
            stage.setScene(scene);
            stage.setTitle("Assignments - KUET Academic Portal");
            stage.show();
        } catch (IOException e) {
            System.err.println("Failed to load assignments page: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void openContactsPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/kuet_academic_portal_desktop/Contacts.fxml"));
            Parent contactsPage = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(contactsPage);
            stage.setScene(scene);
            stage.setTitle("Contacts - KUET Academic Portal");
            stage.show();
        } catch (IOException e) {
            System.err.println("Failed to load contacts page: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void openAttendancePage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/kuet_academic_portal_desktop/Attendance.fxml"));
            Parent attendancePage = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(attendancePage);
            stage.setScene(scene);
            stage.setTitle("Attendance - KUET Academic Portal");
            stage.show();
        } catch (IOException e) {
            System.err.println("Failed to load attendance page: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
