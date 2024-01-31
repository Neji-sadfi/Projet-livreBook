package com.example.livrebook.gui.event;

import com.example.livrebook.model.event.Event;
import com.example.livrebook.service.eventServices.EventService;
import com.example.livrebook.util.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class EventController implements Initializable {
    private EventService eventService;
    private Connection cnx;

    @FXML
    private TextField searchId;

    @FXML
    private TextField nb_ticket;

    @FXML
    private TextField Event_adress;

    @FXML
    private TextField Event_desc;

    @FXML
    private DatePicker Event_end;

    @FXML
    private TextField Event_pic;

    @FXML
    private DatePicker Event_start;

    @FXML
    private TextField event_id;

    @FXML
    private Button btn_add;

    @FXML
    private Button btn_delete;

    @FXML
    private Button btn_update;

    @FXML
    private TableColumn<Event, Integer> colId;

    @FXML
    private TableColumn<Event, String> colTitle;

    @FXML
    private TableColumn<Event, String> colAdress;

    @FXML
    private TableColumn<Event, String> colDesc;

    @FXML
    private TableColumn<Event, Date> colEnd;

    @FXML
    private TableColumn<Event, String> colPic;

    @FXML
    private TableColumn<Event, Date> colStart;

    @FXML
    private TextField event_title;

    @FXML
    private TableView<Event> tvEvent;

    public EventController() {
        this.eventService = new EventService();
        cnx = DbConnection.getInstance().getCnx();
    }

    @FXML
    void handleButtonAction(ActionEvent event) throws SQLException {
        if (event.getSource() == btn_add) {
            if (validateInput()) {
                insertRecord();
            }
        }
        if (event.getSource() == btn_update) {
            if (validateInput()) {
                updateRecord();
            }
        }
        if (event.getSource() == btn_delete) {
            deleteRecord();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showEvent();
        searchId.textProperty().addListener((observableList, oldValue, newValue) -> {
            filterEvents(newValue);
        });
    }

    public List<Event> getEventList() {
        try {
            return eventService.selectAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList<Event> getObservableEventList() {
        return FXCollections.observableArrayList(getEventList());
    }

    public ObservableList<Event> showEvent() {
        ObservableList<Event> list = getObservableEventList();

        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAdress.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPic.setCellValueFactory(new PropertyValueFactory<>("picture"));
        colStart.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        colEnd.setCellValueFactory(new PropertyValueFactory<>("endDate"));

        tvEvent.setItems(list);
        return list;
    }

    private void insertRecord() {
        String req = "INSERT INTO event (title, adresse, description, picture, startDate, endDate, nb_ticket) " +
                "VALUES ('" + event_title.getText() + "','" + Event_adress.getText() + "','"
                + Event_desc.getText() + "','" + Event_pic.getText()
                + "','" + Event_start.getValue() + "','" + Event_end.getValue() + "','" + nb_ticket.getText() + "')";

        if (executeQuery(req)) {
            showEvent();
            showAlert(AlertType.INFORMATION, "Success", "Record added successfully", null);
        } else {
            showAlert(AlertType.ERROR, "Error", "Failed to add record", null);
        }
    }

    private boolean executeQuery(String req) {
        try (Statement st = cnx.createStatement()) {
            st.executeUpdate(req);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void updateRecord() {
        String req = "UPDATE event SET " +
                "title='" + event_title.getText() + "', " +
                "adresse='" + Event_adress.getText() + "', " +
                "description='" + Event_desc.getText() + "', " +
                "picture='" + Event_pic.getText() + "', " +
                "startDate='" + Event_start.getValue() + "', " +
                "endDate='" + Event_end.getValue() + "', " +
                "nb_ticket='" + nb_ticket.getText() + "' " +
                "WHERE id=" + mouseClicked();

        if (executeQuery(req)) {
            showEvent();
            showAlert(AlertType.INFORMATION, "Success", "Record updated successfully", null);
        } else {
            showAlert(AlertType.ERROR, "Error", "Failed to update record", null);
        }
    }

    private void deleteRecord() {
        System.out.println(mouseClicked());
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Delete confirmation");
        dialog.setHeaderText("Are you sure to delete this ticket?");

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, cancelButton);
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == okButton) {
         // Trim to handle whitespace

                try {
                    eventService.delete(mouseClicked());
                  showEvent();

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
        }

    }


    public int mouseClicked() {
        Event e = tvEvent.getSelectionModel().getSelectedItem();

        if (e != null) {
            try {


                event_title.setText(e.getTitle());
                Event_adress.setText(e.getAdresse());
                Event_desc.setText(e.getDescription());
                Event_pic.setText(e.getPicture());
                Event_start.setValue(LocalDate.parse(String.valueOf(e.getStartDate())));
                Event_end.setValue(LocalDate.parse(String.valueOf(e.getEndDate())));
                nb_ticket.setText(String.valueOf(e.getNb_ticket()));

               return e.getId();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        return 0;
    }


    private void filterEvents(String searchQuery) {
        ObservableList<Event> filteredList = FXCollections.observableArrayList();
        ObservableList<Event> eventD = getObservableEventList();
        for (Event event : eventD) {
            if (containsIgnoreCase(event.getTitle(), searchQuery) ||
                    containsIgnoreCase(event.getDescription(), searchQuery) ||
                    containsIgnoreCase(event.getAdresse(), searchQuery)) {
                filteredList.add(event);
            }
        }

        tvEvent.setItems(filteredList);
    }

    private boolean containsIgnoreCase(String source, String query) {
        return source != null && source.toLowerCase().contains(query.toLowerCase());
    }

    private boolean validateInput() {


        if (!isValidNbTicket()) {
            showAlert(AlertType.ERROR, "Invalid Input", "Invalid number of tickets", "Number of tickets should be a positive integer.");
            return false;
        }

        return true;
    }



    private boolean isValidNbTicket() {
        String nbTicket = nb_ticket.getText();
        return nbTicket.matches("\\d+") && Integer.parseInt(nbTicket) > 0;
    }

    private void showAlert(AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
