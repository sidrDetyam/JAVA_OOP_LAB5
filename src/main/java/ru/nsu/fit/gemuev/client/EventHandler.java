package ru.nsu.fit.gemuev.client;

import ru.nsu.fit.gemuev.client.events.FailLoginEvent;
import ru.nsu.fit.gemuev.client.events.SuccessLoginResponse;
import ru.nsu.fit.gemuev.util.Event;
import ru.nsu.fit.gemuev.util.EventListener;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;


public record EventHandler(Socket socket, Model model, EventListener eventListener)
        implements Runnable{

    @Override
    public void run() {

        while(!Thread.interrupted()){

            try{
                Event event = eventListener.nextEvent(socket, model.getServerTimeout());
                System.out.println(event);
                if(model.isLogin()
                        || event instanceof SuccessLoginResponse
                        || event instanceof FailLoginEvent){

                    event.handleEvent(model);
                }

            } catch (IOException e) {
                model.disconnect();
                if(e instanceof SocketTimeoutException) {
                    model.loginView().showLogoutCause(e.getMessage());
                }
                break;
            }
        }
    }
}
