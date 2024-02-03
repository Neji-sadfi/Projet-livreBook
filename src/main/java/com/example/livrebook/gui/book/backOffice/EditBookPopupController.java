package com.example.livrebook.gui.book.backOffice;

import com.example.livrebook.model.book.Book;
import com.example.livrebook.service.bookServices.BookService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class EditBookPopupController {

    @FXML
    private TextField titleField;

    @FXML
    private TextField authorField;

    @FXML
    private ComboBox<String> languageComboBox;
    @FXML
    private ComboBox<String> categoryComboBox;

    @FXML
    private DatePicker publicationDateField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField quantityField;

    @FXML
    private TextField summaryField;

    @FXML
    private TextField pictureField;

    private Book editedBook;
    private BookService bookService;
    private String[] languages = {"Francais", "Anglais", "Arabe","Espagnole"};
    private String[] categories = {"Histoire", "Science-fiction", "Réalité","littérature"};

    // ... (autres méthodes)

    @FXML
    private void importImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.gif"));

        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);

        if (selectedFiles != null && !selectedFiles.isEmpty()) {
            // On récupère le premier fichier sélectionné
            File selectedFile = selectedFiles.get(0);

            // On met le nom du fichier dans le champ pictureField
            pictureField.setText(selectedFile.getName());
        }
    }
    public void setLists(){
        ObservableList<String> languageList = FXCollections.observableArrayList(languages);
        languageComboBox.setItems(languageList);

        ObservableList<String> categoryList = FXCollections.observableArrayList(categories);
        categoryComboBox.setItems(categoryList);
    }
    public EditBookPopupController() {
        this.bookService = new BookService();
    }

    public void initialize(Book book) {
        setLists();
        // Initialisez le formulaire avec les détails du livre
        editedBook = book;
        titleField.setText(book.getTitle());
        authorField.setText(book.getAuthor());
        languageComboBox.setValue(book.getLanguage());
        publicationDateField.setValue(book.getDatePublication().toLocalDate());
        priceField.setText(String.valueOf(book.getPrice()));
        quantityField.setText(String.valueOf(book.getQuantity()));
        summaryField.setText(book.getSummary());
        pictureField.setText(book.getPicture());
        // ... initialisez d'autres champs du formulaire
    }

    @FXML
    private void saveChanges() {
        // Récupérez les nouvelles valeurs depuis les champs du formulaire
        String newTitle = titleField.getText();
        String newAuthor = authorField.getText();
        String newLanguage = languageComboBox.getValue();
        LocalDate publicationDateValue = publicationDateField.getValue();
        int newPrice = parseTextField(priceField, "Prix");
        int newQuantity = parseTextField(quantityField, "Quantité");
        String newSummary = summaryField.getText();
        String newPicture = pictureField.getText();
        if (newTitle.isEmpty() || newAuthor.isEmpty() || newLanguage.isEmpty() || publicationDateValue == null
                || newPrice < 0 || newQuantity < 0) {
            showAlert("Champs obligatoires", "Veuillez remplir tous les champs obligatoires.");
            return; // Arrêtez l'exécution si les champs obligatoires ne sont pas remplis
        }
        // Appliquez les modifications au livre
        editedBook.setTitle(newTitle);
        editedBook.setAuthor(newAuthor);
        editedBook.setLanguage(newLanguage);
        editedBook.setDatePublication(Date.valueOf(publicationDateValue));
        editedBook.setPrice(newPrice);
        editedBook.setQuantity(newQuantity);
        editedBook.setSummary(newSummary);
        editedBook.setPicture(newPicture);

        // Enregistrez les modifications dans la base de données
        try {
            bookService.update(editedBook);
            showAlert("Modification réussie", "Les modifications ont été enregistrées avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erreur lors de la modification", "Une erreur s'est produite lors de l'enregistrement des modifications.");
        }

        // Fermez la fenêtre contextuelle après l'édition/modification
        Stage stage = (Stage) titleField.getScene().getWindow();
        stage.close();
    }
    private int parseTextField(TextField textField, String fieldName) {
        try {
            return Integer.parseInt(textField.getText());
        } catch (NumberFormatException e) {
            showAlert("Erreur de saisie", "Veuillez saisir un nombre valide pour le champ " + fieldName + ".");
            return -1; // Vous pouvez choisir une valeur par défaut ou retourner une valeur spéciale pour indiquer une erreur
        }
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
