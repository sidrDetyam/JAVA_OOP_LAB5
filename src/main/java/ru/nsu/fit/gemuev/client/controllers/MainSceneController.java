
package ru.nsu.fit.gemuev.client.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.gemuev.client.Client;
import ru.nsu.fit.gemuev.client.Model;
import ru.nsu.fit.gemuev.client.views.MainView;
import ru.nsu.fit.gemuev.server.Message;


public class MainSceneController implements MainView, Initializable {

    @FXML
    private Menu logoutButton;
    @FXML
    private MenuBar menu;
    @FXML
    private TextArea messagesArea;
    @FXML
    private TextArea usersOnline;
    @FXML
    private Button sendButton;
    @FXML
    private TextField textField;

    private static final Model model = Model.getInstance();


    @FXML
    private void logoutButtonClick(ActionEvent e){
        model.sendLogoutRequest();
    }

    @FXML
    private void closeButtonClick(ActionEvent e){
        model.close();
    }

    @FXML
    private void disconnectTestButtonClick(ActionEvent e){
        model.disconnectTest();
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
    public void addNewMessage(@NotNull Message message){
        messagesArea.appendText(
                "\n[%s] %s: %s".formatted(message.date(), message.userName(), message.message()));
    }

    @Override
    public void updateUsersOnline(@NotNull List<String> userNames){
        usersOnline.setText("");
        for(String userName : userNames){
            usersOnline.appendText(userName+"\n");
        }
    }

    @Override
    public void openForm() {
        Client.setMainScene();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        model.setMainView(this);
    }

}
