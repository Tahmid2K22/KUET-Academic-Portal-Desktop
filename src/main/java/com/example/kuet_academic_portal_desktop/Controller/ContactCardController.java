package com.example.kuet_academic_portal_desktop.Controller;

import com.example.kuet_academic_portal_desktop.Model.Contact;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ContactCardController {

    @FXML
    private Label nameLabel;

    @FXML
    private Label roleLabel;

    @FXML
    private Label rollIdLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label departmentLabel;

    @FXML
    private Label designationLabel;

    public void setContactData(Contact contact) {
        if (contact == null) return;

        nameLabel.setText(contact.getName());
        roleLabel.setText(contact.getRole());

        if (contact.getRollId() != null && !contact.getRollId().isEmpty()) {
            rollIdLabel.setText(contact.getRollId());
            rollIdLabel.setVisible(true);
        } else {
            rollIdLabel.setVisible(false);
            rollIdLabel.setManaged(false);
        }

        if (contact.getPhone() != null && !contact.getPhone().isEmpty()) {
            phoneLabel.setText(contact.getPhone());
            phoneLabel.setVisible(true);
        } else {
            phoneLabel.setVisible(false);
            phoneLabel.setManaged(false);
        }

        if (contact.getEmail() != null && !contact.getEmail().isEmpty()) {
            emailLabel.setText(contact.getEmail());
            emailLabel.setVisible(true);
        } else {
            emailLabel.setVisible(false);
            emailLabel.setManaged(false);
        }

        if (contact.getDepartment() != null && !contact.getDepartment().isEmpty()) {
            departmentLabel.setText(contact.getDepartment());
            departmentLabel.setVisible(true);
        } else {
            departmentLabel.setVisible(false);
            departmentLabel.setManaged(false);
        }

        if (contact.getDesignation() != null && !contact.getDesignation().isEmpty()) {
            designationLabel.setText(contact.getDesignation());
            designationLabel.setVisible(true);
        } else {
            designationLabel.setVisible(false);
            designationLabel.setManaged(false);
        }
    }
}
