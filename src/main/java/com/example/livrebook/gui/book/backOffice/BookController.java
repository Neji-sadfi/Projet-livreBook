package com.example.livrebook.gui.book.backOffice;

import com.example.livrebook.model.book.Book;
import com.example.livrebook.service.bookServices.BookService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.stage.Stage;


public class BookController implements Initializable {

    @FXML
    private Button add_btn;

    @FXML
    private TableView<Book> bookTable;

    @FXML
    private TableColumn<Book, String> book_author;

    @FXML
    private TableColumn<Book, String> book_category;

    @FXML
    private TableColumn<Book, Date> book_date;


    @FXML
    private TableColumn<Book, String> book_language;

    @FXML
    private TableColumn<Book, Integer> book_price;

    @FXML
    private TableColumn<Book, Integer> book_quantity;

    @FXML
    private TableColumn<Book, String> book_summary;

    @FXML
    private TableColumn<Book, String> book_title;

    @FXML
    private Button clear_btn;

    @FXML
    private Button delete_btn;

    @FXML
    private TableColumn<Book, Integer> id_book;

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
    private TextField form_title;

    private String[] languages = {"Francais", "Anglais", "Arabe","Espagnole"};
    private String[] categories = {"Histoire", "Science-fiction", "Réalité","littérature"};
    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<String> languageComboBox;
    private static BookService bookService;
public BookController(){
    this.bookService = new BookService();
}
    public void setLists(){
        ObservableList<String> languageList = FXCollections.observableArrayList(languages);
        languagesL.setItems(languageList);

        ObservableList<String> categoryList = FXCollections.observableArrayList(categories);
        categoriesL.setItems(categoryList);
    }
    @FXML
    private void deleteBook() {
        Book selectedBook = bookTable.getSelectionModel().getSelectedItem();

        if (selectedBook != null) {
            // Afficher une boîte de dialogue de confirmation
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText("Supprimer le livre ?");
            alert.setContentText("Voulez-vous vraiment supprimer le livre sélectionné ?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Supprimer le livre de la base de données
                try {
                    bookService.delete(selectedBook.getId());
                    // Rafraîchir la table des livres après la suppression
                    setBookTable();
                } catch (SQLException e) {
                    e.printStackTrace(); // Gérez l'exception selon vos besoins
                }
            }
        } else {
            // Aucun livre sélectionné
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un livre à supprimer.");
            alert.showAndWait();
        }
    }
    @FXML
    private void editBook() {
        Book selectedBook = bookTable.getSelectionModel().getSelectedItem();

        if (selectedBook != null) {
            // Chargez la fenêtre contextuelle d'édition/modification du livre
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/livrebook/book/back-office/EditBookPopup.fxml"));
            Parent root;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace(); // Gérez l'exception selon vos besoins
                return;
            }

            // Obtenez le contrôleur de la fenêtre contextuelle
            EditBookPopupController editController = loader.getController();

            // Initialisez le contrôleur avec le livre sélectionné
            editController.initialize(selectedBook);

            // Créez une nouvelle scène et configurez-la avec la fenêtre contextuelle
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Modification du livre");
            stage.setScene(scene);

            // Affichez la fenêtre contextuelle
            stage.showAndWait();

            // Rafraîchissez la table des livres après l'édition/modification
            setBookTable();
        } else {
            // Aucun livre sélectionné
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un livre à éditer.");
            alert.showAndWait();
        }
    }

    public ObservableList<Book> getObservableBookList() {
        return FXCollections.observableArrayList(BookList());
    }
    public List<Book> BookList() {
        List<Book> lb;

        try {
            lb = bookService.selectAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lb;
    }
    public void setBookTable(){
        ObservableList<Book> lb = getObservableBookList();
        book_author.setCellValueFactory(new PropertyValueFactory<Book,String>("author"));
        book_category.setCellValueFactory(new PropertyValueFactory<Book,String>("category"));
        book_date.setCellValueFactory(new PropertyValueFactory<Book,Date>("datePublication"));
        book_language.setCellValueFactory(new PropertyValueFactory<Book,String>("language"));
        book_price.setCellValueFactory(new PropertyValueFactory<Book,Integer>("price"));
        book_quantity.setCellValueFactory(new PropertyValueFactory<Book,Integer>("quantity"));
        book_summary.setCellValueFactory(new PropertyValueFactory<Book,String>("summary"));
        book_title.setCellValueFactory(new PropertyValueFactory<Book,String>("title"));
        bookTable.setItems(lb);

    }

    @FXML
    private void openAddBookPopup() {
        // Chargez la fenêtre contextuelle du formulaire d'ajout
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/livrebook/book/back-office/AddBookPopup.fxml"));
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace(); // Gérez l'exception selon vos besoins
            return;
        }

        // Obtenez le contrôleur de la fenêtre contextuelle
        AddBookPopupController addController = loader.getController();

        // Créez une nouvelle scène et configurez-la avec la fenêtre contextuelle
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Ajout de livre");
        stage.setScene(scene);

        // Affichez la fenêtre contextuelle
        stage.showAndWait();

        // Rafraîchissez la table des livres après l'ajout
        setBookTable();
    }
    @FXML
    private void clearForm() {
        form_actor.clear();
        form_date.getEditor().clear(); // Clear the text field of DatePicker
        form_price.clear();
        form_title.clear();

        // Clear the selected item in ComboBoxes
        languagesL.getSelectionModel().clearSelection();
        categoriesL.getSelectionModel().clearSelection();
        searchBooks();
    }
    @FXML
    private void searchBooks() {
        // Récupérez les valeurs des champs de recherche
        String title = form_title.getText();
        String author = form_actor.getText();
        String category = categoriesL.getValue();
        String language = languagesL.getValue();
        LocalDate date = form_date.getValue();
        Integer price = (form_price.getText() != null && !form_price.getText().isEmpty()) ? Integer.parseInt(form_price.getText()) : null;
        System.out.println(title + author+category+ language + date + price);
        // Utilisez la méthode de recherche du service
        try {
            List<Book> searchResult = bookService.searchBooks(title, author, category, language, date, price);
            System.out.println(searchResult.toString());
            // Mettez à jour la table des livres avec les résultats de la recherche
            bookTable.setItems(FXCollections.observableArrayList(searchResult));
        } catch (SQLException e) {
            e.printStackTrace(); // Gérez l'exception selon vos besoins
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setLists();
    setBookTable();
    }
}
