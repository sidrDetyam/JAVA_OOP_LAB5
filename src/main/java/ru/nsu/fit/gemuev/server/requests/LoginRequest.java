package ru.nsu.fit.gemuev.server.requests;

import ru.nsu.fit.gemuev.server.Server;
import ru.nsu.fit.gemuev.server.User;

import java.net.Socket;

public record LoginRequest(String userName) implements Request {

    @Override
    public void handleRequest(Server server, Socket socket) {

        User user = new User(userName, socket);
        server.userLogin(user);
    }
}
