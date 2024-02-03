package com.example.livrebook.gui.book.frontOffice;

import com.example.livrebook.model.book.BookQuantity;
import com.example.livrebook.model.book.OrderLine;
import com.example.livrebook.model.book.Orders;
import com.example.livrebook.model.user.Account;
import com.example.livrebook.service.bookServices.AccountService;
import com.example.livrebook.service.bookServices.BookQuantityService;
import com.example.livrebook.service.bookServices.OrderLineService;
import com.example.livrebook.service.bookServices.OrdersService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class PaymentPopupController implements Initializable {
    private int totalAmount;
    @FXML
    private TextField accountNumberField;

    @FXML
    private TextField securityCodeField;

    @FXML
    private Button confirmPaymentButton;
    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
        System.out.println(totalAmount);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        confirmPaymentButton.setOnAction(event -> confirmPayment());
    }
    private BookClientController mainController; // Reference to the main controller

    // Getter and setter for mainController
    public void setMainController(BookClientController mainController) {
        this.mainController = mainController;
    }
    private void confirmPayment() {
        String accountNumber = accountNumberField.getText();
        String securityCode = securityCodeField.getText();

        // Retrieve the account for userId 1
        AccountService accountService = new AccountService();
        try {
            Account userAccount = accountService.getAccountByUserId(1);

            // Check if the account details are correct
            if (userAccount != null && userAccount.getAccountNumber() == Integer.parseInt(accountNumber)
                    && userAccount.getCode() == Integer.parseInt(securityCode)
                    && userAccount.getAmount() >= totalAmount) {
                OrdersService ordersService = new OrdersService();
                BookQuantityService bookQuantityService = new BookQuantityService();
                OrderLineService orderLineService = new OrderLineService();
                Orders order = new Orders();
                order.setPaymentMethod("En ligne");
                order.setUserId(1);
                order.setOrderDate(new Date(System.currentTimeMillis()));
                order.setTotalPrice(totalAmount);
                order.setOrderStatus("En attente");
                int id_order = ordersService.insertAndGetGeneratedKey(order);
// insert to order and orderLine
                List<BookQuantity> books = bookQuantityService.selectAll();
                for (BookQuantity bk : books){
                    OrderLine orderLine = new OrderLine();
                    orderLine.setQuantity(bk.getbookQuantity());
                    orderLine.setBookId(bk.getBookId());
                    orderLine.setIdOrder(id_order);
                    orderLineService.insert(orderLine);
                }
                // Call the service to delete all elements (clear the table)

                bookQuantityService.deleteAll();

                // Display a success alert
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Confirmation de paiement");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Le paiement a été effectué avec succès!");
                successAlert.showAndWait();
                Stage stage = (Stage) confirmPaymentButton.getScene().getWindow();
                stage.close();
                mainController.displayCard();
                mainController.resetTotalAmount();
            } else if (userAccount != null && (userAccount.getAccountNumber() != Integer.parseInt(accountNumber)
                    || userAccount.getCode() != Integer.parseInt(securityCode))
                    && userAccount.getAmount() >= totalAmount) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Erreur de paiement");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Les données de paiement sont incorrectes.");
                errorAlert.showAndWait();
            } else {
                // Display an error alert for incorrect data
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Erreur de paiement");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Votre solde est insuffisant.");
                errorAlert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }
    }
}
