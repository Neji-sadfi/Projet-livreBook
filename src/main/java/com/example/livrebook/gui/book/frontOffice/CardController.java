package com.example.livrebook.gui.book.frontOffice;

import com.example.livrebook.model.book.Book;
import com.example.livrebook.model.book.BookQuantity;
import com.example.livrebook.service.bookServices.BookQuantityService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CardController implements Initializable {
    @FXML
    private Button add_btn;

    @FXML
    private ImageView book_img;

    @FXML
    private Label book_name;

    @FXML
    private Label book_price;

    @FXML
    private Spinner<Integer> book_spinner;

    @FXML
    private AnchorPane card_form;
    private Book book;
    private String img;

    private SpinnerValueFactory<Integer> spin;
    private int qty;
    private BookClientController bookClientController;

    public void setBookClientController(BookClientController bookClientController) {
        this.bookClientController = bookClientController;
    }
    public void setQuantity(){
        spin = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,100,0);
        book_spinner.setValueFactory(spin);
    }
    public void addBtn(){
        qty = book_spinner.getValue();
        int bookQuantity = book.getQuantity();
        if (qty <= 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Message d'erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez entrer une quantité valide");
            alert.showAndWait();
        } else if (bookQuantity < qty) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Message d'erreur");
            alert.setHeaderText(null);
            alert.setContentText("Desolé la quantité demandé n'est pas disponible");
            alert.showAndWait();
        }
        else { // Ajouter le produit avec la quantité dans la base de données
            BookQuantityService bookQuantityService = new BookQuantityService();
            BookQuantity newBookQuantity = new BookQuantity();
            newBookQuantity.setBookId(book.getId());
            newBookQuantity.setbookQuantity(qty);

            try {
                bookQuantityService.insert(newBookQuantity);
                spin.setValue(0);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (bookClientController != null) {
            bookClientController.displayCard(); // Appel de la méthode de mise à jour du panier
        }
    }
    public void setBook(Book book){
        this.book = book;
        book_name.setText(book.getTitle());
        book_price.setText(book.getPrice()+" Dt");
        String imagePath = book.getPicture();
        Image image = new Image(getClass().getResourceAsStream("/pictures/" + imagePath));
        book_img.setImage(image);
        book_img.setPreserveRatio(false); // Désactiver la préservation du ratio
        book_img.setFitWidth(book_img.getFitWidth()); // Ajuster la largeur
        book_img.setFitHeight(book_img.getFitHeight());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setQuantity();
    }
}
