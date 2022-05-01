package ru.nsu.fit.gemuev.server;

import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.gemuev.client.events.*;
import ru.nsu.fit.gemuev.server.requests.RequestListener;
import ru.nsu.fit.gemuev.server.requests.SerializableRequestListener;
import ru.nsu.fit.gemuev.client.events.Event;
import ru.nsu.fit.gemuev.client.events.SerializableEventSender;
import ru.nsu.fit.gemuev.client.events.SuccessLoginResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

public class Server {

    private static class LazyHolder{
        static final Server INSTANCE = new Server();
    }

    public static Server getInstance(){
        return LazyHolder.INSTANCE;
    }


    private final int port;
    private final int maxUsers;
    //event sender should be async-safe
    private final EventSender eventSender;
    private final RequestListener requestListener;
    private final List<User> users;

    public RequestListener getRequestListener() {
        return requestListener;
    }

    public int getPort(){
        return port;
    }

    private Server() {
        users = new ArrayList<>();
        eventSender = new SerializableEventSender();
        requestListener = new SerializableRequestListener();

        try (InputStream inputStream = Server.class.getResourceAsStream("/server_config.properties")){

            var tmp = new Properties();
            tmp.load(inputStream);
            String strPort = tmp.getProperty("port");
            String strMaxUsers = tmp.getProperty("max_count_of_users");
            if(strMaxUsers==null || strPort==null){
                throw new LoadPropertiesException("Server initialization fail: wrong config");
            }
            port = Integer.parseInt(strPort);
            maxUsers = Integer.parseInt(strMaxUsers);

        } catch (IOException e) {
            throw new LoadPropertiesException("Server initialization fail", e);
        }
    }


    public void broadcast(@NotNull Event event){
        for(User user : users) {
            sendEvent(event, user);
        }
    }


    public void sendEvent(@NotNull Event event, @NotNull User user){
        try {
            eventSender.sendEvent(event, user.socket());
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }


    public synchronized void debugPrintAllUsers(){
        for(User user : users){
            System.out.println(" --- " + user.name());
        }
    }


    public synchronized void userLogin(User user){

        if(users.size()==maxUsers){
            CompletableFuture.runAsync(() ->
                    sendEvent(new FailLoginEvent("Reached max count of users"), user));
            return;
        }

        users.add(user);
        System.out.println("user login: " + user.name());
        debugPrintAllUsers();

        CompletableFuture.runAsync(() -> {
            sendEvent(new SuccessLoginResponse(42), user);
            broadcast(new ChangeOnlineUsersEvent(onlineUserList()));
        });
    }


    public synchronized void userLogout(Socket socket){

        var opt= findUserBySocket(socket);
        if(opt.isPresent()){
            System.out.println("user logout: " + opt.get().name());
            users.remove(opt.get());
            debugPrintAllUsers();

            CompletableFuture.runAsync(() -> broadcast(new ChangeOnlineUsersEvent(onlineUserList())));
        }

        try {
            socket.close();
        }
        catch(IOException ignore){}
    }
    

    public synchronized Optional<User> findUserBySocket(Socket socket){
        for(User user : users){
            if(user.socket().equals(socket)){
                return Optional.of(user);
            }
        }

        return Optional.empty();
    }


    public synchronized ArrayList<String> onlineUserList(){

        ArrayList<String> usersOnline = new ArrayList<>();
        for(User user : users){
            usersOnline.add(user.name());
        }
        return usersOnline;
    }
    

    public void launch(){

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while(!Thread.interrupted()) {
                Socket socket = serverSocket.accept();
                CompletableFuture.runAsync(new RequestHandler(this, socket));
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        Server.getInstance().launch();
    }

}
