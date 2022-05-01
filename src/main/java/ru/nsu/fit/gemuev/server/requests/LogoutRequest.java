package ru.nsu.fit.gemuev.server.requests;

import ru.nsu.fit.gemuev.server.Server;

import java.net.Socket;

public class LogoutRequest implements Request {

    public LogoutRequest(){

    }

    @Override
    public void handleRequest(Server server, Socket socket) {
        server.userLogout(socket);
    }
}
