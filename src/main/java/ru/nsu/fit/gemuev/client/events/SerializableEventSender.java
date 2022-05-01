package ru.nsu.fit.gemuev.client.events;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SerializableEventSender implements EventSender {

    @Override
    public void sendEvent(@NotNull Event event, @NotNull Socket socket) throws IOException {

        ///TODO: its very bad
        synchronized(socket) {
            var out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            out.writeObject(event);
            out.flush();
        }
    }
}
