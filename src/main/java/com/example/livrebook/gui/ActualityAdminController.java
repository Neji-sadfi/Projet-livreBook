package com.example.livrebook.gui;

import com.example.livrebook.model.actuality.Actuality;
import com.example.livrebook.service.actualityServices.ActualityService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Date;

public class ActualityAdminController {
  ActualityService actu;
    public ActualityAdminController() {
        this.actu = new ActualityService();
    }
    @FXML
    private DatePicker IdDate;

    @FXML
    private TextField TfTitle;

    @FXML
    private Button btnAjout;

    @FXML
    private TextArea tfDesc;

    @FXML
    private Button btnMaj;

    @FXML
    private Button btnRemove;
    @FXML
    private TextField tfId;
    @FXML
    void ajout(ActionEvent event) {
        String title = TfTitle.getText();
        String desc = tfDesc.getText();

        LocalDate selectedDate = IdDate.getValue();
        Date date = null;

        if (selectedDate != null) {
            // Convertir de LocalDate à java.sql.Date
            date = java.sql.Date.valueOf(selectedDate);
        }

        Actuality u1 = new Actuality(title, desc, date);

        try {
            actu.insert(u1);
            System.out.println("actu inserted");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void MAJ(ActionEvent event) {
        String title = TfTitle.getText();
        String desc = tfDesc.getText();
        String id = tfId.getText();

        LocalDate selectedDate = IdDate.getValue();
        Date date = null;

        if (selectedDate != null) {
            // Convertir de LocalDate à java.sql.Date
            date = java.sql.Date.valueOf(selectedDate);
        }

        Actuality u2 = new Actuality(Integer.parseInt(id),title,desc,date);
        try {
            actu.update(u2);
            System.out.println("actu updated");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void supprimer(ActionEvent event) {
        String id = tfId.getText();
        Actuality u3 = new Actuality(Integer.parseInt(id));
        try {
            actu.delete(u3);
            System.out.println("actu deleted");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    }



