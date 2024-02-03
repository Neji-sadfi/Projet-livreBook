package com.example.livrebook.gui.book.frontOffice;

import com.example.livrebook.model.book.Book;
import com.example.livrebook.model.book.OrderLine;
import com.example.livrebook.model.book.OrderLineBook;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Map;
import java.util.Random;

public class OrderDetailsPopupController {
    @FXML
    private Label orderNumberLabel;

    @FXML
    private Label orderDateLabel;

    @FXML
    private Label orderStatusLabel;

    @FXML
    private Label paymentMethodLabel;

    @FXML
    private Label totalPriceLabel;

    @FXML
    private VBox booksDetailsVBox;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initData(OrderLineBook selectedOrder) {
        int orderId = selectedOrder.getOrderId();
        orderNumberLabel.setText("Numéro de commande: " + orderId );
        orderDateLabel.setText("Date de la commande: " + selectedOrder.getOrderDate().toString());
        orderStatusLabel.setText("Statut de commande: " + selectedOrder.getOrderStatus());
        paymentMethodLabel.setText("Méthode de paiement: " + selectedOrder.getPaymentMethod());
        totalPriceLabel.setText("Prix totale: " + selectedOrder.getTotalPrice() +" Dt");

        Map<Book, OrderLine> bookQuantity = selectedOrder.getBookQuantity();

        for (Map.Entry<Book, OrderLine> entry : bookQuantity.entrySet()) {
            Book book = entry.getKey();
            OrderLine orderLine = entry.getValue();
            int quantity = orderLine.getQuantity();

            Label bookLabel = new Label("Titre du livre: " + book.getTitle());
            Label bookLabel1 = new Label("Quantité commandée: " + quantity);
            // Create an ImageView and set its properties
            ImageView bookImageView = new ImageView();
            bookImageView.setFitWidth(100); // Set the width as per your requirement
            bookImageView.setPreserveRatio(true);

            // Load the image using getResource() to obtain a correct URL
            String imagePath = "/pictures/" + book.getPicture(); // Assuming "pictures" is in the resources folder
            Image image = new Image(getClass().getResource(imagePath).toExternalForm());
            bookImageView.setImage(image);
            Label bookLabel2 = new Label("Prix: " + book.getPrice() );
            Label bookLabel3 = new Label("------------------------------------------------------------");
            // Add both Label and ImageView to the VBox
            VBox bookDetails = new VBox(bookLabel,bookLabel1, bookImageView,bookLabel2,bookLabel3);
            booksDetailsVBox.getChildren().add(bookDetails);
        }
    }

    @FXML
    private void closePopup() {
        stage.close();
    }
}