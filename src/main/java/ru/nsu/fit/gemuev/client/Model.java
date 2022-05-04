
package ru.nsu.fit.gemuev.client;

import ru.nsu.fit.gemuev.server.Message;
import ru.nsu.fit.gemuev.util.AbstractSenderListenerFactory;
import ru.nsu.fit.gemuev.util.Request;
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


    public void sendRequest(Request request){
        var requestSender = senderListenerFactory.requestSenderInstance();
        CompletableFuture.runAsync(() -> {
            try {
                requestSender.sendRequest(socket, request);
            } catch (IOException ignore) {}
        });
    }


    public void sendLoginRequest(String name){

        if(connect()) {
            sendRequest(new LoginRequest(name));
        }
    }


    public void acceptLoginResponse(){
        loginView.closeForm();
        mainView.openForm();
        getLastMessages();
    }


    public void disconnect(){
        try {
            mainView.closeForm();
            loginView.openForm();
            socket.close();
        }
        catch(IOException ignore) {}
    }


    public void logoutEvent(String cause){
        disconnect();
        loginView.showLogoutCause(cause);
    }


    public void sendLogoutRequest(){
        sendRequest(new LogoutRequest());
    }


    public void sendNewMessage(String message){
        sendRequest(new MessageRequest(message));
    }


    public boolean connect(){
        try {
            socket = new Socket("localhost", Server.getInstance().getPort());
            var eventListener = senderListenerFactory.eventListenerInstance();
            EventHandler eventHandler = new EventHandler(socket, this, eventListener);
            Thread eventHandlerThread = new Thread(eventHandler);
            eventHandlerThread.start();
            return true;
        }
        catch(IOException e){
            loginView.showLogoutCause("failed to connect");
        }
        return false;
    }



    public void newMessage(Message message){
        mainView.addNewMessage(message);
    }


    public void getLastMessages(){
        sendRequest(new LastMessagesListRequest());
    }


    public void showListOfMessages(List<Message> list){
        for(Message message : list){
            newMessage(message);
        }
    }


    public void updateUsersOnline(List<String> userNames){
        mainView.updateUsersOnline(userNames);
    }

    public void close(){
        try {
            if(socket!=null && socket.isConnected()) {
                socket.close();
            }
        }
        catch(IOException ignore){}
        System.exit(0);
    }
}
