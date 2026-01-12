package com.example.kuet_academic_portal_desktop.Controller;

import com.example.kuet_academic_portal_desktop.Model.Assignment;
import com.example.kuet_academic_portal_desktop.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AssignmentsController {

    @FXML
    private Button goDash;

    @FXML
    private Label semesterLabel;

    @FXML
    private ScrollPane assignmentsScrollPane;

    @FXML
    private VBox assignmentsContainer;

    @FXML
    private Label noAssignmentsLabel;

    private databaseConnect dbConnect;
    private ObservableList<Assignment> assignmentsList;

    @FXML
    public void initialize() {
        dbConnect = new databaseConnect();
        setupSemesterLabel();
        loadAssignments();
    }

    private void setupSemesterLabel() {
        Session session = Session.getInstance();
        String semesterInfo = session.getYear() + "-" + session.getTerm() + " " + session.getDepartment();
        if (session.getSection() != null && !session.getSection().isEmpty()) {
            semesterInfo += " Section " + session.getSection();
        }
        semesterLabel.setText(semesterInfo);
    }

    private void loadAssignments() {
        Session session = Session.getInstance();
        String department = session.getDepartment();
        String year = session.getYear();
        String section = session.getSection();
        String term = session.getTerm();

        List<Assignment> assignments = dbConnect.loadAssignmentData(department,
                                                                    Integer.parseInt(year),
                                                                    Integer.parseInt(term),
                                                                    section);

        if (assignments != null && !assignments.isEmpty()) {
            List<Assignment> filteredAssignments = new ArrayList<>();
            for (Assignment assignment : assignments) {
                if (!assignment.isOverdue()) {
                    filteredAssignments.add(assignment);
                }
            }

            if (!filteredAssignments.isEmpty()) {
                assignmentsList = FXCollections.observableArrayList(filteredAssignments);
                displayAssignments();
            } else {
                noAssignmentsLabel.setVisible(true);
                assignmentsContainer.setVisible(false);
            }
        } else {
            noAssignmentsLabel.setVisible(true);
            assignmentsContainer.setVisible(false);
        }
    }

    private void displayAssignments() {
        assignmentsContainer.getChildren().clear();
        noAssignmentsLabel.setVisible(false);
        assignmentsContainer.setVisible(true);

        for (Assignment assignment : assignmentsList) {
            VBox assignmentCard = createAssignmentCard(assignment);
            assignmentsContainer.getChildren().add(assignmentCard);
        }
    }

    private VBox createAssignmentCard(Assignment assignment) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/kuet_academic_portal_desktop/assignment_card.fxml"));
            VBox card = loader.load();

            Label courseLabel = (Label) card.lookup("#courseLabel");
            Label titleLabel = (Label) card.lookup("#titleLabel");
            Label descLabel = (Label) card.lookup("#descriptionLabel");
            Label teacherLabel = (Label) card.lookup("#teacherLabel");
            Label assignedLabel = (Label) card.lookup("#assignedLabel");
            Label dueLabel = (Label) card.lookup("#dueLabel");

            courseLabel.setText(assignment.getCourseNO() + " - " + assignment.getCourseName());
            titleLabel.setText(assignment.getTitle());
            descLabel.setText(assignment.getDescription());
            teacherLabel.setText("Teacher: " + assignment.getTeacherName());
            assignedLabel.setText("Assigned: " + assignment.getFormattedAssignedDate());
            dueLabel.setText("Due: " + assignment.getFormattedDueDate());

            if (assignment.isOverdue()) {
                dueLabel.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #e74c3c;");
            } else {
                dueLabel.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #27ae60;");
            }

            card.setOnMouseEntered(e -> card.setStyle("-fx-background-color: white; -fx-border-color: #3498db; -fx-border-width: 1; -fx-border-radius: 8; -fx-background-radius: 8; -fx-effect: dropshadow(three-pass-box, rgba(52,152,219,0.2), 8, 0, 0, 3);"));
            card.setOnMouseExited(e -> card.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-width: 1; -fx-border-radius: 8; -fx-background-radius: 8; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.08), 5, 0, 0, 2);"));

            return card;
        } catch (IOException e) {
            e.printStackTrace();
            return new VBox(new Label("Error loading assignment"));
        }
    }


    @FXML
    void goDashFun() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/kuet_academic_portal_desktop/Student_Dashboard.fxml"));
        Parent root = loader.load();
        Scene s = new Scene(root);
        Stage stage = (Stage) goDash.getScene().getWindow();
        stage.setScene(s);
        stage.setTitle("Student Dashboard - KUET Academic Portal");
        stage.show();
    }

    @SuppressWarnings("unused")
    public void refreshAssignments() {
        loadAssignments();
    }
}

