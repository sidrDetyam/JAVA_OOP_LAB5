package ru.nsu.fit.gemuev.client;

import ru.nsu.fit.gemuev.client.events.EventListener;
import ru.nsu.fit.gemuev.client.events.SerializableEventListener;
import ru.nsu.fit.gemuev.server.Server;
import ru.nsu.fit.gemuev.server.requests.*;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Model {

    private Socket socket;
    private final RequestSender requestSender;
    private final EventListener eventListener;

    public Model(){
        requestSender = new SerializableRequestSender();
        eventListener = new SerializableEventListener();
    }


    public void sendLoginRequest(String name){

        try {
            if (socket == null || socket.isClosed() || !socket.isConnected()) {
                connect();
            }

            CompletableFuture.runAsync(() -> {
                try {
                    requestSender.sendRequest(socket, new LoginRequest(name));
                } catch (IOException ignore) {}
            });
        }
        catch (IOException ignore){
            Client.setRoot("/LoginScene.fxml");
        }
    }


    public void acceptLoginResponse(boolean isSuccess){
        if(isSuccess){
            Client.setRoot("/MainScene.fxml");
        }
        else{
            disconnect();
        }
    }


    public void sendLogoutRequest(){
        CompletableFuture.runAsync(() -> {
            try {
                requestSender.sendRequest(socket, new LogoutRequest());
            } catch (IOException ignore) {}
        });
    }


    public void sendNewMessage(String message){
        CompletableFuture.runAsync(() -> {
            try {
                requestSender.sendRequest(socket, new MessageRequest(message));
            } catch (IOException ignore) {}
        });
    }


    public void connect() throws IOException {
        socket = new Socket("localhost", Server.getInstance().getPort());
        EventHandler eventHandler = new EventHandler(socket, this, eventListener);
        CompletableFuture.runAsync(eventHandler);
    }


    public void disconnect(){
        try {
            Client.setRoot("/LoginScene.fxml");
            socket.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }


    public void newMessage(String name, String message){
        Client.primaryController.addNewMessage(name, message);
    }


    public void updateUsersOnline(List<String> userNames){
        Client.primaryController.updateUsersOnline(userNames);
    }

}
