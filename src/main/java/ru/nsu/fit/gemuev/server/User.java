package ru.nsu.fit.gemuev.server;

import java.net.Socket;
import java.util.concurrent.atomic.AtomicLong;

public class User {

    private final String name;
    private final Socket socket;
    private final AtomicLong lastActivity;

    public User(String name, Socket socket){
        this.name = name;
        this.socket = socket;
        lastActivity = new AtomicLong(System.currentTimeMillis()/1000);
    }

    public String name(){
        return this.name;
    }

    public Socket socket(){
        return this.socket;
    }

    public void updateActivity(){
        lastActivity.set(System.currentTimeMillis()/1000);
    }

    public boolean isTimeOut(long timeout){
        return System.currentTimeMillis()/1000 - lastActivity.get() > timeout;
    }

}
