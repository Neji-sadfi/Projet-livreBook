package com.example.livrebook.gui.actuality;

import com.example.livrebook.model.actuality.Actuality;
import com.example.livrebook.service.actualityServices.ActualityService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.stage.FileChooser;
import javafx.util.Callback;

import java.io.File;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Optional;

public class ActualityAdminController {
    private final ActualityService actu;

    public ActualityAdminController() {
        this.actu = new ActualityService();
    }

    @FXML
    private TableView<Actuality> TVId;

    @FXML
    private TableColumn<Actuality, String> colTitle;

    @FXML
    private TableColumn<Actuality, String> colDesc;

    @FXML
    private TableColumn<Actuality, Date> colDate;
    @FXML
    private TableColumn<Actuality, String> colImage;

    @FXML
    private void initialize() {
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colImage.setCellValueFactory(new PropertyValueFactory<>("picture"));


        colDate.setCellFactory(new DateCellFactory());

        loadActualityData();
    }

    private void addActuality() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Ajouter une actualité");
        dialog.setHeaderText(null);
        dialog.setContentText("Entrez le titre:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String title = result.get();

            dialog.setContentText("Entrez la description:");
            result = dialog.showAndWait();
            if (result.isPresent()) {
                String desc = result.get();
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Sélectionner une image");
                File file = fileChooser.showOpenDialog(null);

                if (file != null) {
                    String pictureName = file.getName();

                    LocalDate date = LocalDate.now();
                    Actuality u1 = new Actuality(title, desc, Date.valueOf(date), pictureName);
                    System.out.println(pictureName);
                    try {
                        actu.insert(u1);
                        showPopup("Actualité ajoutée avec succès", Alert.AlertType.INFORMATION);
                        loadActualityData();
                    } catch (SQLException e) {
                        showPopup("Erreur lors de l'ajout de l'actualité.", Alert.AlertType.ERROR);
                        throw new RuntimeException(e);
                    }
                } else {
                    showPopup("Veuillez sélectionner une image.", Alert.AlertType.WARNING);
                }
            } else {
                showPopup("Veuillez entrer une description.", Alert.AlertType.WARNING);
            }
        } else {
            showPopup("Veuillez entrer un titre.", Alert.AlertType.WARNING);
        }
    }



    private void editTitle(Actuality actuality) {
        TextInputDialog dialog = new TextInputDialog(actuality.getTitle());
        dialog.setTitle("Édition du titre");
        dialog.setHeaderText(null);
        dialog.setContentText("Entrez le nouveau titre :");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newTitle -> {
            actuality.setTitle(newTitle);
            try {
                actu.update(actuality);
                showPopup("Titre mis à jour avec succès", Alert.AlertType.INFORMATION);
                loadActualityData();
            } catch (SQLException e) {
                showPopup("Erreur lors de la mise à jour du titre.", Alert.AlertType.ERROR);
                throw new RuntimeException(e);
            }
        });
    }
    private void editDescription(Actuality actuality) {
        TextInputDialog dialog = new TextInputDialog(actuality.getDescription());
        dialog.setTitle("Édition de la description");
        dialog.setHeaderText(null);
        dialog.setContentText("Entrez la nouvelle description :");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newDescription -> {
            actuality.setDescription(newDescription);
            try {
                actu.update(actuality);
                showPopup("Description mise à jour avec succès", Alert.AlertType.INFORMATION);
                loadActualityData();
            } catch (SQLException e) {
                showPopup("Erreur lors de la mise à jour de la description.", Alert.AlertType.ERROR);
                throw new RuntimeException(e);
            }
        });
    }

    private void editDate(Actuality actuality) {
        DatePicker datePicker = new DatePicker();
        datePicker.setValue(actuality.getDate().toLocalDate());

        Dialog<LocalDate> dialog = new Dialog<>();
        dialog.setTitle("Édition de la date");
        dialog.setHeaderText(null);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dialog.getDialogPane().setContent(datePicker);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                return datePicker.getValue();
            }
            return null;
        });

        Optional<LocalDate> result = dialog.showAndWait();
        result.ifPresent(newDate -> {
            actuality.setDate(Date.valueOf(newDate));
            try {
                actu.update(actuality);
                showPopup("Date mise à jour avec succès", Alert.AlertType.INFORMATION);
                loadActualityData();
            } catch (SQLException e) {
                showPopup("Erreur lors de la mise à jour de la date.", Alert.AlertType.ERROR);
                throw new RuntimeException(e);
            }
        });
    }

    private void deleteActuality(Actuality actuality) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr de vouloir supprimer cette actualité ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                actu.delete(actuality.getId());
                showPopup("Actualité supprimée avec succès", Alert.AlertType.INFORMATION);
                loadActualityData();
            } catch (SQLException e) {
                showPopup("Erreur lors de la suppression de l'actualité.", Alert.AlertType.ERROR);
                throw new RuntimeException(e);
            }
        }
    }

    private void loadActualityData() {
        try {

            TVId.getItems().setAll(actu.selectAll());
        } catch (SQLException e) {
            showPopup("Erreur lors du chargement des actualités.", Alert.AlertType.ERROR);
            throw new RuntimeException(e);
        }
    }

    private void showPopup(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleEditAction(ActionEvent event) {
        if (!TVId.getSelectionModel().getSelectedCells().isEmpty()) {
            TableColumn<Actuality, ?> selectedColumn = TVId.getSelectionModel().getSelectedCells().get(0).getTableColumn();
            Actuality selectedActuality = TVId.getSelectionModel().getSelectedItem();

            if (selectedColumn != null && selectedActuality != null) {
                if (selectedColumn.equals(colTitle)) {
                    editTitle(selectedActuality);
                } else if (selectedColumn.equals(colDesc)) {
                    editDescription(selectedActuality);
                } else if (selectedColumn.equals(colDate)) {
                    editDate(selectedActuality);
                }
            } else {
                showPopup("Veuillez sélectionner une cellule à modifier.", Alert.AlertType.WARNING);
            }
        } else {
            showPopup("Veuillez sélectionner une cellule à modifier.", Alert.AlertType.WARNING);
        }
    }


    @FXML
    private void handleDeleteAction(ActionEvent event) {
        Actuality selectedActuality = TVId.getSelectionModel().getSelectedItem();

        if (selectedActuality != null) {
            deleteActuality(selectedActuality);
        } else {
            showPopup("Veuillez sélectionner une ligne à supprimer.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void handleAddAction(ActionEvent event) {
        addActuality();
    }

    public class DateCellFactory implements Callback<TableColumn<Actuality, Date>, TableCell<Actuality, Date>> {
        private final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        @Override
        public TableCell<Actuality, Date> call(TableColumn<Actuality, Date> param) {
            return new TableCell<>() {
                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(format.format(item));
                    }
                }
            };
        }
    }
}