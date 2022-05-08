package ru.nsu.fit.gemuev.client.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import ru.nsu.fit.gemuev.client.Client;
import ru.nsu.fit.gemuev.client.views.LoginView;
import ru.nsu.fit.gemuev.client.Model;

import java.net.URL;
import java.util.ResourceBundle;


public class LoginSceneController implements LoginView, Initializable {

    @FXML
    private TextField loginField;
    @FXML
    private Button loginButton;

    private static final Model model = Model.getInstance();

    public LoginSceneController(){
        ///TODO ref leak
        model.setLoginView(this);
    }

    @FXML
    private void loginButtonPress() {
        if(!loginField.getText().equals("")) {
            model.sendLoginRequest(loginField.getText());
        }
    }

    @FXML
    private void enterTyped(KeyEvent e){
        if(e.getCode().equals(KeyCode.ENTER)) {
            loginButtonPress();
        }
    }

    @Override
    public void openForm() {
        Client.setLoginScene();
    }


    @Override
    public void showLogoutCause(String cause){

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Logout");
            alert.setHeaderText(cause);
            alert.showAndWait();
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        model.setLoginView(this);
        if(model.getUserName()!=null){
            loginField.setText(model.getUserName());
        }
    }
}
