package com.example.kuet_academic_portal_desktop.Controller;

import com.example.kuet_academic_portal_desktop.Model.Assignment;
import com.example.kuet_academic_portal_desktop.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

        System.out.println("Loading assignments for: Dept=" + department + ", Year=" + year +
                         ", Term=" + term + ", Section=" + section);

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
                System.out.println("Loaded " + filteredAssignments.size() + " assignments (filtered out overdue)");
                assignmentsList = FXCollections.observableArrayList(filteredAssignments);
                displayAssignments();
            } else {
                System.out.println("No active assignments found (all are overdue)");
                noAssignmentsLabel.setVisible(true);
                assignmentsContainer.setVisible(false);
            }
        } else {
            System.out.println("No assignments found");
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
            Label statusLabel = (Label) card.lookup("#statusLabel");
            Button submitBtn = (Button) card.lookup("#submitBtn");
            Label submissionLabel = (Label) card.lookup("#submissionLabel");

            courseLabel.setText(assignment.getCourseNO() + " - " + assignment.getCourseName());
            titleLabel.setText(assignment.getTitle());
            descLabel.setText(assignment.getDescription());
            teacherLabel.setText("Teacher: " + assignment.getTeacherName());
            assignedLabel.setText("Assigned: " + assignment.getFormattedAssignedDate());
            dueLabel.setText("Due: " + assignment.getFormattedDueDate());
            statusLabel.setText("Status: " + assignment.getStatus());

            if (assignment.isOverdue()) {
                dueLabel.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #e74c3c;");
            } else {
                dueLabel.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #27ae60;");
            }

            // Check if already submitted
            boolean isSubmitted = checkSubmissionStatus(assignment.getId());

            if (isSubmitted || "Submitted".equalsIgnoreCase(assignment.getStatus())) {
                statusLabel.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-padding: 4 10 4 10; -fx-background-radius: 12; -fx-text-fill: white; -fx-background-color: #27ae60;");
                statusLabel.setText("Status: Submitted");
                submitBtn.setVisible(false);
                submitBtn.setManaged(false);
                submissionLabel.setText("✓ Already submitted");
                submissionLabel.setVisible(true);
            } else if (assignment.isOverdue()) {
                statusLabel.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-padding: 4 10 4 10; -fx-background-radius: 12; -fx-text-fill: white; -fx-background-color: #e74c3c;");
                submitBtn.setDisable(true);
                submitBtn.setText("Overdue");
            } else {
                statusLabel.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-padding: 4 10 4 10; -fx-background-radius: 12; -fx-text-fill: white; -fx-background-color: #f39c12;");
                submitBtn.setOnAction(e -> handleSubmitAssignment(assignment, submitBtn, statusLabel, submissionLabel));
            }

            card.setOnMouseEntered(e -> card.setStyle("-fx-background-color: white; -fx-border-color: #3498db; -fx-border-width: 1; -fx-border-radius: 8; -fx-background-radius: 8; -fx-effect: dropshadow(three-pass-box, rgba(52,152,219,0.2), 8, 0, 0, 3);"));
            card.setOnMouseExited(e -> card.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-width: 1; -fx-border-radius: 8; -fx-background-radius: 8; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.08), 5, 0, 0, 2);"));

            return card;
        } catch (IOException e) {
            System.err.println("Error loading assignment card FXML: " + e.getMessage());
            e.printStackTrace();
            return new VBox(new Label("Error loading assignment"));
        }
    }

    private boolean checkSubmissionStatus(int assignmentId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            Session session = Session.getInstance();
            String studentRoll = session.getRoll();

            conn = databaseConnect.getConn();
            String query = "SELECT COUNT(*) FROM pdf_files WHERE assignment_id = ? AND student_roll = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, assignmentId);
            stmt.setString(2, studentRoll);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error checking submission status: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private void handleSubmitAssignment(Assignment assignment, Button submitBtn, Label statusLabel, Label submissionLabel) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select PDF File to Submit");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("PDF Files", "*.pdf")
        );

        Stage stage = (Stage) submitBtn.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            if (uploadAssignment(assignment.getId(), selectedFile)) {
                submitBtn.setVisible(false);
                submitBtn.setManaged(false);
                statusLabel.setText("Status: Submitted");
                statusLabel.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-padding: 4 10 4 10; -fx-background-radius: 12; -fx-text-fill: white; -fx-background-color: #27ae60;");
                submissionLabel.setText("✓ Submitted successfully");
                submissionLabel.setVisible(true);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Assignment submitted successfully!");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to submit assignment. Please try again.");
            }
        }
    }

    private boolean uploadAssignment(int assignmentId, File file) {
        Connection conn = null;
        PreparedStatement stmt = null;
        FileInputStream fis = null;

        try {
            Session session = Session.getInstance();
            String studentRoll = session.getRoll();
            String studentName = session.getName();

            conn = databaseConnect.getConn();
            String query = "INSERT INTO pdf_files (assignment_id, student_roll, student_name, file_name, file_data) VALUES (?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, assignmentId);
            stmt.setString(2, studentRoll);
            stmt.setString(3, studentName);
            stmt.setString(4, file.getName());

            fis = new FileInputStream(file);
            stmt.setBinaryStream(5, fis, (int) file.length());

            int result = stmt.executeUpdate();
            return result > 0;

        } catch (SQLException | IOException e) {
            System.err.println("Error uploading assignment: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (fis != null) fis.close();
                if (stmt != null) stmt.close();
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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

