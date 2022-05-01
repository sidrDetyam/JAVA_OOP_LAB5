package ru.nsu.fit.gemuev.server.requests;

import ru.nsu.fit.gemuev.server.Server;

import java.io.Serializable;
import java.net.Socket;

public interface Request extends Serializable {

    void handleRequest(Server server, Socket socket);

}
