package ru.nsu.fit.gemuev.client.controllers;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.gemuev.client.Client;
import ru.nsu.fit.gemuev.client.Model;
import ru.nsu.fit.gemuev.client.MainView;


public class MainSceneController implements MainView {

    @FXML
    private TextArea messagesArea;
    @FXML
    private TextArea usersOnline;
    @FXML
    private Button sendButton;
    @FXML
    private TextField textField;

    private static final Model model = Model.getInstance();

    public MainSceneController(){
        ///TODO reference leak from constructor
        model.setMainView(this);
    }


    @FXML
    private void sendButtonClick(){
        String s = textField.getText();
        if(!s.equals("")) {
            textField.setText("");
            model.sendNewMessage(s);
        }
    }

    @FXML
    private void enterTyped(KeyEvent e){
        if(e.getCode().equals(KeyCode.ENTER)) {
            sendButtonClick();
        }
    }

    @Override
    public synchronized void addNewMessage(@NotNull String name, @NotNull String message){
        messagesArea.setText(messagesArea.getText() + "\n" + name + ": " + message);
    }

    @Override
    public synchronized void updateUsersOnline(@NotNull List<String> userNames){
        usersOnline.setText("");
        for(String userName : userNames){
            usersOnline.setText(usersOnline.getText() + "\n" + userName);
        }
    }

    @Override
    public void openForm() {
        Client.setMainScene();
    }
}
