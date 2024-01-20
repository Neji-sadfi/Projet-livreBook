module com.example.livrebook {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.livrebook to javafx.fxml;
    exports com.example.livrebook.gui;
    opens com.example.livrebook.gui to javafx.fxml;
    exports com.example.livrebook.test;
    opens com.example.livrebook.test to javafx.fxml;
    //exports com.example.livrebook;
}