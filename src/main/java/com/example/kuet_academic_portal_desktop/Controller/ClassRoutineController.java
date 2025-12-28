package com.example.kuet_academic_portal_desktop.Controller;

import com.example.kuet_academic_portal_desktop.Model.RoutineEntry;
import com.example.kuet_academic_portal_desktop.Session;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Time;
import java.util.*;

public class ClassRoutineController {

    @FXML
    private Button goDash;

    @FXML
    private Label semesterLabel;

    @FXML
    private TableView<DayRoutine> routineTableView;

    @FXML
    private TableColumn<DayRoutine, String> dayColumn;

    @FXML
    private TableColumn<DayRoutine, String> slot1Column;

    @FXML
    private TableColumn<DayRoutine, String> slot2Column;

    @FXML
    private TableColumn<DayRoutine, String> slot3Column;

    @FXML
    private TableColumn<DayRoutine, String> slot4Column;

    @FXML
    private TableColumn<DayRoutine, String> slot5Column;

    @FXML
    private TableColumn<DayRoutine, String> slot6Column;

    private databaseConnect dbConnect;

    private static final String[] DAYS = {"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};

    private static final String[] TIME_SLOTS = {
        "08:00:00-08:50:00",
        "08:50:00-09:40:00",
        "09:40:00-10:30:00",
        "10:40:00-11:30:00",
        "11:30:00-12:20:00",
        "12:20:00-01:10:00"
    };

    @FXML
    public void initialize() {
        dbConnect = new databaseConnect();

        setupSemesterLabel();
        setupTableColumns();
        loadRoutineData();
    }

    private void setupSemesterLabel() {
        Session session = Session.getInstance();
        String semesterInfo = session.getYear() + "-" + session.getTerm() + " " + session.getDepartment();
        if (session.getSection() != null && !session.getSection().isEmpty()) {
            semesterInfo += " Section " + session.getSection();
        }
        semesterLabel.setText(semesterInfo);
    }

    private void setupTableColumns() {
        dayColumn.setCellValueFactory(data -> data.getValue().dayProperty());
        slot1Column.setCellValueFactory(data -> data.getValue().slot1Property());
        slot2Column.setCellValueFactory(data -> data.getValue().slot2Property());
        slot3Column.setCellValueFactory(data -> data.getValue().slot3Property());
        slot4Column.setCellValueFactory(data -> data.getValue().slot4Property());
        slot5Column.setCellValueFactory(data -> data.getValue().slot5Property());
        slot6Column.setCellValueFactory(data -> data.getValue().slot6Property());

        dayColumn.setStyle("-fx-alignment: CENTER; -fx-font-weight: bold;");
        slot1Column.setStyle("-fx-alignment: CENTER;");
        slot2Column.setStyle("-fx-alignment: CENTER;");
        slot3Column.setStyle("-fx-alignment: CENTER;");
        slot4Column.setStyle("-fx-alignment: CENTER;");
        slot5Column.setStyle("-fx-alignment: CENTER;");
        slot6Column.setStyle("-fx-alignment: CENTER;");
    }

    private void loadRoutineData() {
        Session session = Session.getInstance();
        String department = session.getDepartment();
        String year = session.getYear();
        String section = session.getSection();
        String term = session.getTerm();

        System.out.println("Loading routine for: Dept=" + department + ", Year=" + year +
                         ", Term=" + term + ", Section=" + section);

        List<RoutineEntry> routineData = dbConnect.loadRoutineData(department, year, section);

        if (routineData != null && !routineData.isEmpty()) {
            System.out.println("Loaded " + routineData.size() + " routine entries");

            List<RoutineEntry> filteredData = new ArrayList<>();
            for (RoutineEntry entry : routineData) {
                if (entry.getTerm() == Integer.parseInt(term)) {
                    filteredData.add(entry);
                }
            }

            if (filteredData.isEmpty()) {
                System.out.println("No entries match the term: " + term);
                displayRoutine(routineData);
            } else {
                System.out.println("Filtered to " + filteredData.size() + " entries for term " + term);
                displayRoutine(filteredData);
            }
        } else {
            System.out.println("No routine data found");
            routineTableView.setPlaceholder(new Label("No routine data available for your section."));
        }
    }

    private void displayRoutine(List<RoutineEntry> data) {
        Map<String, Map<String, RoutineEntry>> routineMap = new HashMap<>();

        for (String day : DAYS) {
            routineMap.put(day, new HashMap<>());
        }

        for (RoutineEntry entry : data) {
            String day = entry.getDay();
            String timeSlot = getTimeSlotKey(entry.getStartTime(), entry.getEndTime());

            if (routineMap.containsKey(day) && timeSlot != null) {
                routineMap.get(day).put(timeSlot, entry);
            }
        }

        ObservableList<DayRoutine> routineList = FXCollections.observableArrayList();

        for (String day : DAYS) {
            Map<String, RoutineEntry> daySchedule = routineMap.get(day);

            String slot1 = formatCellContent(daySchedule.get(TIME_SLOTS[0]));
            String slot2 = formatCellContent(daySchedule.get(TIME_SLOTS[1]));
            String slot3 = formatCellContent(daySchedule.get(TIME_SLOTS[2]));
            String slot4 = formatCellContent(daySchedule.get(TIME_SLOTS[3]));
            String slot5 = formatCellContent(daySchedule.get(TIME_SLOTS[4]));
            String slot6 = formatCellContent(daySchedule.get(TIME_SLOTS[5]));

            routineList.add(new DayRoutine(day, slot1, slot2, slot3, slot4, slot5, slot6));
        }

        routineTableView.setItems(routineList);
    }

    private String getTimeSlotKey(Time startTime, Time endTime) {
        if (startTime == null || endTime == null) {
            return null;
        }

        String start = startTime.toString();
        String end = endTime.toString();
        String key = start + "-" + end;

        for (String slot : TIME_SLOTS) {
            if (slot.equals(key)) {
                return slot;
            }
        }

        for (String slot : TIME_SLOTS) {
            String[] parts = slot.split("-");
            Time slotStart = Time.valueOf(parts[0]);
            Time slotEnd = Time.valueOf(parts[1]);

            if (startTime.compareTo(slotStart) >= 0 && startTime.compareTo(slotEnd) < 0) {
                return slot;
            }
        }

        return null;
    }

    private String formatCellContent(RoutineEntry entry) {
        if (entry == null) {
            return "";
        }

        return entry.getCourseNo() + "\n" +
               entry.getTeacher() + "\n" +
               "Room: " + entry.getRoomNumber();
    }

    @SuppressWarnings("unused")
    public void refreshRoutine() {
        loadRoutineData();
    }

    public static class DayRoutine {
        private final SimpleStringProperty day;
        private final SimpleStringProperty slot1;
        private final SimpleStringProperty slot2;
        private final SimpleStringProperty slot3;
        private final SimpleStringProperty slot4;
        private final SimpleStringProperty slot5;
        private final SimpleStringProperty slot6;

        public DayRoutine(String day, String slot1, String slot2, String slot3,
                         String slot4, String slot5, String slot6) {
            this.day = new SimpleStringProperty(day);
            this.slot1 = new SimpleStringProperty(slot1);
            this.slot2 = new SimpleStringProperty(slot2);
            this.slot3 = new SimpleStringProperty(slot3);
            this.slot4 = new SimpleStringProperty(slot4);
            this.slot5 = new SimpleStringProperty(slot5);
            this.slot6 = new SimpleStringProperty(slot6);
        }

        public SimpleStringProperty dayProperty() { return day; }
        public SimpleStringProperty slot1Property() { return slot1; }
        public SimpleStringProperty slot2Property() { return slot2; }
        public SimpleStringProperty slot3Property() { return slot3; }
        public SimpleStringProperty slot4Property() { return slot4; }
        public SimpleStringProperty slot5Property() { return slot5; }
        public SimpleStringProperty slot6Property() { return slot6; }

        @SuppressWarnings("unused")
        public String getDay() { return day.get(); }
        @SuppressWarnings("unused")
        public String getSlot1() { return slot1.get(); }
        @SuppressWarnings("unused")
        public String getSlot2() { return slot2.get(); }
        @SuppressWarnings("unused")
        public String getSlot3() { return slot3.get(); }
        @SuppressWarnings("unused")
        public String getSlot4() { return slot4.get(); }
        @SuppressWarnings("unused")
        public String getSlot5() { return slot5.get(); }
        @SuppressWarnings("unused")
        public String getSlot6() { return slot6.get(); }
    }

    @FXML
    void goDashFun() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/kuet_academic_portal_desktop/Student_Dashboard.fxml"));
        Parent root =loader.load();
        Scene s =new Scene(root);
        Stage stage =(Stage) goDash.getScene().getWindow();
        stage.setScene(s);
        stage.setTitle("Student Dashboard - KUET Academic Portal");
        stage.show();


    }
}

