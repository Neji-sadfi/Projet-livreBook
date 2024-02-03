package com.example.livrebook.gui.book.frontOffice;

import com.example.livrebook.model.book.*;
import com.example.livrebook.service.bookServices.BookQuantityService;
import com.example.livrebook.service.bookServices.BookService;
import com.example.livrebook.service.bookServices.OrderLineService;
import com.example.livrebook.service.bookServices.OrdersService;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class BookClientController implements Initializable {
    @FXML
    private Label card_delivery;

    @FXML
    private Label card_total;

    @FXML
    private TableView<BookQuantityDetails> card_tableView;

    @FXML
    private TableColumn<BookQuantityDetails, String> col_livreId;

    @FXML
    private TableColumn<BookQuantityDetails, Integer> col_quantity;

    @FXML
    private TableColumn<BookQuantityDetails, Integer> col_price;

    @FXML
    private TableColumn<BookQuantityDetails, Integer> col_totPrice;
    @FXML
    private Button delete_btn;

    @FXML
    private AnchorPane main_form;

    @FXML
    private AnchorPane main_pane;

    @FXML
    private ScrollPane main_scrollPane;

    @FXML
    private Button order_btn;

    @FXML
    private Button paiement_btn;
    @FXML
    private GridPane gridPane;
    private AtomicInteger totalAmount = new AtomicInteger(0);

    @FXML
    private ComboBox<String> languageComboBox;
    @FXML
    private ComboBox<String> categoryComboBox;
    @FXML
    private TextField searchTextField;
    private String[] languages = {"Francais", "Anglais", "Arabe","Espagnole"};
    private String[] categories = {"Histoire", "Science-fiction", "Réalité","littérature"};

    @FXML
    public List<Book> searchBooks() {
        String title = searchTextField.getText();
        String category = categoryComboBox.getValue();
        String language = languageComboBox.getValue();

        try {
            List<Book> searchResult = bookService.searchBooksClient(title, category, language);
            return searchResult;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookList;
    }

    public void setLists(){
        ObservableList<String> languageList = FXCollections.observableArrayList(languages);
        languageList.add(null);  // Ajouter null à la liste
        languageComboBox.setItems(languageList);

        ObservableList<String> categoryList = FXCollections.observableArrayList(categories);
        categoryList.add(null);  // Ajouter null à la liste
        categoryComboBox.setItems(categoryList);
    }
    public void resetTotalAmount(){
    totalAmount.set(0);
    card_total.setText("--");
    card_delivery.setText("--");
}
    private ObservableList<Book> bookList;

    private BookService bookService;

    private BookQuantityService bookQuantityService;
    public BookClientController(){
        bookService = new BookService();
        bookQuantityService = new BookQuantityService();
    }

    public ObservableList<Book> getBooksList() {
        if (searchTextField.getText()== null && categoryComboBox.getValue() == null && languageComboBox.getValue() == null) {
            try {
                List<Book> books = bookService.selectAll();
                return FXCollections.observableArrayList(books);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            List<Book> books = searchBooks();
        return FXCollections.observableArrayList(books);
        }
    }
    public ObservableList<BookQuantityDetails> getQuantitiesBooksList() {
        try {
            List<BookQuantityDetails> books = bookQuantityService.getAllBooksQuantities();
            return FXCollections.observableArrayList(books);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void displayCard(){
        ObservableList<BookQuantityDetails> books = getQuantitiesBooksList();
        col_livreId.setCellValueFactory(new PropertyValueFactory<>("title"));
        col_quantity.setCellValueFactory(new PropertyValueFactory<>("bookQuantity"));
        col_price.setCellValueFactory(new PropertyValueFactory<>("price"));

        col_totPrice.setCellValueFactory(cellData -> {
            int quantity = cellData.getValue().getBookQuantity();
            int price = cellData.getValue().getPrice();
            int totPrice = quantity * price;
            totalAmount.addAndGet(totPrice);
            updateTotalLabel();
            return new ReadOnlyObjectWrapper<>(totPrice);
        });

        card_tableView.setItems(books);

    }
    public void updateTotalLabel() {
        card_total.setText( calculateTotalAmount() + " Dt");
        if (calculateTotalAmount() >= 150) {
            card_delivery.setText("Gratuite");
        } else if(calculateTotalAmount() == 0) {
            card_delivery.setText("--");
        }
         else {
            card_delivery.setText("7Dt");
        }
    }
    public void displayBooks(){
        gridPane.getChildren().clear();
        if (bookList == null) {
            bookList = FXCollections.observableArrayList(); // Initialisez la liste si elle est null
        } else {
            bookList.clear(); // Sinon, effacez son contenu
        }

        bookList.addAll(getBooksList());
        int row = 0;
        int column = 0;
        gridPane.getRowConstraints().clear();
        gridPane.getColumnConstraints().clear();

        for (int q = 0; q < bookList.size(); q++){
            try {
                FXMLLoader load = new FXMLLoader();
                load.setLocation(getClass().getResource("/com/example/livrebook/book/front-office/card.fxml"));
                AnchorPane pane = load.load();
                CardController cardController = load.getController();
                cardController.setBook(bookList.get(q));
                cardController.setBookClientController(this);

                if (column == 3) {
                    column = 0;
                    row += 1;
                }

                // Set row and column constraints
                GridPane.setRowIndex(pane, row);
                GridPane.setColumnIndex(pane, column);

                gridPane.getChildren().add(pane);
                column++;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void handleDeleteButtonClick() {
        // Obtenez l'élément sélectionné dans le TableView
        BookQuantityDetails selectedBook = card_tableView.getSelectionModel().getSelectedItem();

        // Vérifiez si un élément est sélectionné
        if (selectedBook != null) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation de suppression");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Voulez-vous vraiment supprimer ce livre du panier?");

            // Obtenez la réponse de l'utilisateur
            Optional<ButtonType> result = confirmationAlert.showAndWait();

            // Si l'utilisateur a cliqué sur le bouton OK, supprimez l'élément du panier
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    int price = selectedBook.getPrice();
                    bookQuantityService.delete(selectedBook.getBookQuantityId());

                    displayCard();

                    totalAmount.set(calculateTotalAmount()); // Update total amount

                    updateTotalLabel(); // Update label

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                // Affichez un message ou une alerte indiquant à l'utilisateur de sélectionner un élément
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Avertissement");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez sélectionner un élément à supprimer.");
                alert.showAndWait();
            }
        }
    }
    public int calculateTotalAmount() {
        int total = 0;

        for (BookQuantityDetails item : card_tableView.getItems()) {
            int quantity = item.getBookQuantity();
            int itemPrice = item.getPrice();
            total += quantity * itemPrice;
        }

        return total;
    }

    public void payer() {
        if (totalAmount.get() == 0) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erreur de paiement");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Veuillez remplir votre panier pour passer au paiement.");
            errorAlert.showAndWait();
        } else {
            try {
                int currentTotalAmount = calculateTotalAmount();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/livrebook/book/front-office/payment_popup.fxml"));
                AnchorPane paymentPopup = loader.load();
                PaymentPopupController paymentPopupController = loader.getController();
                paymentPopupController.setTotalAmount(currentTotalAmount);
                paymentPopupController.setMainController(this);

                Stage paymentStage = new Stage();
                paymentStage.setTitle("Paiement");
                paymentStage.initModality(Modality.WINDOW_MODAL);
                paymentStage.initOwner(main_pane.getScene().getWindow());

                Scene scene = new Scene(paymentPopup);
                paymentStage.setScene(scene);

                paymentStage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void commander(){
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation de commande");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Voulez-vous vraiment commander ces articles et payer à la livraison?");

        // Obtenez la réponse de l'utilisateur
        Optional<ButtonType> result = confirmationAlert.showAndWait();

        // Si l'utilisateur a cliqué sur le bouton OK, supprimez l'élément du panier
        if (result.isPresent() && result.get() == ButtonType.OK) {
            OrdersService ordersService = new OrdersService();
            BookQuantityService bookQuantityService = new BookQuantityService();
            OrderLineService orderLineService = new OrderLineService();
            Orders order = new Orders();
            order.setPaymentMethod("à la livraison");
            order.setUserId(1);
            order.setOrderDate(new Date(System.currentTimeMillis()));
            order.setTotalPrice(totalAmount.get());
            order.setOrderStatus("En attente");
            int id_order = 0;
            try {
                id_order = ordersService.insertAndGetGeneratedKey(order);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
// insert to order and orderLine
            List<BookQuantity> books = null;
            try {
                books = bookQuantityService.selectAll();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            for (BookQuantity bk : books) {
                OrderLine orderLine = new OrderLine();
                orderLine.setQuantity(bk.getbookQuantity());
                orderLine.setBookId(bk.getBookId());
                orderLine.setIdOrder(id_order);
                try {
                    orderLineService.insert(orderLine);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            // Call the service to delete all elements (clear the table)

            try {
                bookQuantityService.deleteAll();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Display a success alert
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Confirmation de commande");
            successAlert.setHeaderText(null);
            successAlert.setContentText("La commande été effectué avec succès!");
            successAlert.showAndWait();
            displayCard();
            resetTotalAmount();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayBooks();
        setLists();
        displayCard();
        updateTotalLabel();
    }


}
