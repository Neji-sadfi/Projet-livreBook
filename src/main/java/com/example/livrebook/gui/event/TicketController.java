package com.example.livrebook.gui.event;

import com.example.livrebook.model.event.Ticket;

import com.example.livrebook.service.eventServices.TicketService;
import com.example.livrebook.util.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class TicketController implements Initializable {
    private Connection cnx;
    TicketService ticketService;
    public TicketController(){
        this.ticketService = new TicketService();
        cnx = DbConnection.getInstance().getCnx();
    }
    @FXML
    private TableColumn<Ticket,String> QRCode;

    @FXML
    private TextField Ticket_idevent;

    @FXML
    private Button btn_add;

    @FXML
    private Button btn_delete;
    @FXML
    private TextField Ticket_isPayed;

    @FXML
    private Button btn_update;

    @FXML
    private TableColumn<Ticket, Integer> colId;

    @FXML
    private TableColumn<Ticket,Integer> colIdEvent;

    @FXML
    private TableColumn<Ticket,Boolean> colIsPayed;

    @FXML
    private TableColumn<Ticket,Integer> colPrice;

    @FXML
    private TextField searchId;

    @FXML
    private TextField ticket_Price;

    @FXML
    private TextField ticket_QR;

    @FXML
    private TextField ticket_id;

    @FXML
    private TableView<Ticket> tvTicket;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showTickets();
        searchId.textProperty().addListener((observableList, oldValue, newValue) -> {
            filterTickets(newValue);
        });
    }
    void handleButtonAction(ActionEvent event) {
        if (event.getSource()==btn_add){
            insertRecord();
        }
        if (event.getSource()==btn_update){
            updateRecord();
        }
        if (event.getSource()==btn_delete){
            deleteRecord();
        }
    }

    public List<Ticket> getTicketList() {
        try {

            return ticketService.selectAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);

    }}



    public ObservableList<Ticket> getObservableTicketList() {
        return FXCollections.observableArrayList(getTicketList());
    }


        public ObservableList<Ticket> showTickets() {
            ObservableList<Ticket> list = getObservableTicketList();
            colId.setCellValueFactory(new PropertyValueFactory<>("id"));
            QRCode.setCellValueFactory(new PropertyValueFactory<>("QRCODE")); // This line is causing the NullPointerException
            colIdEvent.setCellValueFactory(new PropertyValueFactory<>("idevent"));
            colIsPayed.setCellValueFactory(new PropertyValueFactory<>("isPayed"));

            colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            tvTicket.setItems(list);
            return list;
        }


    private void insertRecord() {
        try {
            String req = "INSERT INTO ticket (QRCODE, idevent, isPayed, price) " +
                    "VALUES ('" + ticket_QR.getText() + "','" + Ticket_idevent.getText() + "','"
                    + Ticket_isPayed.getText() + "','" + ticket_Price.getText() + "')";

            executeQuery(req);
            showTickets();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void executeQuery(String req) {
        try (Statement st = cnx.createStatement()) {
            st.executeUpdate(req);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void updateRecord() {
        String req = "UPDATE ticket SET " +
                "QRCODE='" + ticket_QR.getText() + "', " +
                "idevent='" + Ticket_idevent.getText() + "', " +
                "isPayed='" + "false" + "', " +
                "price='" + ticket_Price.getText() + "' " +
                "WHERE id=" + ticket_id.getText();

        executeQuery(req);
        showTickets();
    }

    private void deleteRecord() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Delete confirmation");
        dialog.setHeaderText("Are you sure to delete this ticket?");

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, cancelButton);
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == okButton) {
            String id = ticket_id.getText();

            try {
                ticketService.delete(Integer.parseInt(id));
                showTickets();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void mouseClicked(javafx.scene.input.MouseEvent event) {
        Ticket t = tvTicket.getSelectionModel().getSelectedItem();
        if (t != null) {
            try {
                int t_id = t.getId();
                ticket_QR.setText(t.getQRCODE());
                Ticket_idevent.setText(String.valueOf(t.getIdevent()));
                ticket_Price.setText(String.valueOf(t.getPrice()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void filterTickets(String searchQuery) {
        ObservableList<Ticket> filteredList = FXCollections.observableArrayList();
        ObservableList<Ticket> ticketD = getObservableTicketList();
        for (Ticket ticket : ticketD) {
            if (containsIgnoreCase(String.valueOf(ticket.getQRCODE()), searchQuery) ||
                    containsIgnoreCase(String.valueOf(ticket.getIdevent()), searchQuery) ||
                    containsIgnoreCase(String.valueOf(ticket.getPrice()), searchQuery)) {
                filteredList.add(ticket);
            }
        }
        tvTicket.setItems(filteredList);
    }

    private boolean containsIgnoreCase(String source, String query) {
        return source != null && source.toLowerCase().contains(query.toLowerCase());
    }
}

