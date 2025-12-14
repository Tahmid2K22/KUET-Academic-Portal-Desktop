module com.example.kuet_academic_portal_desktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;

    opens com.example.kuet_academic_portal_desktop to javafx.fxml;
    opens com.example.kuet_academic_portal_desktop.Controller to javafx.fxml;
    opens com.example.kuet_academic_portal_desktop.CSS to javafx.fxml;
    exports com.example.kuet_academic_portal_desktop;
}