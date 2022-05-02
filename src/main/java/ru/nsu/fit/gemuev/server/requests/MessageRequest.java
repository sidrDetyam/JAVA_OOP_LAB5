package ru.nsu.fit.gemuev.server.requests;

import ru.nsu.fit.gemuev.util.Request;
import ru.nsu.fit.gemuev.client.events.MessageEvent;
import ru.nsu.fit.gemuev.server.User;
import ru.nsu.fit.gemuev.server.Server;

import java.net.Socket;

public record MessageRequest(String message) implements Request {

    @Override
    public void handleRequest(Server server, Socket socket) {

        var opt = server.findUserBySocket(socket);

        if(opt.isPresent()) {
            User user = opt.get();
            server.broadcast(new MessageEvent(user.name(), message));
        }
        else{
            System.out.println("Unknown user");
        }
    }
}
