package com.example.livrebook.gui.book.backOffice;

import com.example.livrebook.model.book.Book;
import com.example.livrebook.service.bookServices.BookService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddBookPopupController implements Initializable {
    @FXML
    private AnchorPane main_pane;

    @FXML
    private Button update_btn;
    @FXML
    private ComboBox<String> languagesL;
    @FXML
    private ComboBox<String> categoriesL;

    @FXML
    private TextField form_actor;

    @FXML
    private DatePicker form_date;

    @FXML
    private TextField form_price;

    @FXML
    private TextField form_quantity;

    @FXML
    private TextField form_summary;
    @FXML
    private ImageView book_imageView;
    @FXML
    private Button add_btn;

    @FXML
    private TextField form_title;

    private String[] languages = {"Francais", "Anglais", "Arabe","Espagnole"};
    private String[] categories = {"Histoire", "Science-fiction", "Réalité","littérature"};
    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<String> languageComboBox;
    private static BookService bookService;
    public AddBookPopupController(){
        bookService = new BookService();
    }
    @FXML
    private String importImage(ActionEvent event) {
        String fileName = "";
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif")
        );

        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());


            book_imageView.setImage(image);
            book_imageView.setPreserveRatio(false);
            book_imageView.setFitWidth(book_imageView.getFitWidth());
            book_imageView.setFitHeight(book_imageView.getFitHeight());

            fileName = selectedFile.getName();
        }
        System.out.println(fileName);
        return fileName;
    }
    @FXML
    private void insertBook() {
        String author = form_actor.getText();
        String category = categoriesL.getValue();
        LocalDate dateValue = form_date.getValue();
        String language = languagesL.getValue();
        int price = parseTextField(form_price, "Prix");
        int quantity = parseTextField(form_quantity, "Quantité");
        String summary = form_summary.getText();
        String title = form_title.getText();
        String pictureFileName = getPictureFileName();


        if (title.isEmpty() || author.isEmpty() || category == null || dateValue == null || pictureFileName.isEmpty()
                || language == null || price < 0 || quantity < 0) {
            showAlert("Champs obligatoires", "Veuillez remplir tous les champs obligatoires.");
            return;
        }

        else{

            Book newBook = new Book();
            newBook.setAuthor(author);
            newBook.setCategory(category);
            newBook.setDatePublication(Date.valueOf(dateValue));
            newBook.setLanguage(language);
            newBook.setPrice(price);
            newBook.setQuantity(quantity);
            newBook.setSummary(summary);
            newBook.setTitle(title);
            newBook.setPicture(pictureFileName);
            try {
                bookService.insert(newBook);
                Stage stage = (Stage) form_actor.getScene().getWindow();

                stage.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
    private int parseTextField(TextField textField, String fieldName) {
        try {
            return Integer.parseInt(textField.getText());
        } catch (NumberFormatException e) {
            showAlert("Erreur de saisie", "Veuillez saisir un nombre valide pour le champ " + fieldName + ".");
            return -1;
        }
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private String getPictureFileName() {
        Image image = book_imageView.getImage();

        String imageUrl = image.getUrl();
        int lastSlashIndex = imageUrl.lastIndexOf("/");
        if (lastSlashIndex >= 0 && lastSlashIndex < imageUrl.length() - 1) {
            System.out.println(imageUrl.substring(lastSlashIndex + 1));
            return imageUrl.substring(lastSlashIndex + 1);
        } else {
            return "";
        }
    }
    public void setLists(){
        ObservableList<String> languageList = FXCollections.observableArrayList(languages);
        languagesL.setItems(languageList);

        ObservableList<String> categoryList = FXCollections.observableArrayList(categories);
        categoriesL.setItems(categoryList);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
setLists();
    }
}
