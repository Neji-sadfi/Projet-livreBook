package com.example.livrebook.gui.actuality;

import com.example.livrebook.model.actuality.Actuality;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ActualityDetailsPopupController implements Initializable {

    @FXML
    private VBox root;

    @FXML
    private Label titleLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private ImageView imageView;


    private Actuality actu;
    public void setActualityData(Actuality actuality) {
        this.actu = actuality;
        if (titleLabel != null && descriptionLabel != null && imageView != null && actuality != null) {
            titleLabel.setText(actuality.getTitle());
            descriptionLabel.setText(actuality.getDescription());
            System.out.println(titleLabel+","+descriptionLabel);
            String imagePath = actuality.getPicture();
            if (imagePath != null && !imagePath.isEmpty()) {
                // Assuming imagePath contains the relative path to the image file
                String fullImagePath = "/com/example/livrebook/images/" + imagePath;
                // Load image from resources
                try {
                    Image image = new Image(getClass().getResourceAsStream(fullImagePath));
                    imageView.setImage(image);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
    @FXML
    private void onActionButtonClicked(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/livrebook/actuality/CommentView.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Commentaires");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    }

