module com.example.livrebook {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    //requires com.google.zxing;
    //requires com.google.zxing.javase;
    //requires itextpdf;
    requires java.desktop;
    requires com.jfoenix;
    requires com.google.zxing;
    requires itextpdf;
    requires com.google.zxing.javase;
    //requires com.jfoenix;

    opens com.example.livrebook to javafx.fxml;
    opens com.example.livrebook.model.user to javafx.base; // Open this specific package
    exports com.example.livrebook.gui;
    opens com.example.livrebook.gui to javafx.fxml;
    exports com.example.livrebook.test;
    opens com.example.livrebook.test to javafx.fxml;
    exports com.example.livrebook.gui.book.backOffice;
    opens com.example.livrebook.gui.book.backOffice to javafx.fxml;
    opens com.example.livrebook.model.book to javafx.base;
    exports com.example.livrebook.gui.book.frontOffice;
    opens com.example.livrebook.gui.book.frontOffice to javafx.fxml;

    exports com.example.livrebook.gui.delivery;
    opens com.example.livrebook.gui.delivery to javafx.fxml;
    exports com.example.livrebook.test.delivery;

    exports com.example.livrebook.model.reclamation;
    opens com.example.livrebook.model.reclamation to javafx.base;

    opens com.example.livrebook.model.delivery to javafx.base;
    exports com.example.livrebook.model.delivery;
    opens com.example.livrebook.model.deliveryOrder to javafx.base;
    exports com.example.livrebook.model.deliveryOrder;
    exports com.example.livrebook.gui.event;
    opens com.example.livrebook.gui.event to javafx.fxml;

    opens com.example.livrebook.model.actuality to javafx.base;
    exports com.example.livrebook.model.actuality;
    exports com.example.livrebook.gui.actuality;
    opens com.example.livrebook.gui.actuality;
    exports com.example.livrebook.test.actuality;
    opens com.example.livrebook.test.actuality to javafx.fxml;
    opens com.example.livrebook.model.event to javafx.base;
}

