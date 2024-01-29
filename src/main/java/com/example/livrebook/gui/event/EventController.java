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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import org.w3c.dom.events.MouseEvent;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;



public class EventController implements Initializable {
    private EventService eventService;
    private Event event;
    private Connection cnx;
    private ObservableList<Event> eventD;

    public EventController() {
        this.eventService = new EventService();
        cnx = DbConnection.getInstance().getCnx();
    }
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

    @FXML
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





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        showEvent();
        searchId.textProperty().addListener((observableList, oldValue, newValue)->{
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
        colId.setCellValueFactory(new PropertyValueFactory<Event,Integer>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<Event,String>("title"));
        colAdress.setCellValueFactory(new PropertyValueFactory<Event,String>("adresse"));
        colDesc.setCellValueFactory(new PropertyValueFactory<Event,String>("description"));
        colPic.setCellValueFactory(new PropertyValueFactory<Event,String>("picture"));
        colStart.setCellValueFactory(new PropertyValueFactory<Event,Date>("startDate"));
        colEnd.setCellValueFactory(new PropertyValueFactory<Event,Date>("endDate"));
        tvEvent.setItems(list);
        return list;

    }

    private void insertRecord() {
        String req = "INSERT INTO event (title, adresse, description, picture, startDate, endDate, nb_ticket) " +
                "VALUES ('" + event_title.getText() + "','" + Event_adress.getText() + "','"
                + Event_desc.getText() + "','" + Event_pic.getText()
                + "','" + Event_start.getValue() + "','" + Event_end.getValue() + "','" + nb_ticket.getText() + "')";

        executeQuery(req);
        showEvent();
    }


    private void executeQuery(String req) {
        Statement st ;
        try {
            st = cnx.createStatement();
            st.executeUpdate(req);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    private void updateRecord(){
        String req = "UPDATE event SET " +
                "title='" + event_title.getText() + "', " +
                "adresse='" + Event_adress.getText() + "', " +
                "description='" + Event_desc.getText() + "', " +
                "picture='" + Event_pic.getText() + "', " +
                "startDate='" + Event_start.getValue() + "', " +
                "endDate='" + Event_end.getValue() + "', " +
                "nb_ticket='" + nb_ticket.getText() + "' " +
                "WHERE id=" + event_id.getText();



        executeQuery(req);
        showEvent();
    }

    private void deleteRecord() {

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Delete confirmation");
        dialog.setHeaderText("are you sure to delete this event ?");


        ButtonType okButton = new ButtonType ("OK",ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel",ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton,cancelButton);
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get()==okButton){
            String id = event_id.getText();

            try {
                eventService.delete(Integer.parseInt(id));
                showEvent();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }


    public void mouseClicked (javafx.scene.input.MouseEvent event){
        Event e = tvEvent.getSelectionModel().getSelectedItem();
        if (e != null) {

          try {

            int e_id=e.getId();
           event_title.setText(e.getTitle());
           Event_adress.setText(e.getAdresse());
           Event_desc.setText(e.getDescription());
           Event_pic.setText(e.getPicture());
           Event_start.setValue(LocalDate.parse(String.valueOf(e.getEndDate())));
           Event_end.setValue(LocalDate.parse(String.valueOf(e.getEndDate())));
           nb_ticket.setText(String.valueOf(e.getNb_ticket()));
       }catch(Exception e1){
           e1.printStackTrace();
       }

    }}

    private void filterEvents(String searchQuery) {
        // Perform filtering based on the search query
        ObservableList<Event> filteredList = FXCollections.observableArrayList();
        ObservableList<Event> eventD= getObservableEventList();
        for (Event event : eventD) {
            // Customize this condition based on your search criteria
            if (containsIgnoreCase(event.getTitle(), searchQuery) ||
                    containsIgnoreCase(event.getDescription(), searchQuery) ||
                    containsIgnoreCase(event.getAdresse(), searchQuery)) {
                filteredList.add(event);
            }
        }

        // Update the TableView with the filtered list
        tvEvent.setItems(filteredList);
    }

    private boolean containsIgnoreCase(String source, String query) {
        return source != null && source.toLowerCase().contains(query.toLowerCase());
    }


}