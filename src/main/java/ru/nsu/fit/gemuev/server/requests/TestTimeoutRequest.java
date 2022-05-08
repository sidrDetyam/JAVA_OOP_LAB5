package ru.nsu.fit.gemuev.server.requests;

import ru.nsu.fit.gemuev.server.Server;
import ru.nsu.fit.gemuev.util.Request;

import java.net.Socket;

public record TestTimeoutRequest(String requestType) implements Request {

    public TestTimeoutRequest(){
        this("TestTimeout");
    }

    @Override
    public void handleRequest(Server server, Socket socket) {
        server.testTimeOut(socket);
    }
}
