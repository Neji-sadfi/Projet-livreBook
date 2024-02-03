package com.example.livrebook.gui;

import com.example.livrebook.model.user.User;
import com.example.livrebook.service.userServices.UserService;
import com.example.livrebook.util.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class UpdateAddUserController implements Initializable {

    @FXML
    private Button User_updateBtn;

    @FXML
    private PasswordField updateUser_Cpassword;

    @FXML
    private TextField updateUser_email;

    @FXML
    private TextField updateUser_firstname;

    @FXML
    private TextField updateUser_gender;

    @FXML
    private TextField updateUser_id;

    @FXML
    private TextField updateUser_lastname;

    @FXML
    private PasswordField updateUser_password;

    @FXML
    private TextField updateUser_phone;

    @FXML
    private Button addUser_btnAdd;

    @FXML
    private TextField addUser_email;

    @FXML
    private TextField addUser_firstname;

    @FXML
    private TextField addUser_gender;

    @FXML
    private TextField addUser_id;

    @FXML
    private TextField addUser_lastname;

    @FXML
    private TextField addUser_phone;

    @FXML
    private PasswordField addUser_password;

    @FXML
    private PasswordField addUser_Cpassword;

    @FXML
    private ChoiceBox<String> addUser_role;

    @FXML
    private Button addUser_updateBtn;

    private final DbConnection dbConnection = DbConnection.getInstance();
    private Connection connect = dbConnection.getCnx();
    private PreparedStatement prepare;
    private ResultSet result;

    alertMessage alert = new alertMessage();
    AdminFormController adminFormController;



    private void populateRoleChoiceBoxAddBtn() {
        if (addUser_role != null) {
        List<String> roles = Arrays.asList("Admin", "Client");
        ObservableList<String> roleList = FXCollections.observableArrayList(roles);

        addUser_role.setItems(roleList);

        // Set the default value if the list is not empty
        if (!roleList.isEmpty()) {
            addUser_role.setValue(roleList.get(0));
        }
    } else {
        System.err.println("addUser_role is null. Check your FXML file.");
    }
    }


    @FXML
    public void addBtn() {
        if (addUser_id.getText().isEmpty()
                || addUser_firstname.getText().isEmpty()
                || addUser_lastname.getText().isEmpty()
                || addUser_email.getText().isEmpty()
                || addUser_gender.getText().isEmpty()
                || addUser_phone.getText().isEmpty()
                || addUser_password.getText().isEmpty()
                || addUser_Cpassword.getText().isEmpty()
                || addUser_role.getSelectionModel().isEmpty()) {

            alert.errorMessage("Please fill all fields");
        } else if (!addUser_password.getText().equals(addUser_Cpassword.getText())) {

            alert.errorMessage("Passwords do not match");
        } else {
            String checkUserId = "SELECT * FROM user WHERE user.id = ?";
            try {
                prepare = connect.prepareStatement(checkUserId);
                prepare.setString(1, addUser_id.getText());
                result = prepare.executeQuery();

                if (result.next()) {

                    alert.errorMessage("User ID is taken");
                } else {
                    String insertData = "INSERT INTO user (first_name, last_name, email, gender, phoneNumber, password,confirmpassword, role) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?,?)";

                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1, addUser_firstname.getText());
                    prepare.setString(2, addUser_lastname.getText());
                    prepare.setString(3, addUser_email.getText());
                    prepare.setString(4, addUser_gender.getText());
                    prepare.setString(5, addUser_phone.getText());
                    prepare.setString(6, addUser_password.getText());
                    prepare.setString(7, addUser_Cpassword.getText());
                    prepare.setString(8, addUser_role.getSelectionModel().getSelectedItem().toString());

                    prepare.executeUpdate();

                    alert.successMessage("User added successfully!");

                    clearFields();
                    ((Stage) addUser_btnAdd.getScene().getWindow()).close();
                }
            } catch (SQLException e) {
                e.printStackTrace();

            } finally {
                try {
                    if (result != null) result.close();
                    if (prepare != null) prepare.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    User selectedUser;

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;

        if (selectedUser != null) {
            updateUser_id.setText(String.valueOf(selectedUser.getId()));
            updateUser_firstname.setText(selectedUser.getFirst_name());
            updateUser_lastname.setText(selectedUser.getLast_name());
            updateUser_email.setText(selectedUser.getEmail());
            updateUser_gender.setText(selectedUser.getGender());
            updateUser_phone.setText(selectedUser.getPhoneNumber());
        }
    }

    private final UserService us = new UserService();

    public void setAdminFormController(AdminFormController adminFormController) {
        this.adminFormController = adminFormController;
    }
    @FXML
    public void updateBtn() {

        if (updateUser_id.getText().isEmpty()
                || updateUser_firstname.getText().isEmpty()
                || updateUser_lastname.getText().isEmpty()
                || updateUser_email.getText().isEmpty()
                || updateUser_gender.getText().isEmpty()
                || updateUser_phone.getText().isEmpty()
                || updateUser_password.getText().isEmpty()
                || updateUser_Cpassword.getText().isEmpty()) {
            alert.errorMessage("Please fill all fields");
            return;
        }

        if (!updateUser_password.getText().equals(updateUser_Cpassword.getText())) {
            alert.errorMessage("Passwords do not match");
            return;
        }

        String updateData = "UPDATE user SET " +
                "first_name = ?, " +
                "last_name = ?, " +
                "email = ?, " +
                "gender = ?, " +
                "phoneNumber = ?, " +
                "password = ?, " +
                "confirmpassword = ? " +
                "WHERE id = ?";

        try {
            prepare = connect.prepareStatement(updateData);
            prepare.setString(1, updateUser_firstname.getText());
            prepare.setString(2, updateUser_lastname.getText());
            prepare.setString(3, updateUser_email.getText());
            prepare.setString(4, updateUser_gender.getText());
            prepare.setString(5, updateUser_phone.getText());
            prepare.setString(6, updateUser_password.getText());
            prepare.setString(7, updateUser_Cpassword.getText());
            prepare.setString(8, updateUser_id.getText());

            int rowsUpdated = prepare.executeUpdate();

            if (rowsUpdated > 0) {
                alert.successMessage("User updated successfully!");

                updatedUser = us.selectById(Integer.parseInt(updateUser_id.getText()));


                ((Stage) User_updateBtn.getScene().getWindow()).close();


            } else {
                alert.errorMessage("User ID not found for update");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            alert.errorMessage("Error updating user");
        }
    }

    private User updatedUser;

    public User getUpdatedUser() {
        return updatedUser;
    }

    public void loadUpdatedUserInTableView(User updatedUser) {
        adminFormController.updateUserInTableView(updatedUser);
    }
    @FXML

    private void clearFields() {
        addUser_id.clear();
        addUser_firstname.clear();
        addUser_lastname.clear();
        addUser_email.clear();
        addUser_gender.clear();
        addUser_phone.clear();
        addUser_password.clear();
        addUser_Cpassword.clear();
        addUser_role.getSelectionModel().clearSelection();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            populateRoleChoiceBoxAddBtn();

    }
}
