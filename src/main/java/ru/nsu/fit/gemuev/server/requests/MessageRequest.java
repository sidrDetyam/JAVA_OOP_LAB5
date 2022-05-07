package ru.nsu.fit.gemuev.server.requests;

import ru.nsu.fit.gemuev.util.Request;
import ru.nsu.fit.gemuev.server.Server;

import java.net.Socket;

public record MessageRequest(String message, String requestType) implements Request {

    public MessageRequest(String message){
        this(message, "Message");
    }

    @Override
    public void handleRequest(Server server, Socket socket) {
        server.newMessageRequest(socket, message);
    }
}
