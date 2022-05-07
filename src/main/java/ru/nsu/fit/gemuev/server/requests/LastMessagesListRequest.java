package ru.nsu.fit.gemuev.server.requests;

import ru.nsu.fit.gemuev.server.Server;
import ru.nsu.fit.gemuev.util.Request;

import java.net.Socket;

public record LastMessagesListRequest(String requestType) implements Request {

    public LastMessagesListRequest(){
        this("LastMessagesList");
    }

    @Override
    public void handleRequest(Server server, Socket socket) {
        server.lastMessagesListRequest(socket);
    }
}
