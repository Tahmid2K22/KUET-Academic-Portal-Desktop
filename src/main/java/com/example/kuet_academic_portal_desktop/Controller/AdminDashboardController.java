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

    private databaseConnect db = new databaseConnect();
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
            if (!button.getStyleClass().contains("sidebar-button-active")) {
                button.getStyleClass().add("sidebar-button-active");
            }
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

    private void loadView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            VBox view = loader.load();
            contentArea.getChildren().clear();
            contentArea.getChildren().add(view);

            Object controller = loader.getController();
            if (controller instanceof AddStudentController) {
                ((AddStudentController) controller).setParentController(this);
            } else if (controller instanceof UpdateStudentController) {
                ((UpdateStudentController) controller).setParentController(this);
            } else if (controller instanceof AddAssignmentController) {
                ((AddAssignmentController) controller).setParentController(this);
            } else if (controller instanceof AddAttendanceController) {
                ((AddAttendanceController) controller).setParentController(this);
            } else if (controller instanceof AddNoticeController) {
                ((AddNoticeController) controller).setParentController(this);
            } else if (controller instanceof AddResultController) {
                ((AddResultController) controller).setParentController(this);
            } else if (controller instanceof AddRoutineController) {
                ((AddRoutineController) controller).setParentController(this);
            }

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load view: " + e.getMessage());
        }
    }

    @FXML
    public void logout() {
        try {
            Session.getInstance().setEmail(null);
            Session.getInstance().setRole(null);
            Session.getInstance().setName(null);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/kuet_academic_portal_desktop/Login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) adminNameLabel.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Login - KUET Academic Portal");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to logout: " + e.getMessage());
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

    public void closeConnection() throws SQLException {
        db.disconnect();
    }

    

    public static class AddStudentController {
        @FXML private TextField nameField;
        @FXML private TextField emailField;
        @FXML private TextField rollField;
        @FXML private ComboBox<String> departmentCombo;
        @FXML private ComboBox<String> yearCombo;
        @FXML private ComboBox<String> termCombo;
        @FXML private ComboBox<String> sectionCombo;
        @FXML private TextField cgpaField;
        @FXML private TextField passwordField;
        @FXML private Button submitBtn;
        @FXML private Button clearBtn;

        private AdminDashboardController parentController;

        @FXML
        public void initialize() {
            departmentCombo.getItems().addAll("CSE", "EEE", "ME", "CE", "IPE");
            yearCombo.getItems().addAll("1", "2", "3", "4");
            termCombo.getItems().addAll("1", "2");
            sectionCombo.getItems().addAll("A", "B", "C");

            departmentCombo.setValue("CSE");
            yearCombo.setValue("2");
            termCombo.setValue("2");
            sectionCombo.setValue("A");
            cgpaField.setText("0.00");
        }

        public void setParentController(AdminDashboardController parent) {
            this.parentController = parent;
        }

        @FXML
        public void handleSubmit() {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String rollText = rollField.getText().trim();
            String department = departmentCombo.getValue();
            String yearText = yearCombo.getValue();
            String termText = termCombo.getValue();
            String section = sectionCombo.getValue();
            String cgpaText = cgpaField.getText().trim();
            String password = passwordField.getText().trim();

            if (name.isEmpty() || email.isEmpty() || rollText.isEmpty() || password.isEmpty()) {
                parentController.showAlert(Alert.AlertType.ERROR, "Validation Error", "Please fill all required fields");
                return;
            }

            int roll;
            int year;
            int term;
            double cgpa;
            try {
                roll = Integer.parseInt(rollText);
                year = Integer.parseInt(yearText);
                term = Integer.parseInt(termText);
                cgpa = Double.parseDouble(cgpaText);
                if (cgpa < 0 || cgpa > 4.0) {
                    parentController.showAlert(Alert.AlertType.ERROR, "Validation Error", "CGPA must be between 0 and 4.0");
                    return;
                }
            } catch (NumberFormatException e) {
                parentController.showAlert(Alert.AlertType.ERROR, "Validation Error", "Invalid number format");
                return;
            }

            Connection conn = null;
            PreparedStatement userStmt = null;
            PreparedStatement studentStmt = null;

            try {
                conn = parentController.getConnection();
                conn.setAutoCommit(false);

                String userQuery = "INSERT INTO users (email, password, role) VALUES (?, ?, 'Student')";
                userStmt = conn.prepareStatement(userQuery);
                userStmt.setString(1, email);
                userStmt.setString(2, password);
                userStmt.executeUpdate();

                String studentQuery = "INSERT INTO students (name, email, roll, department, year, term, section, CGPA, phone, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                studentStmt = conn.prepareStatement(studentQuery);
                studentStmt.setString(1, name);
                studentStmt.setString(2, email);
                studentStmt.setInt(3, roll);
                studentStmt.setString(4, department);
                studentStmt.setInt(5, year);
                studentStmt.setInt(6, term);
                studentStmt.setString(7, section);
                studentStmt.setDouble(8, cgpa);
                studentStmt.setString(9, "");
                studentStmt.setString(10, password);
                studentStmt.executeUpdate();

                conn.commit();
                parentController.showAlert(Alert.AlertType.INFORMATION, "Success", "Student added successfully!");
                clearForm();

            } catch (SQLException e) {
                if (conn != null) {
                    try {
                        conn.rollback();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
                e.printStackTrace();
                String errorMsg = e.getMessage();
                if (errorMsg.contains("Duplicate entry")) {
                    parentController.showAlert(Alert.AlertType.ERROR, "Database Error", "Student with this email or roll already exists!");
                } else {
                    parentController.showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to add student: " + errorMsg);
                }
            } finally {
                try {
                    if (userStmt != null) userStmt.close();
                    if (studentStmt != null) studentStmt.close();
                    if (conn != null) {
                        conn.setAutoCommit(true);
                        parentController.closeConnection();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        @FXML
        public void clearForm() {
            nameField.clear();
            emailField.clear();
            rollField.clear();
            passwordField.clear();
            cgpaField.setText("0.00");
            departmentCombo.setValue("CSE");
            yearCombo.setValue("2");
            termCombo.setValue("2");
            sectionCombo.setValue("A");
        }
    }

    public static class AddAssignmentController {
        @FXML private TextField titleField;
        @FXML private TextField courseNoField;
        @FXML private TextField courseNameField;
        @FXML private TextArea descriptionArea;
        @FXML private DatePicker assignedDatePicker;
        @FXML private DatePicker dueDatePicker;
        @FXML private ComboBox<String> yearCombo;
        @FXML private ComboBox<String> termCombo;
        @FXML private ComboBox<String> sectionCombo;

        private AdminDashboardController parentController;

        @FXML
        public void initialize() {
            yearCombo.getItems().addAll("1", "2", "3", "4");
            termCombo.getItems().addAll("1", "2");
            sectionCombo.getItems().addAll("A", "B", "C", "All");

            yearCombo.setValue("2");
            termCombo.setValue("2");
            sectionCombo.setValue("A");
            assignedDatePicker.setValue(LocalDate.now());
        }

        public void setParentController(AdminDashboardController parent) {
            this.parentController = parent;
        }

        @FXML
        public void handleSubmit() {
            String title = titleField.getText().trim();
            String courseNo = courseNoField.getText().trim();
            String courseName = courseNameField.getText().trim();
            String description = descriptionArea.getText().trim();
            LocalDate assignedDate = assignedDatePicker.getValue();
            LocalDate dueDate = dueDatePicker.getValue();
            String yearText = yearCombo.getValue();
            String termText = termCombo.getValue();
            String section = sectionCombo.getValue();

            if (title.isEmpty() || courseNo.isEmpty() || courseName.isEmpty() || assignedDate == null || dueDate == null) {
                parentController.showAlert(Alert.AlertType.ERROR, "Validation Error", "Please fill all required fields");
                return;
            }

            if (dueDate.isBefore(assignedDate)) {
                parentController.showAlert(Alert.AlertType.ERROR, "Validation Error", "Due date cannot be before assigned date");
                return;
            }

            Connection conn = null;
            PreparedStatement stmt = null;

            try {
                int year = Integer.parseInt(yearText);
                int term = Integer.parseInt(termText);

                conn = parentController.getConnection();
                String query = "INSERT INTO assignments (course_no, course_name, title, description, assigned_date, due_date, status, year, term, department, section, teacher_name) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                stmt = conn.prepareStatement(query);
                stmt.setString(1, courseNo);
                stmt.setString(2, courseName);
                stmt.setString(3, title);
                stmt.setString(4, description);
                stmt.setTimestamp(5, Timestamp.valueOf(assignedDate.atStartOfDay()));
                stmt.setTimestamp(6, Timestamp.valueOf(dueDate.atStartOfDay()));
                stmt.setString(7, "Active");
                stmt.setInt(8, year);
                stmt.setInt(9, term);
                stmt.setString(10, "CSE");
                stmt.setString(11, section);
                stmt.setString(12, "");
                stmt.executeUpdate();

                parentController.showAlert(Alert.AlertType.INFORMATION, "Success", "Assignment added successfully!");
                clearForm();

            } catch (SQLException e) {
                e.printStackTrace();
                parentController.showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to add assignment: " + e.getMessage());
            } finally {
                try {
                    if (stmt != null) stmt.close();
                    if (conn != null) parentController.closeConnection();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        @FXML
        public void clearForm() {
            titleField.clear();
            courseNoField.clear();
            courseNameField.clear();
            descriptionArea.clear();
            assignedDatePicker.setValue(LocalDate.now());
            dueDatePicker.setValue(null);
        }
    }

    public static class AddAttendanceController {
        @FXML private TextField courseNoField;
        @FXML private TextField courseNameField;
        @FXML private TextField rollField;
        @FXML private DatePicker datePicker;
        @FXML private ComboBox<String> statusCombo;
        @FXML private ComboBox<String> yearCombo;
        @FXML private ComboBox<String> termCombo;
        @FXML private ComboBox<String> departmentCombo;
        @FXML private ComboBox<String> sectionCombo;

        private AdminDashboardController parentController;

        @FXML
        public void initialize() {
            statusCombo.getItems().addAll("Present", "Absent");
            yearCombo.getItems().addAll("1", "2", "3", "4");
            termCombo.getItems().addAll("1", "2");
            departmentCombo.getItems().addAll("CSE", "EEE", "ME", "CE", "IPE");
            sectionCombo.getItems().addAll("A", "B", "C");

            statusCombo.setValue("Present");
            yearCombo.setValue("2");
            termCombo.setValue("2");
            departmentCombo.setValue("CSE");
            sectionCombo.setValue("A");
            datePicker.setValue(LocalDate.now());
        }

        public void setParentController(AdminDashboardController parent) {
            this.parentController = parent;
        }

        @FXML
        public void handleSubmit() {
            String courseNo = courseNoField.getText().trim();
            String courseName = courseNameField.getText().trim();
            String rollText = rollField.getText().trim();
            LocalDate date = datePicker.getValue();
            String status = statusCombo.getValue();
            String yearText = yearCombo.getValue();
            String termText = termCombo.getValue();
            String department = departmentCombo.getValue();
            String section = sectionCombo.getValue();

            if (courseNo.isEmpty() || courseName.isEmpty() || rollText.isEmpty() || date == null) {
                parentController.showAlert(Alert.AlertType.ERROR, "Validation Error", "Please fill all required fields");
                return;
            }

            Connection conn = null;
            PreparedStatement stmt = null;

            try {
                int year = Integer.parseInt(yearText);
                int term = Integer.parseInt(termText);

                conn = parentController.getConnection();
                String query = "INSERT INTO attendance (course_no, course_name, date, status, year, term, department, section, student_roll) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                stmt = conn.prepareStatement(query);
                stmt.setString(1, courseNo);
                stmt.setString(2, courseName);
                stmt.setDate(3, Date.valueOf(date));
                stmt.setString(4, status);
                stmt.setInt(5, year);
                stmt.setInt(6, term);
                stmt.setString(7, department);
                stmt.setString(8, section);
                stmt.setString(9, rollText);
                stmt.executeUpdate();

                parentController.showAlert(Alert.AlertType.INFORMATION, "Success", "Attendance record added successfully!");
                clearForm();

            } catch (SQLException e) {
                e.printStackTrace();
                String errorMsg = e.getMessage();
                if (errorMsg.contains("Duplicate entry")) {
                    parentController.showAlert(Alert.AlertType.ERROR, "Database Error", "Attendance record already exists for this student on this date!");
                } else {
                    parentController.showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to add attendance: " + errorMsg);
                }
            } finally {
                try {
                    if (stmt != null) stmt.close();
                    if (conn != null) parentController.closeConnection();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        @FXML
        public void clearForm() {
            courseNoField.clear();
            courseNameField.clear();
            rollField.clear();
            datePicker.setValue(LocalDate.now());
            statusCombo.setValue("Present");
        }
    }

    public static class AddNoticeController {
        @FXML private TextField titleField;
        @FXML private TextArea descriptionArea;
        @FXML private DatePicker datePicker;
        @FXML private ComboBox<String> yearCombo;
        @FXML private ComboBox<String> termCombo;

        private AdminDashboardController parentController;

        @FXML
        public void initialize() {
            yearCombo.getItems().addAll("All", "1", "2", "3", "4");
            termCombo.getItems().addAll("All", "1", "2");

            yearCombo.setValue("All");
            termCombo.setValue("All");
            datePicker.setValue(LocalDate.now());
        }

        public void setParentController(AdminDashboardController parent) {
            this.parentController = parent;
        }

        @FXML
        public void handleSubmit() {
            String title = titleField.getText().trim();
            String description = descriptionArea.getText().trim();
            LocalDate date = datePicker.getValue();
            String yearText = yearCombo.getValue();
            String termText = termCombo.getValue();

            if (title.isEmpty() || description.isEmpty() || date == null) {
                parentController.showAlert(Alert.AlertType.ERROR, "Validation Error", "Please fill all required fields");
                return;
            }

            Connection conn = null;
            PreparedStatement stmt = null;

            try {
                Integer year = yearText.equals("All") ? null : Integer.parseInt(yearText);
                Integer term = termText.equals("All") ? null : Integer.parseInt(termText);

                conn = parentController.getConnection();
                String query = "INSERT INTO notices (title, description, date, year, term) VALUES (?, ?, ?, ?, ?)";
                stmt = conn.prepareStatement(query);
                stmt.setString(1, title);
                stmt.setString(2, description);
                stmt.setTimestamp(3, Timestamp.valueOf(date.atStartOfDay()));
                if (year != null) {
                    stmt.setInt(4, year);
                } else {
                    stmt.setNull(4, Types.INTEGER);
                }
                if (term != null) {
                    stmt.setInt(5, term);
                } else {
                    stmt.setNull(5, Types.INTEGER);
                }
                stmt.executeUpdate();

                parentController.showAlert(Alert.AlertType.INFORMATION, "Success", "Notice published successfully!");
                clearForm();

            } catch (SQLException e) {
                e.printStackTrace();
                parentController.showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to add notice: " + e.getMessage());
            } finally {
                try {
                    if (stmt != null) stmt.close();
                    if (conn != null) parentController.closeConnection();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        @FXML
        public void clearForm() {
            titleField.clear();
            descriptionArea.clear();
            datePicker.setValue(LocalDate.now());
            yearCombo.setValue("All");
            termCombo.setValue("All");
        }
    }

    public static class AddResultController {
        @FXML private TextField rollField;
        @FXML private TextField courseNameField;
        @FXML private TextField ctMarksField;
        @FXML private TextField attendanceMarksField;
        @FXML private ComboBox<String> yearCombo;
        @FXML private ComboBox<String> termCombo;

        private AdminDashboardController parentController;

        @FXML
        public void initialize() {
            yearCombo.getItems().addAll("1", "2", "3", "4");
            termCombo.getItems().addAll("1", "2");

            yearCombo.setValue("2");
            termCombo.setValue("2");
        }

        public void setParentController(AdminDashboardController parent) {
            this.parentController = parent;
        }

        @FXML
        public void handleSubmit() {
            String rollText = rollField.getText().trim();
            String courseName = courseNameField.getText().trim();
            String obtainedMarksText = ctMarksField.getText().trim();
            String totalMarksText = attendanceMarksField.getText().trim();
            String yearText = yearCombo.getValue();
            String termText = termCombo.getValue();

            if (rollText.isEmpty() || courseName.isEmpty() || obtainedMarksText.isEmpty() || totalMarksText.isEmpty()) {
                parentController.showAlert(Alert.AlertType.ERROR, "Validation Error", "Please fill all required fields");
                return;
            }

            Connection conn = null;
            PreparedStatement stmt = null;

            try {
                int roll = Integer.parseInt(rollText);
                int year = Integer.parseInt(yearText);
                int term = Integer.parseInt(termText);
                double obtainedMarks = Double.parseDouble(obtainedMarksText);
                double totalMarks = Double.parseDouble(totalMarksText);

                conn = parentController.getConnection();
                String query = "INSERT INTO results (roll, title, mark, total_mark, term, year) VALUES (?, ?, ?, ?, ?, ?)";
                stmt = conn.prepareStatement(query);
                stmt.setInt(1, roll);
                stmt.setString(2, courseName);
                stmt.setDouble(3, obtainedMarks);
                stmt.setDouble(4, totalMarks);
                stmt.setInt(5, term);
                stmt.setInt(6, year);
                stmt.executeUpdate();

                parentController.showAlert(Alert.AlertType.INFORMATION, "Success", "Result added successfully!");
                clearForm();

            } catch (NumberFormatException e) {
                parentController.showAlert(Alert.AlertType.ERROR, "Validation Error", "Invalid number format");
            } catch (SQLException e) {
                e.printStackTrace();
                parentController.showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to add result: " + e.getMessage());
            } finally {
                try {
                    if (stmt != null) stmt.close();
                    if (conn != null) parentController.closeConnection();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        @FXML
        public void clearForm() {
            rollField.clear();
            courseNameField.clear();
            ctMarksField.clear();
            attendanceMarksField.clear();
        }
    }

    public static class AddRoutineController {
        @FXML private ComboBox<String> dayCombo;
        @FXML private TextField timeField;
        @FXML private TextField courseNoField;
        @FXML private TextField courseNameField;
        @FXML private TextField teacherField;
        @FXML private TextField roomField;
        @FXML private ComboBox<String> yearCombo;
        @FXML private ComboBox<String> termCombo;
        @FXML private ComboBox<String> departmentCombo;
        @FXML private ComboBox<String> sectionCombo;

        private AdminDashboardController parentController;

        @FXML
        public void initialize() {
            dayCombo.getItems().addAll("Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday");
            yearCombo.getItems().addAll("1", "2", "3", "4");
            termCombo.getItems().addAll("1", "2");
            departmentCombo.getItems().addAll("CSE", "EEE", "ME", "CE", "IPE");
            sectionCombo.getItems().addAll("A", "B", "C");

            dayCombo.setValue("Saturday");
            yearCombo.setValue("2");
            termCombo.setValue("2");
            departmentCombo.setValue("CSE");
            sectionCombo.setValue("A");
        }

        public void setParentController(AdminDashboardController parent) {
            this.parentController = parent;
        }

        @FXML
        public void handleSubmit() {
            String day = dayCombo.getValue();
            String time = timeField.getText().trim();
            String courseNo = courseNoField.getText().trim();
            String courseName = courseNameField.getText().trim();
            String teacher = teacherField.getText().trim();
            String room = roomField.getText().trim();
            String yearText = yearCombo.getValue();
            String termText = termCombo.getValue();
            String department = departmentCombo.getValue();
            String section = sectionCombo.getValue();

            if (day == null || time.isEmpty() || courseNo.isEmpty() || courseName.isEmpty() || teacher.isEmpty() || room.isEmpty()) {
                parentController.showAlert(Alert.AlertType.ERROR, "Validation Error", "Please fill all required fields");
                return;
            }

            Connection conn = null;
            PreparedStatement stmt = null;

            try {
                int year = Integer.parseInt(yearText);
                int term = Integer.parseInt(termText);

                conn = parentController.getConnection();
                String query = "INSERT INTO class_routine (day, time_slot, course_code, course_name, teacher, room, type, year, term, section, department) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                stmt = conn.prepareStatement(query);
                stmt.setString(1, day);
                stmt.setString(2, time);
                stmt.setString(3, courseNo);
                stmt.setString(4, courseName);
                stmt.setString(5, teacher);
                stmt.setString(6, room);
                stmt.setString(7, "Lecture");
                stmt.setInt(8, year);
                stmt.setInt(9, term);
                stmt.setString(10, section);
                stmt.setString(11, department);
                stmt.executeUpdate();

                parentController.showAlert(Alert.AlertType.INFORMATION, "Success", "Class routine added successfully!");
                clearForm();

            } catch (SQLException e) {
                e.printStackTrace();
                parentController.showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to add routine: " + e.getMessage());
            } finally {
                try {
                    if (stmt != null) stmt.close();
                    if (conn != null) parentController.closeConnection();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        @FXML
        public void clearForm() {
            timeField.clear();
            courseNoField.clear();
            courseNameField.clear();
            teacherField.clear();
            roomField.clear();
            dayCombo.setValue("Saturday");
        }
    }

    public static class UpdateStudentController {
        @FXML private TextField searchRollField;
        @FXML private VBox updateFormContainer;
        @FXML private TextField nameField;
        @FXML private TextField rollField;
        @FXML private TextField emailField;
        @FXML private TextField phoneField;
        @FXML private ComboBox<String> yearCombo;
        @FXML private ComboBox<String> termCombo;
        @FXML private ComboBox<String> sectionCombo;
        @FXML private ComboBox<String> deptCombo;

        private AdminDashboardController parentController;
        private int currentStudentId = -1;

        @FXML
        public void initialize() {
            yearCombo.getItems().addAll("1", "2", "3", "4");
            termCombo.getItems().addAll("1", "2");
            sectionCombo.getItems().addAll("A", "B", "C");
            deptCombo.getItems().addAll("CSE", "EEE", "ME", "CE", "IPE");
        }

        public void setParentController(AdminDashboardController parent) {
            this.parentController = parent;
        }

        @FXML
        public void searchStudent() {
            String searchRoll = searchRollField.getText().trim();

            if (searchRoll.isEmpty()) {
                parentController.showAlert(Alert.AlertType.ERROR, "Validation Error", "Please enter a roll number");
                return;
            }

            Connection conn = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;

            try {
                conn = parentController.getConnection();
                String query = "SELECT * FROM students WHERE roll = ?";
                stmt = conn.prepareStatement(query);
                stmt.setString(1, searchRoll);
                rs = stmt.executeQuery();

                if (rs.next()) {
                    currentStudentId = rs.getInt("id");
                    nameField.setText(rs.getString("name"));
                    rollField.setText(rs.getString("roll"));
                    emailField.setText(rs.getString("email"));
                    phoneField.setText(rs.getString("phone") != null ? rs.getString("phone") : "");
                    yearCombo.setValue(String.valueOf(rs.getInt("year")));
                    termCombo.setValue(String.valueOf(rs.getInt("term")));
                    sectionCombo.setValue(rs.getString("section"));
                    deptCombo.setValue(rs.getString("department"));

                    updateFormContainer.setVisible(true);
                    updateFormContainer.setManaged(true);
                } else {
                    parentController.showAlert(Alert.AlertType.ERROR, "Not Found", "No student found with roll: " + searchRoll);
                    updateFormContainer.setVisible(false);
                    updateFormContainer.setManaged(false);
                }

            } catch (SQLException e) {
                e.printStackTrace();
                parentController.showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to search student: " + e.getMessage());
            } finally {
                try {
                    if (rs != null) rs.close();
                    if (stmt != null) stmt.close();
                    if (conn != null) parentController.closeConnection();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        @FXML
        public void updateStudent() {
            if (currentStudentId == -1) {
                parentController.showAlert(Alert.AlertType.ERROR, "Error", "No student selected");
                return;
            }

            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();
            String yearText = yearCombo.getValue();
            String termText = termCombo.getValue();
            String section = sectionCombo.getValue();
            String department = deptCombo.getValue();

            if (name.isEmpty() || email.isEmpty()) {
                parentController.showAlert(Alert.AlertType.ERROR, "Validation Error", "Name and Email are required");
                return;
            }

            Connection conn = null;
            PreparedStatement stmt = null;

            try {
                int year = Integer.parseInt(yearText);
                int term = Integer.parseInt(termText);

                conn = parentController.getConnection();
                String query = "UPDATE students SET name = ?, email = ?, phone = ?, year = ?, term = ?, section = ?, department = ? WHERE id = ?";
                stmt = conn.prepareStatement(query);
                stmt.setString(1, name);
                stmt.setString(2, email);
                stmt.setString(3, phone);
                stmt.setInt(4, year);
                stmt.setInt(5, term);
                stmt.setString(6, section);
                stmt.setString(7, department);
                stmt.setInt(8, currentStudentId);

                int rowsUpdated = stmt.executeUpdate();

                if (rowsUpdated > 0) {
                    parentController.showAlert(Alert.AlertType.INFORMATION, "Success", "Student information updated successfully!");
                } else {
                    parentController.showAlert(Alert.AlertType.ERROR, "Error", "Failed to update student");
                }

            } catch (SQLException e) {
                e.printStackTrace();
                parentController.showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to update student: " + e.getMessage());
            } catch (NumberFormatException e) {
                parentController.showAlert(Alert.AlertType.ERROR, "Validation Error", "Invalid year or term value");
            } finally {
                try {
                    if (stmt != null) stmt.close();
                    if (conn != null) parentController.closeConnection();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        @FXML
        public void clearForm() {
            searchRollField.clear();
            nameField.clear();
            rollField.clear();
            emailField.clear();
            phoneField.clear();
            yearCombo.setValue(null);
            termCombo.setValue(null);
            sectionCombo.setValue(null);
            deptCombo.setValue(null);
            updateFormContainer.setVisible(false);
            updateFormContainer.setManaged(false);
            currentStudentId = -1;
        }
    }
}
