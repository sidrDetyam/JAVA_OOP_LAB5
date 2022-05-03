
package ru.nsu.fit.gemuev.client;

import ru.nsu.fit.gemuev.server.Message;
import ru.nsu.fit.gemuev.util.AbstractSenderListenerFactory;
import ru.nsu.fit.gemuev.util.serializable.SerializableSenderListenerFactory;
import ru.nsu.fit.gemuev.server.Server;
import ru.nsu.fit.gemuev.server.requests.*;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CompletableFuture;


public class Model{

    private static class Holder{
        static final Model INSTANCE = new Model();
    }

    public static Model getInstance(){
        return Holder.INSTANCE;
    }


    private Socket socket;
    private final AbstractSenderListenerFactory senderListenerFactory;

    private LoginView loginView;
    private MainView mainView;

    public void setLoginView(LoginView loginView){
        this.loginView = loginView;
    }

    public void setMainView(MainView mainView){
        this.mainView = mainView;
    }


    private Model(){
        senderListenerFactory = SerializableSenderListenerFactory.getInstance();
    }


    public void sendLoginRequest(String name){

        if(connect()) {
            var requestSender = senderListenerFactory.requestSenderInstance();
            CompletableFuture.runAsync(() -> {
                try {
                    requestSender.sendRequest(socket, new LoginRequest(name));
                } catch (IOException ignore) {}
            });
        }
        else {
            loginView.openForm();
            mainView.closeForm();
        }
    }


    public void acceptLoginResponse(boolean isSuccess){
        if(isSuccess){
            loginView.closeForm();
            mainView.openForm();
            getLastMessages();
        }
        else{
            disconnect();
        }
    }


    public void sendLogoutRequest(){
        var requestSender = senderListenerFactory.requestSenderInstance();
        CompletableFuture.runAsync(() -> {
            try {
                requestSender.sendRequest(socket, new LogoutRequest());
            } catch (IOException ignore) {}
        });
    }


    public void sendNewMessage(String message){
        var requestSender = senderListenerFactory.requestSenderInstance();
        CompletableFuture.runAsync(() -> {
            try {
                requestSender.sendRequest(socket, new MessageRequest(message));
            } catch (IOException ignore) {}
        });
    }


    public boolean connect(){
        try {
            socket = new Socket("localhost", Server.getInstance().getPort());
            var eventListener = senderListenerFactory.eventListenerInstance();
            EventHandler eventHandler = new EventHandler(socket, this, eventListener);
            //TODO !demon in common pool
            CompletableFuture.runAsync(eventHandler);
            return true;
        }
        catch(IOException ignore){}
        return false;
    }


    public void disconnect(){
        try {
            loginView.openForm();
            mainView.closeForm();
            socket.close();
        }
        catch(IOException ignore) {}
    }


    public void newMessage(Message message){
        mainView.addNewMessage(message);
    }


    public void getLastMessages(){
        var requestSender = senderListenerFactory.requestSenderInstance();
        CompletableFuture.runAsync(() -> {
            try {
                requestSender.sendRequest(socket, new LastMessagesListRequest());
            } catch (IOException ignore) {}
        });
    }


    public void showListOfMessages(List<Message> list){
        for(Message message : list){
            newMessage(message);
        }
    }


    public void updateUsersOnline(List<String> userNames){
        mainView.updateUsersOnline(userNames);
    }
}
