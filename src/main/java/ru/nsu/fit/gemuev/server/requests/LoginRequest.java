package ru.nsu.fit.gemuev.server.requests;

import ru.nsu.fit.gemuev.util.Request;
import ru.nsu.fit.gemuev.server.Server;

import java.net.Socket;

public record LoginRequest(String userName, String requestType) implements Request {

    public LoginRequest(String userName){
        this(userName, "Login");
    }

    @Override
    public void handleRequest(Server server, Socket socket) {
        server.userLogin(socket, userName);
    }
}
