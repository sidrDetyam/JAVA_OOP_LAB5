package ru.nsu.fit.gemuev.server.requests;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.Socket;

public interface RequestSender {

    void sendRequest(@NotNull Socket socket, @NotNull Request request) throws IOException;
}
