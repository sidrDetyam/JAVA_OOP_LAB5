package ru.nsu.fit.gemuev.server.requests;

import java.io.IOException;
import java.net.Socket;
import java.util.Optional;

public interface RequestListener {

    Optional<Request> nextRequest(Socket socket) throws IOException;
}
