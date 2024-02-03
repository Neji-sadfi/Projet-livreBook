package com.example.livrebook.gui.delivery;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import com.example.livrebook.model.reclamation.FeedBack;
import com.example.livrebook.service.reclamationServices.FeedbackService;
import com.example.livrebook.util.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class FeedBackAdminController {
    @FXML
    private TableView<FeedBack> FeedBackTableView;

    @FXML
    private TableColumn<FeedBack, Integer> idColumn;

    @FXML
    private TableColumn<FeedBack, String> nomClientColumn;

    @FXML
    private TableColumn<FeedBack, String> emailColumn;

    @FXML
    private TableColumn<FeedBack, String> descriptionColumn;

    @FXML
    private TableColumn<FeedBack, String> reponseColumn; // Nouvelle colonne pour la réponse

    @FXML
    private TableColumn<FeedBack, String> Supprec;

    private FeedbackService feedbackService;

    public FeedBackAdminController() {
        this.feedbackService = new FeedbackService();
    }

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomClientColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        // Utilisation de ButtonTableCellFactoryReply
        reponseColumn.setCellFactory(new ButtonTableCellFactoryReply());
        Supprec.setCellFactory(new ButtonTableCellFactoryDelete(feedbackService));

        try {
            List<FeedBack> feedbackList = feedbackService.selectAll();
            FeedBackTableView.getItems().addAll(feedbackList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Classe ButtonTableCellFactoryDelete en tant que classe régulière à l'intérieur de FeedBackAdminController
    public class ButtonTableCellFactoryDelete implements Callback<TableColumn<FeedBack, String>, TableCell<FeedBack, String>> {
        private final FeedbackService feedbackService;

        public ButtonTableCellFactoryDelete(FeedbackService feedbackService) {
            this.feedbackService = feedbackService;
        }

        @Override
        public TableCell<FeedBack, String> call(TableColumn<FeedBack, String> param) {
            return new TableCell<>() {
                final Button button = new Button("Supprimer");

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        setGraphic(button);
                        button.setOnAction(event -> {
                            // Obtenez la réclamation associée à cette ligne
                            FeedBack feedback = getTableView().getItems().get(getIndex());

                            // Supprimez la réclamation de la base de données
                            try {
                                feedbackService.removeFeedback(feedback);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }

                            // Supprimez la réclamation de la TableView
                            getTableView().getItems().remove(feedback);
                        });
                    }
                }
            };
        }
    }

    // Classe de cellFactory pour le bouton "Répondre"
    public class ButtonTableCellFactoryReply implements Callback<TableColumn<FeedBack, String>, TableCell<FeedBack, String>> {
        @Override
        public TableCell<FeedBack, String> call(TableColumn<FeedBack, String> param) {
            return new TableCell<>() {
                final Button button = new Button("Répondre");

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        setGraphic(button);
                        button.setOnAction(event -> {
                            FeedBack selectedFeedback = getTableView().getItems().get(getIndex());

                            // Afficher une zone de texte pour la réponse
                            TextArea responseTextArea = new TextArea();
                            responseTextArea.setPromptText("Saisissez votre réponse ici...");

                            Button sendButton = new Button("Envoyer");
                            sendButton.setOnAction(e -> {
                                String response = responseTextArea.getText();
                                int reclamationId = selectedFeedback.getId(); // Récupérer l'identifiant de la réclamation
                                String clientEmail = selectedFeedback.getEmail(); // Get the client's email
                                String sql = "INSERT INTO re_reponse (id_reclamation, reponse) VALUES (?, ?)";
                                try (Connection connection = DbConnection.getInstance().getCnx();
                                     PreparedStatement statement = connection.prepareStatement(sql)) {
                                    statement.setInt(1, reclamationId);
                                    statement.setString(2, response);
                                    int rowsInserted = statement.executeUpdate();
                                    if (rowsInserted > 0) {
                                        System.out.println("Réponse insérée avec succès dans la table re_reponse.");
                                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                                        successAlert.setTitle("Succès");
                                        successAlert.setHeaderText(null);
                                        successAlert.setContentText("Réponse insérée avec succès dans la table re_reponse.");
                                        successAlert.showAndWait();

                                    } else {
                                        System.out.println("Échec de l'insertion de la réponse dans la table re_reponse.");
                                    }
                                } catch (SQLException ex) {
                                    ex.printStackTrace();
                                }
                            });

                            Alert responseAlert = new Alert(Alert.AlertType.INFORMATION);
                            responseAlert.setTitle("Répondre à la réclamation");
                            responseAlert.setHeaderText(null);
                            responseAlert.getDialogPane().setContent(new VBox(responseTextArea, sendButton));
                            responseAlert.showAndWait();
                        });
                    }
                }
            };
        }
    }

}
