package ru.nsu.fit.gemuev.server.requests;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class SerializableRequestSender implements RequestSender {

    @Override
    public void sendRequest(@NotNull Socket socket, @NotNull Request request) throws IOException {

        synchronized(socket) {
            var out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            out.writeObject(request);
            out.flush();
        }
    }

}
