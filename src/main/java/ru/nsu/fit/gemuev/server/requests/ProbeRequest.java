package ru.nsu.fit.gemuev.server.requests;

import ru.nsu.fit.gemuev.server.Server;
import ru.nsu.fit.gemuev.util.Request;

import java.net.Socket;

public record ProbeRequest(String requestType) implements Request {

    public ProbeRequest(){
        this("Probe");
    }

    @Override
    public void handleRequest(Server server, Socket socket) {
        server.updateUserActivity(socket);
    }
}
