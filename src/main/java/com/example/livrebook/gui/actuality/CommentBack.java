package com.example.livrebook.gui.actuality;

import com.example.livrebook.model.actuality.Comment;
import com.example.livrebook.service.actualityServices.CommentService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class CommentBack implements Initializable {

    @FXML
    private TableView<Comment> TVId;

    @FXML
    private TableColumn<Comment, String> colComment;

    @FXML
    private TableColumn<Comment, String> colDelete;

    private final CommentService commentService = new CommentService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Configure the table columns
        colComment.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDelete.setCellFactory(new ButtonTableCellFactoryDelete(commentService));

        // Load data from the database
        loadData();
    }

    private void loadData() {
        try {
            List<Comment> comments = commentService.selectAll();
            ObservableList<Comment> commentList = FXCollections.observableArrayList(comments);
            TVId.setItems(commentList);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle appropriate exceptions here
        }
    }

    public static class ButtonTableCellFactoryDelete implements Callback<TableColumn<Comment, String>, TableCell<Comment, String>> {
        private final CommentService commentService;

        public ButtonTableCellFactoryDelete(CommentService commentService) {
            this.commentService = commentService;
        }

        @Override
        public TableCell<Comment, String> call(TableColumn<Comment, String> param) {
            return new TableCell<>() {
                final Button button = new Button("Supprimer");

                {
                    button.setOnAction(event -> {
                        Comment comment = getTableView().getItems().get(getIndex());
                        try {
                            commentService.delete(comment);
                            ((TableView<Comment>) getTableView()).getItems().remove(comment);
                        } catch (SQLException e) {
                            e.printStackTrace();
                            // Handle appropriate exceptions here
                        }
                    });
                }

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        setGraphic(button);
                        setText(null);
                    }
                }
            };
        }
    }
}
