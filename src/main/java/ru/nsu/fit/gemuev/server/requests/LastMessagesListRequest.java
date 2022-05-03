package ru.nsu.fit.gemuev.server.requests;

import ru.nsu.fit.gemuev.server.Server;
import ru.nsu.fit.gemuev.util.Request;

import java.net.Socket;

public class LastMessagesListRequest implements Request {

    @Override
    public void handleRequest(Server server, Socket socket) {
        server.lastMessagesListRequest(socket);
    }
}
