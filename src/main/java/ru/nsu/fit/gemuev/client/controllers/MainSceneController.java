package ru.nsu.fit.gemuev.client.controllers;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ru.nsu.fit.gemuev.client.Client;
import org.jetbrains.annotations.NotNull;


public class MainSceneController {

    @FXML
    private TextArea messagesArea;
    @FXML
    private TextArea usersOnline;
    @FXML
    private Button button;
    @FXML
    private TextField textField;


    ///TODO пиздец
    public MainSceneController(){
        Client.primaryController = this;
    }


    @FXML
    private void sendNewMessage(){
        String s = textField.getText();
        textField.setText("");
        Client.model.sendNewMessage(s);
    }


    public synchronized void addNewMessage(String name, String message){
        messagesArea.setText(messagesArea.getText() + "\n" + name + ": " + message);
    }

    public synchronized void updateUsersOnline(@NotNull List<String> userNames){
        usersOnline.setText("");
        for(String userName : userNames){
            usersOnline.setText(usersOnline.getText() + "\n" + userName);
        }
    }

}
