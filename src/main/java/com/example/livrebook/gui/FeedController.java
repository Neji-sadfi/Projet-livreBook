package com.example.livrebook.gui;

import com.example.livrebook.util.DbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FeedController {

    @FXML
    private Button btnSoumettre;

    @FXML
    private TextField tfDescription;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfNom;

    // Add a method to get the database connection
    private Connection getConnection() {
        return DbConnection.getInstance().getCnx();
    }

    @FXML
    void soumettreReclamation(ActionEvent event) {
        String nom = tfNom.getText();
        String email = tfEmail.getText();
        String description = tfDescription.getText();

        // Define the SQL query
        String sql = "INSERT INTO feedback (nom, email, description) VALUES (?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {


            statement.setString(1, nom);
            statement.setString(2, email);
            statement.setString(3, description);

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                showAlert("Réclamation soumise avec succès", "La réclamation a été soumise avec succès.");

                tfNom.clear();
                tfEmail.clear();
                tfDescription.clear();
            } else {
                showAlert("Erreur", "Échec de la soumission de la réclamation.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



        private void showAlert(String title, String content) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(content);
            alert.showAndWait();
        }

    }

