package ru.nsu.fit.gemuev.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import ru.nsu.fit.gemuev.client.Client;


public class LoginSceneController {

    @FXML
    private TextField loginField;
    @FXML
    private Button loginButton;

    @FXML
    private void loginButtonPress(){
        Client.model.sendLoginRequest(loginField.getText());
    }
}
