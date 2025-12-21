package com.example.kuet_academic_portal_desktop.Controller;

import com.example.kuet_academic_portal_desktop.Model.Notice;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class NoticeCardController {
    @FXML
    private Label title;

    @FXML
    private Label desc;

    @FXML
    private Label date;

    public void setNoticeData(Notice notice) {
        title.setText(notice.getTitle());
        desc.setText(notice.getDescription());
        date.setText(notice.getDate());
    }
}

