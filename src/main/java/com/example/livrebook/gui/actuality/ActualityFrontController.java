package com.example.livrebook.gui.actuality;

import com.example.livrebook.model.actuality.Actuality;
import com.example.livrebook.service.actualityServices.ActualityService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ActualityFrontController implements Initializable {

    @FXML
    private GridPane gridPane;

    private final ActualityService actualityService;
    public  ActualityFrontController(){this.actualityService = new ActualityService();}
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            List<Actuality> recentlyAdded = recentlyAdded();

            int cardsPerRow = 3;
            int column = 0;
            int row = 0;

            for (Actuality event : recentlyAdded) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/livrebook/actuality/Card.fxml"));
                HBox cardBox = fxmlLoader.load();
                CardController cardController = fxmlLoader.getController();
                cardController.setActualityData(event);
                System.out.println("Retrieved Actuality: " + event.getTitle());
                if (column == cardsPerRow) {
                    column = 0;
                    ++row;
                }
                gridPane.add(cardBox, column++, row);
                GridPane.setMargin(cardBox, new Insets(20));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public List<Actuality> recentlyAdded() {
        try {
            List<Actuality> allEvents = actualityService.selectAll();
            System.out.println("All Retrieved Actualities:");
            for (Actuality act : allEvents) {
                System.out.println(act.getTitle());
            }
            List<Actuality> sortedEvents = allEvents.stream().sorted((e1, e2) -> e2.getDate().compareTo(e1.getDate())).collect(Collectors.toList());
            return sortedEvents;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}