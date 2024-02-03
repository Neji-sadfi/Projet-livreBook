package com.example.livrebook.gui.actuality;

import com.example.livrebook.model.actuality.Actuality;
import com.example.livrebook.service.actualityServices.ActualityService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;

public class CardController implements Initializable {

    @FXML
    private HBox box;

    @FXML
    private ImageView imageField;

    @FXML
    private Label titleLabel;

    private Actuality actualityData;
    private final ActualityService act;
    public CardController(){this.act=new ActualityService();}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void setActualityData(Actuality actualityData) {
        this.actualityData = actualityData;
        if (actualityData != null) {
            titleLabel.setText(actualityData.getTitle());
            String imagePath = actualityData.getPicture();
            if (imagePath != null && !imagePath.isEmpty()) {
                String fullImagePath = "file:///" + "C:/Users/user/IdeaProjects/Projet-livreBook/src/main/resources/com/example/livrebook/images/" + imagePath;
                Image image = new Image(fullImagePath);
                imageField.setImage(image);
            }
        }
    }

    @FXML
    private void showDetailsPopup(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/livrebook/actuality/ActuallityDetailsPopup.fxml"));
            Parent root = loader.load();

            ActualityDetailsPopupController controller = loader.getController();
            controller.setActualityData(actualityData);

            Stage stage = new Stage();
            stage.setTitle("Details Actuality");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
