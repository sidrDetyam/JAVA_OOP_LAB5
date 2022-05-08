package ru.nsu.fit.gemuev.server.requests;

import ru.nsu.fit.gemuev.server.Server;
import ru.nsu.fit.gemuev.util.Request;

import java.net.Socket;

public record OnlineUsersListRequest(String requestType) implements Request {

    public OnlineUsersListRequest(){
        this("OnlineUsersList");
    }

    @Override
    public void handleRequest(Server server, Socket socket) {
        server.getUserList(socket);
    }
}
