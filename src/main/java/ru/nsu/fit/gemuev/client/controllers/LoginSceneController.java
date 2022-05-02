package ru.nsu.fit.gemuev.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import ru.nsu.fit.gemuev.client.Client;
import ru.nsu.fit.gemuev.client.LoginView;
import ru.nsu.fit.gemuev.client.Model;


public class LoginSceneController implements LoginView {

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
    private void loginButtonPress(){
        model.sendLoginRequest(loginField.getText());
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
}
