package com.example.livrebook.gui.actuality;

import com.example.livrebook.model.actuality.Comment;
import com.example.livrebook.service.actualityServices.CommentService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CommentController implements Initializable {

    @FXML
    private TextArea CommentField;

    private final CommentService commentService;

    // Default constructor
    public CommentController() {
        this.commentService = new CommentService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void OnSendComment(ActionEvent actionEvent) {
        String comment = CommentField.getText();
        Comment com = new Comment(comment);
        try {
            commentService.insert(com);
            showPopup("comment sent", Alert.AlertType.INFORMATION);
        } catch (SQLException e) {
            showPopup("Problem adding the comment.", Alert.AlertType.ERROR);
            throw new RuntimeException(e);
        }
    }

    private void showPopup(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
