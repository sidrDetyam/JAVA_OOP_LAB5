package ru.nsu.fit.gemuev.server;

import java.net.Socket;
import java.util.concurrent.atomic.AtomicLong;

public class User {

    private final String name;
    private final Socket socket;
    private final AtomicLong lastActivity;
    //for debug and demonstration
    private boolean isDebugDisconnected;

    public void disconnect(){
        isDebugDisconnected = true;
    }

    public boolean isDebugDisconnected(){return isDebugDisconnected;}

    public User(String name, Socket socket){
        this.name = name;
        this.socket = socket;
        lastActivity = new AtomicLong(System.currentTimeMillis());
    }

    public String name(){
        return this.name;
    }

    public Socket socket(){
        return this.socket;
    }

    public void updateActivity(){
        if(!isDebugDisconnected) {
            lastActivity.set(System.currentTimeMillis());
        }
    }

    public boolean isHalfTimeOut(long timeout){
        return System.currentTimeMillis() - lastActivity.get() > timeout/2;
    }

    public boolean isTimeOut(long timeout){
        return System.currentTimeMillis() - lastActivity.get() > timeout;
    }

}
