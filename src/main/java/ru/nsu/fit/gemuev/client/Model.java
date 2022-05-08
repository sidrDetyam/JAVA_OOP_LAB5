
package ru.nsu.fit.gemuev.client;

import ru.nsu.fit.gemuev.client.views.LoginView;
import ru.nsu.fit.gemuev.client.views.MainView;
import ru.nsu.fit.gemuev.server.Message;
import ru.nsu.fit.gemuev.util.AbstractSenderListenerFactory;
import ru.nsu.fit.gemuev.util.Request;
import ru.nsu.fit.gemuev.server.Server;
import ru.nsu.fit.gemuev.server.requests.*;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;


public class Model{

    private static class Holder{
        static final Model INSTANCE = new Model();
    }

    public static Model getInstance(){
        return Holder.INSTANCE;
    }


    private Socket socket;
    private final AbstractSenderListenerFactory senderListenerFactory =
            AbstractSenderListenerFactory.of();
    private final Executor threadPool = ForkJoinPool.commonPool();

    private LoginView loginView;
    private MainView mainView;
    private boolean isLogin;
    private int serverTimeout = 30000;
    private String userName;


    public synchronized String getUserName(){return userName;}

    public synchronized int getServerTimeout(){return serverTimeout;}

    public synchronized LoginView loginView(){return loginView;}

    public synchronized boolean isLogin(){
        return isLogin;
    }

    public synchronized void setLoginView(LoginView loginView){
        this.loginView = loginView;
    }

    public synchronized void setMainView(MainView mainView){
        this.mainView = mainView;
    }

    private Model(){}

    public synchronized void sendRequest(Request request){
        var requestSender = senderListenerFactory.requestSenderInstance();
        CompletableFuture.runAsync(() -> {
            try {
                requestSender.sendRequest(socket, request);
            } catch (IOException ignore) {}
        }, threadPool);
    }


    public synchronized void sendLoginRequest(String name){
        if(connect()) {
            userName = name;
            sendRequest(new LoginRequest(name));
        }
    }


    public synchronized void acceptLoginResponse(int serverTimeout){
        loginView.closeForm();
        mainView.openForm();
        this.serverTimeout = serverTimeout;
        isLogin = true;
        getLastMessages();
        getOnlineUserList();
    }


    public synchronized void disconnect(){
        try {
            isLogin = false;
            mainView.closeForm();
            loginView.openForm();
            socket.close();
        }
        catch(IOException ignore) {}
    }


    public synchronized void logoutEvent(String cause){
        disconnect();
    }


    public synchronized boolean connect(){
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


    public synchronized void sendProveRequest(){sendRequest(new ProbeRequest());}

    public synchronized void getOnlineUserList(){sendRequest(new OnlineUsersListRequest());}

    public synchronized void sendLogoutRequest(){
        sendRequest(new LogoutRequest());
    }

    public synchronized void sendNewMessage(String message){
        sendRequest(new MessageRequest(message));
    }

    public synchronized void newMessage(Message message){
        mainView.addNewMessage(message);
    }

    public synchronized void getLastMessages(){
        sendRequest(new LastMessagesListRequest());
    }

    public synchronized void disconnectTest(){sendRequest(new TestTimeoutRequest());}

    public synchronized void showListOfMessages(List<Message> list){
        for(Message message : list){
            newMessage(message);
        }
    }


    public synchronized void updateUsersOnline(List<String> userNames){
        mainView.updateUsersOnline(userNames);
    }

    public synchronized void close(){
        try {
            if(socket!=null && socket.isConnected()) {
                socket.close();
            }
        }
        catch(IOException ignore){}
        System.exit(0);
    }
}
