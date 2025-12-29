package com.example.kuet_academic_portal_desktop.Controller;

import com.example.kuet_academic_portal_desktop.Model.Contact;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ContactsController {

    @FXML
    private VBox contactsVBox;

    @FXML
    private Label noContactsLabel;

    public void initialize() {
        System.out.println("Contacts page opened.");
        loadAllContacts();
    }

    private void loadAllContacts() {
        Task<List<Contact>> loadContactsTask = new Task<>() {
            @Override
            protected List<Contact> call() throws Exception {
                databaseConnect db = new databaseConnect();
                return db.loadContactData();
            }

            @Override
            protected void succeeded() {
                List<Contact> contacts = getValue();
                System.out.println("Contacts page loaded " + (contacts == null ? 0 : contacts.size()) + " contacts");
                Platform.runLater(() -> displayAllContacts(contacts));
            }

            @Override
            protected void failed() {
                System.err.println("Failed to load contacts: " + getException().getMessage());
                if (getException() != null) {
                    getException().printStackTrace();
                }
                Platform.runLater(() -> {
                    noContactsLabel.setText("Failed to load contacts. Please try again later.");
                    noContactsLabel.setVisible(true);
                });
            }
        };

        Thread contactThread = new Thread(loadContactsTask);
        contactThread.setDaemon(true);
        contactThread.start();
    }

    private void displayAllContacts(List<Contact> contacts) {
        contactsVBox.getChildren().clear();

        if (contacts == null || contacts.isEmpty()) {
            noContactsLabel.setText("No contacts available at the moment.");
            noContactsLabel.setVisible(true);
            contactsVBox.getChildren().add(noContactsLabel);
            return;
        }

        noContactsLabel.setVisible(false);

        for (Contact contact : contacts) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/kuet_academic_portal_desktop/contact_card.fxml"));
                VBox contactCard = loader.load();
                ContactCardController controller = loader.getController();
                controller.setContactData(contact);
                contactCard.setMaxWidth(Double.MAX_VALUE);
                contactsVBox.getChildren().add(contactCard);
            } catch (IOException e) {
                System.err.println("Failed to load contact card: " + e.getMessage());
                e.printStackTrace();
                // Fallback: create a simple contact card
                VBox fallbackCard = createFallbackContactCard(contact);
                contactsVBox.getChildren().add(fallbackCard);
            }
        }
    }

    private VBox createFallbackContactCard(Contact contact) {
        VBox card = new VBox(10);
        card.setStyle("-fx-background-color: white; -fx-padding: 20; -fx-background-radius: 10; -fx-border-color: #ddd; -fx-border-radius: 10;");
        card.setPadding(new Insets(20));

        Label nameLabel = new Label(contact.getName());
        nameLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label roleLabel = new Label("Role: " + contact.getRole());
        roleLabel.setStyle("-fx-font-size: 14px;");

        card.getChildren().addAll(nameLabel, roleLabel);

        if (contact.getDesignation() != null && !contact.getDesignation().isEmpty()) {
            Label designationLabel = new Label("Designation: " + contact.getDesignation());
            designationLabel.setStyle("-fx-font-size: 14px;");
            card.getChildren().add(designationLabel);
        }

        if (contact.getRollId() != null && !contact.getRollId().isEmpty()) {
            Label rollLabel = new Label("Roll/ID: " + contact.getRollId());
            rollLabel.setStyle("-fx-font-size: 14px;");
            card.getChildren().add(rollLabel);
        }

        if (contact.getDepartment() != null && !contact.getDepartment().isEmpty()) {
            Label deptLabel = new Label("Department: " + contact.getDepartment());
            deptLabel.setStyle("-fx-font-size: 14px;");
            card.getChildren().add(deptLabel);
        }

        if (contact.getEmail() != null && !contact.getEmail().isEmpty()) {
            Label emailLabel = new Label("Email: " + contact.getEmail());
            emailLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #2980b9;");
            card.getChildren().add(emailLabel);
        }

        if (contact.getPhone() != null && !contact.getPhone().isEmpty()) {
            Label phoneLabel = new Label("Phone: " + contact.getPhone());
            phoneLabel.setStyle("-fx-font-size: 14px;");
            card.getChildren().add(phoneLabel);
        }

        return card;
    }

    @FXML
    private void goBackToDashboard(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/kuet_academic_portal_desktop/Student_Dashboard.fxml"));
            Parent dashboard = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(dashboard);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Failed to load dashboard: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
