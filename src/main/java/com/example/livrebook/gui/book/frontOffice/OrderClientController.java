package com.example.livrebook.gui.book.frontOffice;

import com.example.livrebook.model.book.Book;
import com.example.livrebook.model.book.OrderLine;
import com.example.livrebook.model.book.OrderLineBook;
import com.example.livrebook.model.book.Orders;
import com.example.livrebook.service.bookServices.BookService;
import com.example.livrebook.service.bookServices.OrderLineService;
import com.example.livrebook.service.bookServices.OrdersService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class OrderClientController implements Initializable {
    @FXML
    private Button cancel_btn;

    @FXML
    private Button clear_btn;

    @FXML
    private TextField form_bookName;

    @FXML
    private DatePicker form_orderDate;

    @FXML
    private AnchorPane main_pane;

    @FXML
    private Button search_btn;

    @FXML
    private TableView<OrderLineBook> orderTableView;

    @FXML
    private TableColumn<OrderLineBook, Integer> orderNumberColumn;

    @FXML
    private TableColumn<OrderLineBook, String> dateColumn;

    @FXML
    private TableColumn<OrderLineBook, String> statusColumn;

    @FXML
    private TableColumn<OrderLineBook, String> paymentMethodColumn;

    @FXML
    private TableColumn<OrderLineBook, Integer> totalPriceColumn;

    @FXML
    private TableColumn<OrderLineBook, String> bookColumn;

    @FXML
    private TableColumn<OrderLineBook, Integer> quantityColumn;

    private OrderLineService orderLineService;

    private OrdersService ordersService;

    private BookService bookService;

    public OrderClientController() {
        orderLineService = new OrderLineService();
        bookService = new BookService();
        ordersService = new OrdersService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeColumns();
        initializeTableViewEvents();
        getOrders();
    }

    private void initializeTableViewEvents() {
        orderTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                handleRowClick(newSelection);
            }
        });
    }

    @FXML
    private void handleRowClick(OrderLineBook selectedOrder) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/livrebook/book/front-office/OrderDetailsPopup.fxml"));
            Parent root = loader.load();

            OrderDetailsPopupController popupController = loader.getController();
            Stage popupStage = new Stage();
            popupController.setStage(popupStage);
            popupController.initData(selectedOrder);

            Scene scene = new Scene(root);
            popupStage.setScene(scene);
            popupStage.setTitle("Détailles de commande");
            popupStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeColumns() {
        orderNumberColumn.setCellValueFactory((new PropertyValueFactory<>("orderId")));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("orderStatus"));
        paymentMethodColumn.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
    }

    @FXML
    private void getOrders() {
        try {
            List<OrderLineBook> orderLineBooks = orderLineService.getAllUserOrders(1);
           /* for (OrderLineBook order : orderLineBooks){
                order.setOrderId(order.getOrderId() + new Random().nextInt(100));
            }*/
            orderTableView.getItems().setAll(orderLineBooks);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cancelOrder(ActionEvent actionEvent) {
        OrderLineBook selectedOrder = orderTableView.getSelectionModel().getSelectedItem();
        String orderStatus = selectedOrder.getOrderStatus();
        if ("En cours de livraison".equals(orderStatus)) {
            showAlert("Commande déjà en livraison", Alert.AlertType.WARNING);
        } else if ("Annulé".equals(orderStatus)) {
            showAlert("Commande déjà annulée", Alert.AlertType.WARNING);
        } else {
            // Demandez confirmation pour annuler la commande
            boolean confirmed = showConfirmationAlert("Voulez-vous vraiment annuler cette commande ?");

            // Si l'utilisateur confirme, mettez à jour le statut de la commande
            if (confirmed) {
                Orders order = new Orders();
                order.setId(selectedOrder.getOrderId());
                order.setOrderStatus("Annulé");
                try {
                    ordersService.update(order);
                    getOrders();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                showAlert("Commande annulée avec succès", Alert.AlertType.INFORMATION);
            }
        }
    }
    private void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Méthode pour afficher une alerte de confirmation
    private boolean showConfirmationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Ajoutez les boutons OK et Annuler à l'alerte
        ButtonType okButton = new ButtonType("OK");
        ButtonType cancelButton = new ButtonType("Annuler", ButtonType.CANCEL.getButtonData());
        alert.getButtonTypes().setAll(okButton, cancelButton);

        // Affichez l'alerte et attendez la réponse de l'utilisateur
        return alert.showAndWait().orElse(ButtonType.CANCEL) == okButton;
    }

}
