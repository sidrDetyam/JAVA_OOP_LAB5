package ru.nsu.fit.gemuev.server;

import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

public class User {

    private final String name;
    private final Socket socket;
    private final AtomicLong lastActivity;
    private final ExecutorService eventThreadPool;

    public User(String name, Socket socket, int nThreads){
        this.name = name;
        this.socket = socket;
        lastActivity = new AtomicLong(System.currentTimeMillis());
        eventThreadPool = Executors.newFixedThreadPool(nThreads);
    }

    public String name(){
        return this.name;
    }

    public Socket socket(){
        return this.socket;
    }

    public ExecutorService eventThreadPool(){return eventThreadPool;}

    public void updateActivity(){
        lastActivity.set(System.currentTimeMillis());
    }

    public boolean isHalfTimeOut(long halfTimeout){
        return System.currentTimeMillis() - lastActivity.get() > halfTimeout;
    }

    public boolean isTimeOut(long timeout){
        return System.currentTimeMillis() - lastActivity.get() > timeout;
    }

    //for debug and demonstration
    private boolean isDebugDisconnected;
    public void disconnect(){
        isDebugDisconnected = true;
    }
    public boolean isDebugDisconnected(){return isDebugDisconnected;}
}
