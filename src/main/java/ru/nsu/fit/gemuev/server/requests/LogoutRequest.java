package ru.nsu.fit.gemuev.server.requests;

import ru.nsu.fit.gemuev.util.Request;
import ru.nsu.fit.gemuev.server.Server;

import java.net.Socket;

public record LogoutRequest(String requestType) implements Request {

    public LogoutRequest(){
        this("Logout");
    }

    @Override
    public void handleRequest(Server server, Socket socket) {
        server.userLogout(socket);
    }
}
