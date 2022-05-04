package ru.nsu.fit.gemuev.server;

import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.gemuev.util.AbstractSenderListenerFactory;
import ru.nsu.fit.gemuev.util.serializable.SerializableSenderListenerFactory;
import ru.nsu.fit.gemuev.client.events.*;
import ru.nsu.fit.gemuev.util.Event;
import ru.nsu.fit.gemuev.client.events.SuccessLoginResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import org.apache.log4j.Logger;


public class Server {

    private static class LazyHolder{
        static final Server INSTANCE = new Server();
    }

    public static Server getInstance(){
        return LazyHolder.INSTANCE;
    }

    private final Logger log = Logger.getLogger(Server.class);

    private final int port;
    private final int maxUsers;
    private final int lastMessagesCount;
    private final int timeout;
    private final AbstractSenderListenerFactory senderListenerFactory;
    private final List<User> users;
    private final List<Message> messages;
    private Thread timeoutDemon;
    private final List<Thread> requestHandlers;


    public int getPort(){
        return port;
    }

    public int getTimeout(){return timeout;}

    public Logger getLogger(){
        return log;
    }

    private Server() {
        users = new ArrayList<>();
        messages = new ArrayList<>();
        requestHandlers = new ArrayList<>();
        senderListenerFactory = SerializableSenderListenerFactory.getInstance();

        try (InputStream inputStream = Server.class.getResourceAsStream("/server_config.properties")){

            var tmp = new Properties();
            tmp.load(inputStream);
            String strPort = tmp.getProperty("port");
            String strMaxUsers = tmp.getProperty("max_count_of_users");
            String strMaxCntMessages = tmp.getProperty("last_messages_count");
            String strTimeout = tmp.getProperty("user_time_out");

            port = Integer.parseInt(strPort);
            maxUsers = Integer.parseInt(strMaxUsers);
            lastMessagesCount = Integer.parseInt(strMaxCntMessages);
            timeout = Integer.parseInt(strTimeout);

            log.info("Server initialize");

        } catch (IOException | NullPointerException e) {
            log.info("Server initialization fail" + e.getMessage());
            throw new LoadPropertiesException("Server initialization fail", e);
        }
    }


    public void broadcastEvent(@NotNull Event event){
        for(User user : users) {
            sendEvent(event, user);
        }
    }


    public void sendEvent(@NotNull Event event, @NotNull User user){
        try {
            var eventSender = senderListenerFactory.eventSenderInstance();
            eventSender.sendEvent(event, user.socket());
            log.info("Send event " + event + " to user " + user.name());
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }


    public synchronized void debugPrintAllUsers(){
        for(User user : users){
            log.debug(user.name() + " - online");
        }
    }


    public void newMessageRequest(Socket socket, String message){

        var opt = findUserBySocket(socket);
        if(opt.isEmpty()){
            return;
        }
        User user = opt.get();
        user.updateActivity();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        var date = dtf.format(LocalTime.now());
        Message message_ = new Message(user.name(), message, date);

        synchronized(this) {
            messages.add(message_);
        }
        broadcastEvent(new MessageEvent(message_));
    }


    public synchronized void userLogin(Socket socket, String userName){

        User user = new User(userName, socket);

        if(users.size()==maxUsers){
            CompletableFuture.runAsync(() ->
                    sendEvent(new FailLoginEvent("Reached max count of users"), user));
            return;
        }

        users.add(user);
        log.info("User login: " + userName);
        //debugPrintAllUsers();

        CompletableFuture.runAsync(() -> {
            sendEvent(new SuccessLoginResponse(42), user);
            broadcastEvent(new ChangeOnlineUsersEvent(onlineUserList()));
        });
    }


    public synchronized void userLogout(Socket socket){

        var opt= findUserBySocket(socket);
        if(opt.isEmpty()){
            log.error("Unknown user " + socket);
            return;
        }

        log.info("User logout: " + opt.get().name());
        users.remove(opt.get());
        //debugPrintAllUsers();

        CompletableFuture.runAsync(() -> {
            try {
                socket.close();
            }
            catch(IOException ignore){}
            broadcastEvent(new ChangeOnlineUsersEvent(onlineUserList()));
        });
    }


    public synchronized void lastMessagesListRequest(Socket socket){

        var opt = findUserBySocket(socket);
        if(opt.isPresent()){
            User user = opt.get();
            ArrayList<Message> list = new ArrayList<>();
            int startInd = messages.size()-Math.min(lastMessagesCount, messages.size());
            for(int i=startInd; i<messages.size(); ++i){
                list.add(messages.get(i));
            }

            sendEvent(new LastMessagesListEvent(list), user);
        }
        else {
            log.error("Unknown user " + socket);
        }
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
    

    public synchronized void deleteNonActivityUsers(){

        ArrayList<User> tmp = new ArrayList<>(users);

        for(User user : tmp){
            if(user.isTimeOut(timeout)){
                sendEvent(new FailLoginEvent("you are timed out"), user);
                userLogout(user.socket());
                log.info("User disconnected by timeout: " + user.name());
            }
        }
    }


    public void launch(){

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            timeoutDemon = new Thread(new TimeoutDemon(this));
            timeoutDemon.start();
            log.info("Server launched");

            while(!Thread.interrupted()) {
                Socket socket = serverSocket.accept();

                log.info("Got a connection with: " + socket);

                var requestListener = senderListenerFactory.requestListenerInstance();

                Thread requestHandler = new Thread(new RequestHandler(this, socket, requestListener));
                requestHandlers.add(requestHandler);
                requestHandler.start();
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public synchronized void stop(){
        for(User user : users){
            sendEvent(new FailLoginEvent("Server stopped"), user);
        }
        users.clear();

        timeoutDemon.interrupt();
        for(Thread thread : requestHandlers){
            thread.interrupt();
        }
        requestHandlers.clear();
    }


    public static void main(String[] args) {
        Server.getInstance().launch();
    }

}
