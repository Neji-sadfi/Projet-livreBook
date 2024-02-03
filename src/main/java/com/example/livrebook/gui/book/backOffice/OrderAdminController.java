package com.example.livrebook.gui.book.backOffice;

import com.example.livrebook.model.book.OrderTableData;
import com.example.livrebook.model.book.Orders;
import com.example.livrebook.model.user.User;
import com.example.livrebook.service.bookServices.OrdersService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OrderAdminController {

    @FXML
    private TableView<OrderTableData> orderTable;

    @FXML
    private TextField form_userName;

    @FXML
    private DatePicker form_orderDate;

    @FXML
    private TextField form_userEmail;

    @FXML
    private ComboBox<String> form_paymentMethod;

    @FXML
    private Button search_btn;

    @FXML
    private Button clear_btn;

    @FXML
    private Button delivery_btn;

    private OrdersService orderService;

    @FXML
    public void initialize() {
        this.orderService = new OrdersService();
        loadOrderData();
    }
    private void loadOrderData() {
        HashMap<Orders, User> ordersAndUsers = orderService.getOrdersAndUsers();

        ObservableList<OrderTableData> orderTableDataList = FXCollections.observableArrayList();

        for (Map.Entry<Orders, User> entry : ordersAndUsers.entrySet()) {
            Orders order = entry.getKey();
            User user = entry.getValue();

            OrderTableData orderTableData = new OrderTableData(order.getId(),
                    user.getFirst_name() + " " + user.getLast_name(),
                    user.getEmail(),
                    order.getOrderDate(),
                    order.getOrderStatus(),
                    order.getPaymentMethod(),
                    order.getTotalPrice()
            );

            orderTableDataList.add(orderTableData);
        }

        ObservableList<OrderTableData> orderData = FXCollections.observableArrayList(orderTableDataList);

        orderTable.getColumns().clear();

        orderTable.setItems(orderData);

        TableColumn<OrderTableData, String> userIdColumn = new TableColumn<>("Nom du Client");
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        orderTable.getColumns().add(userIdColumn);

        TableColumn<OrderTableData, String> emailColumn = new TableColumn<>("Email Client");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        orderTable.getColumns().add(emailColumn);

        TableColumn<OrderTableData, Date> orderDateColumn = new TableColumn<>("Date de Commande");
        orderDateColumn.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        orderTable.getColumns().add(orderDateColumn);

        TableColumn<OrderTableData, String> orderPaymentMethod = new TableColumn<>("Méthode de paiement");
        orderPaymentMethod.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        orderTable.getColumns().add(orderPaymentMethod);

        TableColumn<OrderTableData, String> orderStatusColumn = new TableColumn<>("Statut de Commande");
        orderStatusColumn.setCellValueFactory(new PropertyValueFactory<>("orderStatus"));
        orderTable.getColumns().add(orderStatusColumn);

        TableColumn<OrderTableData, Integer> totalPriceColumn = new TableColumn<>("Total Prix");
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        orderTable.getColumns().add(totalPriceColumn);
    }

    @FXML
    private void searchOrders() {
        String userName = form_userName.getText();
        LocalDate orderDate = form_orderDate.getValue();
        String userEmail = form_userEmail.getText();
        String paymentMethod = form_paymentMethod.getValue();


        HashMap<Orders, User> searchedOrdersAndUsers = orderService.searchOrders(userName, orderDate, userEmail, paymentMethod);

        fillOrderTableWithData(searchedOrdersAndUsers);
    }
    private void fillOrderTableWithData(HashMap<Orders, User> ordersAndUsers) {
        ObservableList<OrderTableData> orderTableDataList = FXCollections.observableArrayList();

        for (Map.Entry<Orders, User> entry : ordersAndUsers.entrySet()) {
            Orders order = entry.getKey();
            User user = entry.getValue();

            OrderTableData orderTableData = new OrderTableData(order.getId(),
                    user.getFirst_name() + " " + user.getLast_name(),
                    user.getEmail(),
                    order.getOrderDate(),
                    order.getOrderStatus(),
                    order.getPaymentMethod(),
                    order.getTotalPrice()
            );

            orderTableDataList.add(orderTableData);
        }

        ObservableList<OrderTableData> orderData = FXCollections.observableArrayList(orderTableDataList);

        orderTable.getColumns().clear();

        orderTable.setItems(orderData);

        TableColumn<OrderTableData, String> userIdColumn = new TableColumn<>("Nom du Client");
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        orderTable.getColumns().add(userIdColumn);

        TableColumn<OrderTableData, String> emailColumn = new TableColumn<>("Email Client");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        orderTable.getColumns().add(emailColumn);

        TableColumn<OrderTableData, Date> orderDateColumn = new TableColumn<>("Date de Commande");
        orderDateColumn.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        orderTable.getColumns().add(orderDateColumn);

        TableColumn<OrderTableData, String> orderPaymentMethod = new TableColumn<>("Méthode de paiement");
        orderPaymentMethod.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        orderTable.getColumns().add(orderPaymentMethod);

        TableColumn<OrderTableData, String> orderStatusColumn = new TableColumn<>("Statut de Commande");
        orderStatusColumn.setCellValueFactory(new PropertyValueFactory<>("orderStatus"));
        orderTable.getColumns().add(orderStatusColumn);

        TableColumn<OrderTableData, Integer> totalPriceColumn = new TableColumn<>("Total Prix");
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        orderTable.getColumns().add(totalPriceColumn);
    }


    @FXML
    private void clearForm() {
        form_userName.clear();
        form_orderDate.getEditor().clear();
        form_userEmail.clear();
        form_paymentMethod.getSelectionModel().clearSelection();
        loadOrderData();
    }
    @FXML
    private void deliverOrder() {
        OrderTableData selectedOrder = orderTable.getSelectionModel().getSelectedItem();

        if (selectedOrder != null) {
            Orders orderToUpdate = new Orders();
            orderToUpdate.setId(selectedOrder.getOrderId());
            orderToUpdate.setOrderStatus("En cours de livraison");
            if (!"En cours de livraison".equals(selectedOrder.getOrderStatus())) {
            try {
                boolean updateSuccess = orderService.update(orderToUpdate);

                if (updateSuccess) {
                    loadOrderData();
                    showInformationAlert("Livraison", "La commande est mise en livraison.");
                } else {
                    showErrorAlert("Erreur", "Échec de la mise à jour du statut de la commande.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showErrorAlert("Erreur", "Une erreur s'est produite lors de la livraison de la commande.");
            }
            }
            else {
                showWarningAlert("Avertissement", "La commande est déjà en cours de livraison.");
            }
        } else {
            showWarningAlert("Avertissement", "Veuillez sélectionner une commande à livrer.");
        }
    }

    private void showInformationAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showWarningAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

