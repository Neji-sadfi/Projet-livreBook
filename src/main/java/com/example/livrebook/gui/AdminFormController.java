package com.example.livrebook.gui;

import com.example.livrebook.model.user.User;
import com.example.livrebook.service.userServices.UserService;
import com.example.livrebook.util.DbConnection;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminFormController implements Initializable {

    private final UserService us = new UserService();



    @FXML
    private Button addUser_updateBtn;

    private final DbConnection dbConnection = DbConnection.getInstance();
    private Connection connect = dbConnection.getCnx();
    private PreparedStatement prepare;
    private ResultSet result;

    alertMessage alert = new alertMessage();

    @FXML
    private AnchorPane admin;

    @FXML
    private Button user_addbtn;

    @FXML
    private Button user_deletebtn;

    @FXML
    private TableColumn<User, Integer> user_IdCol;

    @FXML
    private TableColumn<User, String> user_firstnameCol;

    @FXML
    private TableColumn<User, String> user_lastnameCol;

    @FXML
    private TableColumn<User, String> user_emailCol;

    @FXML
    private TableColumn<User, String> user_genderCol;


    @FXML
    private TableColumn<?, ?> user_passwordCol;

    @FXML
    private TableColumn<?, ?> user_CpasswordCol;

    @FXML
    private TableColumn<User, String> user_phoneNumberCol;

    @FXML
    private TableColumn<?,?> user_roleCol;

    @FXML
    public TableView<User> user_tableview;

    @FXML
    private Button user_updatebtn;





    public void loadUserData() {
        try {
            user_tableview.getItems().setAll(us.selectAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUserInTableView(User updatedUser) {
        ObservableList<User> userList = user_tableview.getItems();

        int index = -1;
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getId() == updatedUser.getId()) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            userList.set(index, updatedUser);
        } else {
            userList.add(updatedUser);
        }
    }

    public void addUserAddBtn() {
        try {
            String fxmlPath = "/com/example/livrebook/add-user.fxml";

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxmlPath));

            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Add User");
            stage.showAndWait();
            loadUserData();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    public void updateUserBtn() {
        User selectedUser = user_tableview.getSelectionModel().getSelectedItem();

        if (selectedUser != null) {
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/livrebook/update-user.fxml"));
                Parent root = loader.load();

                UpdateAddUserController updateAddUserController = loader.getController();

                updateAddUserController.setAdminFormController(this);

                updateAddUserController.setSelectedUser(selectedUser);

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Update User");
                stage.showAndWait();

                User updatedUser = updateAddUserController.getUpdatedUser();

                if (updatedUser != null) {

                    updateUserInTableView(updatedUser);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            alert.errorMessage("Please select a user to update");
        }
    }



    public void deleteBtn() {
        User selectedUser =user_tableview.getSelectionModel().getSelectedItem();


        if (selectedUser == null) {
            alert.errorMessage("Please select a user to delete");
            return;
        }

        String deleteQuery = "DELETE FROM user WHERE id = ?";

        try {
            prepare = connect.prepareStatement(deleteQuery);
            prepare.setInt(1, selectedUser.getId());

            int rowsDeleted = prepare.executeUpdate();

            if (rowsDeleted > 0) {

                alert.successMessage("User deleted successfully!");
            } else {
                alert.errorMessage("User ID not found for deletion");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (prepare != null) prepare.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        loadUserData();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        user_IdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        user_firstnameCol.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        user_lastnameCol.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        user_emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        user_genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        user_phoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        user_roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));
        user_passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));
        user_CpasswordCol.setCellValueFactory(new PropertyValueFactory<>("confirmpassword"));

        loadUserData();
    }

}
