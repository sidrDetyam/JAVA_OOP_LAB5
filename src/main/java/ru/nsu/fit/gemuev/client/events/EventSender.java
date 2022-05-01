package ru.nsu.fit.gemuev.client.events;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.Socket;

public interface EventSender {

    void sendEvent(@NotNull Event event, @NotNull Socket socket) throws IOException;
}
