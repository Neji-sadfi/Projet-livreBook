package com.example.livrebook.gui.delivery;

import com.example.livrebook.model.book.Orders;
import com.example.livrebook.model.delivery.Delivery;
import com.example.livrebook.model.deliveryOrder.DeliveryOrder;
import com.example.livrebook.service.bookServices.OrdersService;
import com.example.livrebook.service.deliveryOrderServices.DeliveryOrderServices;
import com.example.livrebook.service.deliveryServices.DeliveryService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;

public class DeliveryController implements Initializable {
    private DeliveryService deliveryService;
    private DeliveryOrderServices deliveryOrderServices;
    private Delivery delivery;

    private Orders orders;
    private OrdersService ordersService;
    public DeliveryController(){
        deliveryService = new DeliveryService();
        deliveryOrderServices = new DeliveryOrderServices();

    }

    @FXML
    private ListView<DeliveryOrder> _idDelivredTableView;

    @FXML
    private Button _idAddButton;

    @FXML
    private ListView<DeliveryOrder> _idListeView;

    @FXML
    private Button _idRemoveButton;

    @FXML
    private TextField idLiv;

    @FXML
    private TableView<DeliveryOrder> _idTableView;

    @FXML
    private TableColumn<DeliveryOrder, String> idAddress;

    @FXML
    private TableColumn<DeliveryOrder, Integer> idLivraison;

    @FXML
    private TableColumn<DeliveryOrder, String> idOrderBy;

    @FXML
    private TableColumn<DeliveryOrder, String> idStatus;

    @FXML
    private TableColumn<DeliveryOrder, Integer> idTotal;

    @FXML
    private TableColumn<DeliveryOrder, String> idPaymentMethod;

    @FXML
    private TableColumn<DeliveryOrder, String> idPaymentStatus;

    @FXML
    private Button _idDeliveredButton;

    private ObjectProperty<DeliveryOrder> selectedLivraison = new SimpleObjectProperty<>();

    public ObjectProperty<DeliveryOrder> selectedLivraisonProperty() {
        return selectedLivraison;
    }



    @FXML
    void onAddDelivery(ActionEvent event) {
        // Get the selected Livraison from the Tabl eView
        DeliveryOrder selectedDelivery = _idTableView.getSelectionModel().getSelectedItem();

        if (selectedDelivery != null) {
            // Add the selected Livraison to the ListView
            _idListeView.getItems().add(selectedDelivery);
            System.out.println("prrrrrr");
            selectedDelivery.setPending(true);
            try {
                deliveryOrderServices.update(selectedDelivery);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


            System.out.println(selectedDelivery.isPending());
            // Optionally, you can update the display of the ListView
//            showLivraisonById();
//            _idTableView.getItems().remove(selectedLivraison);

        }
    }


    @FXML
    void onRemoveDelivery(ActionEvent event) {
        DeliveryOrder selectedDelivery = _idTableView.getSelectionModel().getSelectedItem();

        // Create the confirmation alert
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Confirm Deletion");
        alert.setContentText("Are you sure you want to delete this delivery?");

        // Show the alert and wait for user response
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // User clicked OK, proceed with deletion
                try {
                    deliveryOrderServices.deleteDeliveryOrder(selectedDelivery.getIdDelivery(),selectedDelivery.getIdOrder());
//                    deliveryOrderServices.delete(selectedDelivery.getIdOrder());
                    showLivraison();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                // User clicked Cancel or closed the dialog, do nothing
            }
        });
    }

    @FXML
    void onAddDelivred(ActionEvent event) {
// Get the selected Livraison from the Tabl eView
        DeliveryOrder selectedDelivery = _idListeView.getSelectionModel().getSelectedItem();

        if (selectedDelivery != null) {
            // Add the selected Livraison to the ListView
//            _idListViewToDeliver.getItems().add(selectedDelivery);
            System.out.println("prrrrrr");
            selectedDelivery.setStatus("delivred");
            try {
                deliveryOrderServices.update(selectedDelivery);
                showLivraisonByStatus();
                showLivraisonById();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<DeliveryOrder> getIntilaizationSelectedLivraison() {
        try {
            return deliveryOrderServices.selectWherePending();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public ObservableList<DeliveryOrder> getObservableLivraison() {
        return FXCollections.observableArrayList(getIntilaizationSelectedLivraison());
    }
    public ObservableList<DeliveryOrder> showLivraisonById() {
        ObservableList<DeliveryOrder> list = getObservableLivraison();
        _idListeView.setItems(list);
        return list;

    }




    public List<DeliveryOrder> getIntilaizationSelected() {
        try {
            return deliveryOrderServices.selectWhereStatus();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public ObservableList<DeliveryOrder> getObservableLivraisonStatus() {
        return FXCollections.observableArrayList(getIntilaizationSelected());
    }
    public ObservableList<DeliveryOrder> showLivraisonByStatus() {
        ObservableList<DeliveryOrder> list = getObservableLivraisonStatus();
//        idLivraison.setCellValueFactory(new PropertyValueFactory<Livraison,Integer>("id"));
        idStatus.setCellValueFactory(new PropertyValueFactory<DeliveryOrder,String>("status"));
        idAddress.setCellValueFactory(new PropertyValueFactory<DeliveryOrder,String>("adress"));
        idOrderBy.setCellValueFactory(new PropertyValueFactory<DeliveryOrder,String>("first_name"));
        _idDelivredTableView.setItems(list);
        return list;

    }








    public List<DeliveryOrder> getLivraisonList() {
        try {
            return deliveryOrderServices.selectAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
//    public List<Livraison> getOrdersListe() {
//        try {
//            return livraisonService.selectAll();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }


    public ObservableList<DeliveryOrder> getObservableLivraisonList() {
        return FXCollections.observableArrayList(getLivraisonList());
    }
//    public ObservableList<Livraison> getObservableOrdersList() {
//        return FXCollections.observableArrayList(getOrdersListe());
//    }

    public ObservableList<DeliveryOrder> showLivraison() {
        ObservableList<DeliveryOrder> list = getObservableLivraisonList();
//        ObservableList<Orders> listOrders = getObservableOrdersList();
//        idLivraison.setCellValueFactory(new PropertyValueFactory<Livraison,Integer>("id"));
        idStatus.setCellValueFactory(new PropertyValueFactory<DeliveryOrder,String>("status"));
        idAddress.setCellValueFactory(new PropertyValueFactory<DeliveryOrder,String>("adress"));
        idOrderBy.setCellValueFactory(new PropertyValueFactory<DeliveryOrder,String>("first_name"));
        idPaymentStatus.setCellValueFactory(new PropertyValueFactory<DeliveryOrder,String>("orderStatus"));
        idPaymentMethod.setCellValueFactory(new PropertyValueFactory<DeliveryOrder,String>("paymentMethod"));
        idTotal.setCellValueFactory(new PropertyValueFactory<DeliveryOrder,Integer>("totalPrice"));
        _idTableView.setItems(list);
//        _idTableView.setItems(listOrders);
        return list;
    }
//    public ObservableList<Livraison> showOrders() {
////        ObservableList<Livraison> list = getObservableLivraisonList();
//        ObservableList<Livraison> listOrders = getObservableOrdersList();
////        idLivraison.setCellValueFactory(new PropertyValueFactory<Livraison,Integer>("id"));
////        idStatus.setCellValueFactory(new PropertyValueFactory<Livraison,String>("status"));
////        idAddress.setCellValueFactory(new PropertyValueFactory<Livraison,String>("adress"));
////        idOrderBy.setCellValueFactory(new PropertyValueFactory<Livraison,Integer>("orderId"));
//        idPaymentStatus.setCellValueFactory(new PropertyValueFactory<Livraison,String>("payment status"));
//        idPaymentMethod.setCellValueFactory(new PropertyValueFactory<Livraison,String>("payment method"));
//        idTotal.setCellValueFactory(new PropertyValueFactory<Livraison,Integer>("Total"));
//        _idTableView.setItems(listOrders);
////        _idTableView.setItems(listOrders);
//        return listOrders;
//    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showLivraison();
//        initializeTableView();
//        getIntilaizationSelectedLivraison();
        showLivraisonById();
        showLivraisonByStatus();
//        showOrders();
    }
}
