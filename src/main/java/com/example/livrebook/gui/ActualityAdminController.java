package com.example.livrebook.gui;

import com.example.livrebook.model.actuality.Actuality;
import com.example.livrebook.service.actualityServices.ActualityService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Date;
import java.util.List;

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
    private TableColumn<Actuality, Date> colDate;

    @FXML
    private TableColumn<Actuality, Integer> colId;

    @FXML
    private TableColumn<Actuality, String> colTitle;

    @FXML
    private TableColumn<Actuality, String> coldesc;

    @FXML
    private TableView<Actuality> TVId;
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
        loadActualityData();
    }

    @FXML
    void MAJ(ActionEvent event) {
        Actuality selectedActuality = TVId.getSelectionModel().getSelectedItem();

        if (selectedActuality != null) {
            // Obtenir les valeurs actuelles des champs de saisie
            String title = TfTitle.getText();
            String desc = tfDesc.getText();
            LocalDate selectedDate = IdDate.getValue();
            Date date = (selectedDate != null) ? java.sql.Date.valueOf(selectedDate) : null;

            // Mettre à jour les propriétés de l'objet Actuality
            selectedActuality.setTitle(title);
            selectedActuality.setDescription(desc);
            selectedActuality.setDate(date);

            try {
                actu.update(selectedActuality);
                System.out.println("actu updated");
                loadActualityData(); // Rafraîchir les données après la mise à jour
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            // Affichez un message d'erreur ou faites quelque chose pour informer l'utilisateur
            System.out.println("Veuillez sélectionner une ligne à mettre à jour.");
        }
    }
    @FXML
    void supprimer(ActionEvent event) {
        Actuality selectedActuality = TVId.getSelectionModel().getSelectedItem();

        if (selectedActuality != null) {
            try {
                actu.delete(selectedActuality);
                System.out.println("actu deleted");
                loadActualityData();
                showPopup("Actuality supprimée avec succès", Alert.AlertType.INFORMATION);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            showPopup("Veuillez sélectionner une ligne à supprimer.", Alert.AlertType.INFORMATION);
            System.out.println("Veuillez sélectionner une ligne à supprimer.");
        }
    }
    @FXML
    private void initialize() {

            colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            coldesc.setCellValueFactory(new PropertyValueFactory<>("description"));
            colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

            TVId.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    TfTitle.setText(newSelection.getTitle());
                    tfDesc.setText(newSelection.getDescription());
                    //IdDate.setValue(newSelection.getDate());
                }
            });

            loadActualityData();

        }


    private void loadActualityData() {
        try {
            List<Actuality> actualities = actu.selectAll(); // Supposons que vous ayez une méthode getAll() dans ActualityService
            TVId.getItems().setAll(actualities);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void showPopup(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



}



