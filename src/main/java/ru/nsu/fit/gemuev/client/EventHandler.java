package ru.nsu.fit.gemuev.client;

import ru.nsu.fit.gemuev.client.events.Event;
import ru.nsu.fit.gemuev.client.events.EventListener;

import java.io.IOException;
import java.net.Socket;


public record EventHandler(Socket socket, Model model, EventListener eventListener) implements Runnable{


    @Override
    public void run() {

        while(!Thread.interrupted()){

            try{
                var opt = eventListener.nextEvent(socket);
                if(opt.isEmpty()){
                    throw new IOException("Something goes wrong");
                }
                Event event = opt.get();

                System.out.println("New event! " + event);
                event.handleEvent(model);

            } catch (IOException e) {
                e.printStackTrace();
                model.disconnect();
                break;
            }
        }
    }
}
