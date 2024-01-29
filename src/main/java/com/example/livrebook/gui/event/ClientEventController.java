package com.example.livrebook.gui.event;

import com.example.livrebook.model.event.Event;
import com.example.livrebook.service.eventServices.EventService;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.ClientInfoStatus;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ClientEventController implements Initializable {

    @FXML
    private HBox CardLayout;
    @FXML
    private GridPane eventContainer;
    private EventService eventService;
    private List<Event> recentlyAdded;
    private List<Event> recommended;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.eventService = new EventService();
            List<Event> recentlyAdded = recentlyAdded();
            recommended = new ArrayList<>(events());
              int column =0;
              int row=1;
            for (Event event : recentlyAdded) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/livrebook/Event/Card.fxml"));
                HBox cardBox = fxmlLoader.load();
                CardController cardController = fxmlLoader.getController();
                cardController.setData(event);
                CardLayout.getChildren().add(cardBox);
            }
            for (Event event :recommended) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/livrebook/Event/Event.fxml"));
                VBox eventBox = fxmlLoader.load();
                EventC eventC = fxmlLoader.getController();
                eventC.setData(event);
               if (column ==4){
                   column =0;
                   ++row;
               }
               eventContainer.add(eventBox,column++,row);
               GridPane.setMargin(eventBox, new Insets(20));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Event> recentlyAdded() {
        try {
            // Retrieve all events from the database
            List<Event> allEvents = eventService.selectAll();

            // Sort events by date in descending order
            List<Event> sortedEvents = allEvents.stream()
                    .sorted(Comparator.comparing(Event::getStartDate).reversed())
                    .collect(Collectors.toList());

            // Get the first 5 events (or less if there are fewer than 5 events)
            return sortedEvents.stream().limit(5).collect(Collectors.toList());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private List<Event> events() {
        try {
            List<Event> allEvents = eventService.selectAll();

            // Sort events by date in descending order
            List<Event> sortedEvents = allEvents.stream()
                    .sorted(Comparator.comparing(Event::getStartDate).reversed())
                    .collect(Collectors.toList());

            // Skip the last 5 events
            List<Event> eventsExcludingLast5 = sortedEvents.stream()
                    .skip(5)
                    .collect(Collectors.toList());

            return eventsExcludingLast5;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
