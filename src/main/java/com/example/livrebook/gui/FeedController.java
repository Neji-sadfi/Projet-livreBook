package com.example.livrebook.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FeedController {

    @FXML
    private TextArea chatArea;

    @FXML
    private TextField inputField;

    @FXML
    private Button sendButton;

    @FXML
    private void initialize() {
        // Cette méthode est appelée automatiquement après le chargement de l'interface.
        // Mettez ici le code que vous souhaitez exécuter lors de l'initialisation.

        // Définir le focus sur la zone de saisie du texte par défaut
        inputField.requestFocus();
    }

    @FXML
    private void handleSendButtonAction(ActionEvent event) {
        String userMessage = inputField.getText();
        // Traitez le message ici (simplement ajoutez-le à la zone de chat pour cet exemple)
        chatArea.appendText("Utilisateur : " + userMessage + "\n");
        // Vous pouvez ajouter ici la logique pour traiter la réclamation
        // ...

        // Effacez le champ de saisie après l'envoi
        inputField.clear();
    }
}
