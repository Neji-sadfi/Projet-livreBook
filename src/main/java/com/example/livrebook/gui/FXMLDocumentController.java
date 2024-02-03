package com.example.livrebook.gui;

import com.example.livrebook.service.userServices.UserService;
import com.example.livrebook.util.DbConnection;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import com.example.livrebook.model.user.User;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FXMLDocumentController implements Initializable {

    private Connection connect;
    private PreparedStatement prepare;


    UserService us ;

    public FXMLDocumentController() {
        this.us= new UserService();
    }

    @FXML
    private Button changePass_backBtn;

    @FXML
    private PasswordField changePass_confirmPass;

    @FXML
    private AnchorPane changePass_form;

    @FXML
    private PasswordField changePass_password;

    @FXML
    private Button changePass_proceedBtn;

    @FXML
    private TextField forgot_answer;

    @FXML
    private Button forgot_backBtn;

    @FXML
    private AnchorPane forgot_form;

    @FXML
    private Button forgot_proceedbtn;

    @FXML
    private ComboBox<?> forgot_selectQuestion;

    @FXML
    private TextField forgot_username;

    @FXML
    private Button login_btn;

    @FXML
    private Button login_createAccount;

    @FXML
    private Hyperlink login_forgotpassword;

    @FXML
    private AnchorPane login_form;

    @FXML
    private PasswordField login_password;

    @FXML
    private CheckBox login_selectshowpassword;

    @FXML
    private TextField login_showpassword;

    @FXML
    private ChoiceBox<String> login_choiceBox;

    @FXML
    private TextField login_username;

    @FXML
    private AnchorPane main_form;

    @FXML
    private TextField signup_answer;

    @FXML
    private Button signup_btn;

    @FXML
    private PasswordField signup_confirmpass;

    @FXML
    private TextField signup_email;

    @FXML
    private AnchorPane signup_form;

    @FXML
    private TextField signup_gender;
    @FXML
    private TextField signup_lastname;

    @FXML
    private Button signup_loginaccount;

    @FXML
    private TextField signup_number;

    @FXML
    private PasswordField signup_password;

    @FXML
    private ComboBox<?> signup_selectQuestion;

    @FXML
    private TextField signup_username;


    public void login() {
        alertMessage alert = new alertMessage();
        if (login_username.getText().isEmpty() || login_password.getText().isEmpty()) {
            alert.errorMessage("Incorrect Username/Password");
        } else {
            try {
                String selectedRole = String.valueOf(login_choiceBox.getValue());

                if (selectedRole == null) {
                    alert.errorMessage("Please select a role");
                    return;
                }

                User loginUser = new User(
                        login_username.getText(),
                        "",
                        "",
                        "",
                        "",
                        login_password.getText(),
                        "",
                        "",
                        "",
                        selectedRole
                );

                boolean loginSuccess = us.login(loginUser);

                if (loginSuccess) {
                    redirectToRolePage(selectedRole);
                } else {
                    alert.errorMessage("Incorrect Username/Password");
                }
            } catch (SQLException | IOException e) {
                System.err.println(e.getMessage());
                alert.errorMessage("An error occurred during login");
            }
        }
    }


    public void redirectToRolePage(String role) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();

            switch (role) {
                case "Admin":
                    loader.setLocation(getClass().getResource("/com/example/livrebook/admin-form.fxml"));
                    break;
                case "Client":


                    break;
                default:
                    return;
            }

            Parent root = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);

            stage.setTitle("LivreBook | " + role + " Portal");
            stage.setScene(scene);
            stage.show();

            login_btn.getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void populateRoleChoiceBox() {
        String[] roleList = {"Admin", "Client"};

        ObservableList<String> roleObservableList = FXCollections.observableArrayList(roleList);

        login_choiceBox.setItems(roleObservableList);

        login_choiceBox.setValue(roleList[0]);
    }

    public void showPassword(){
        if (login_selectshowpassword.isSelected()){
            login_showpassword.setText(login_password.getText());
            login_showpassword.setVisible(true);
            login_password.setVisible(false);
        }else{
            login_password.setText(login_showpassword.getText());
            login_showpassword.setVisible(false);
            login_password.setVisible(true);
        }
    }

    public void forgotPassword(){
        alertMessage alert = new alertMessage();

        if ( forgot_username.getText().isEmpty()
                || forgot_selectQuestion.getSelectionModel().getSelectedItem()== null
                || forgot_answer.getText().isEmpty()){
            alert.errorMessage("Please fill all blank fields");
        }else {
            try {
                User user = new User(
                        forgot_username.getText(),
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        (String) forgot_selectQuestion.getSelectionModel().getSelectedItem(),
                        forgot_answer.getText()
                );

                boolean passwordRecoverySuccess = us.forgotPassword(user);

                if (passwordRecoverySuccess) {
                    alert.successMessage("Password recovery successful!");
                    signup_form.setVisible(false);
                    login_form.setVisible(false);
                    forgot_form.setVisible(false);
                    changePass_form.setVisible(true);

                } else {
                    alert.errorMessage("Incorrect information for password recovery");
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
                alert.errorMessage("An error occurred during password recovery");
            }
        }
    }

    public void forgotListQuestions(){
        List<String> listQ = new ArrayList<>();
        for ( String data : questionList){
            listQ.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listQ);
        forgot_selectQuestion.setItems(listData);

    }


    public void register() {
        alertMessage alert = new alertMessage();

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        String numberRegex = "\\d{8}";
        String genderRegex = "^(Male|Female)$";

        if (signup_email.getText().isEmpty() || signup_username.getText().isEmpty()
                || signup_password.getText().isEmpty() || signup_confirmpass.getText().isEmpty()
                || signup_selectQuestion.getSelectionModel().getSelectedItem() == null
                || signup_answer.getText().isEmpty()) {
            alert.errorMessage("All fields are necessary to be filled");
        } else if (!signup_password.getText().equals(signup_confirmpass.getText())) {
            alert.errorMessage(("Password does not match"));
        } else if (signup_password.getText().length() < 8) {
            alert.errorMessage("Invalid Password, at least 8 characters needed");
        } else if (!signup_email.getText().matches(emailRegex)) {
            alert.errorMessage("Invalid Email syntax");
        } else if (!signup_number.getText().matches(numberRegex)) {
            alert.errorMessage("Invalid Phone Number (8 digits required)");
        } else if (!signup_gender.getText().matches(genderRegex)) {
            alert.errorMessage("Invalid Gender (Male or Female only)");
        } else {
            try {
                User newUser = new User(signup_username.getText(),
                        signup_lastname.getText(),
                        signup_email.getText(),
                        signup_gender.getText(),
                        signup_number.getText(),
                        signup_password.getText(),
                        signup_confirmpass.getText(),
                        (String) signup_selectQuestion.getSelectionModel().getSelectedItem(),
                        signup_answer.getText());

                boolean insertSuccess = us.insert(newUser);

                if (insertSuccess) {
                    alert.successMessage("Registration Successful!");
                    System.out.println("Registration Successful!");
                    registerClearFields();
                    signup_form.setVisible(false);
                    login_form.setVisible(true);
                } else {
                    System.err.println("Error occurred during registration");
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }


    public void registerClearFields(){
        signup_username.setText("");
        signup_lastname.setText("");
        signup_email.setText("");
        signup_gender.setText("");
        signup_number.setText("");
        signup_password.setText("");
        signup_confirmpass.setText("");
        signup_selectQuestion.getSelectionModel().clearSelection();
        signup_answer.setText("");
    }


    public void changePassword(){
        alertMessage alert = new alertMessage();

        if(changePass_password.getText().isEmpty()|| changePass_confirmPass.getText().isEmpty()){
            alert.errorMessage("please fill all blank fields");
        }else if(!changePass_password.getText().equals(changePass_confirmPass.getText())){
            alert.errorMessage("Password does not match");

        } else if (changePass_password.getText().length()<8) {
            alert.errorMessage("Invalid Password, at least 8 characters needed");


        }else {
            String updateData ="UPDATE user SET password=? "+"WHERE first_name='"+forgot_username.getText()+"'";

            connect = DbConnection.getInstance().getCnx();;

            try{
                prepare=connect.prepareStatement(updateData);
                prepare.setString(1,changePass_password.getText());
                prepare.executeUpdate();
                alert.successMessage("Successfully changed Password");
                signup_form.setVisible(false);
                login_form.setVisible(true);
                forgot_form.setVisible(false);
                changePass_form.setVisible(false);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void switchForm(ActionEvent event){

        if(event.getSource()== signup_loginaccount || event.getSource()== forgot_backBtn){
            signup_form.setVisible(false);
            login_form.setVisible(true);
            forgot_form.setVisible(false);
            changePass_form.setVisible(false);
        } else if (event.getSource()==login_createAccount) {
            signup_form.setVisible(true);
            login_form.setVisible(false);
            forgot_form.setVisible(false);
            changePass_form.setVisible(false);
        }else if (event.getSource()==login_forgotpassword) {
            signup_form.setVisible(false);
            login_form.setVisible(false);
            forgot_form.setVisible(true);
            changePass_form.setVisible(false);

            forgotListQuestions();
        } else if (event.getSource()== changePass_backBtn) {
            signup_form.setVisible(false);
            login_form.setVisible(false);
            forgot_form.setVisible(true);
            changePass_form.setVisible(false);


        }
    }


    private String[] questionList = { "what is your favourite food?", "What is your favourite color?",
            "what is the name of your pet?" ,"What is your favourite sport? "};
    public void questions(){
        List<String> listQ = new ArrayList<>();

        for (String data : questionList){
            listQ.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(listQ);
        signup_selectQuestion.setItems(listData);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        questions();
        forgotListQuestions();
        populateRoleChoiceBox();

    }
}
