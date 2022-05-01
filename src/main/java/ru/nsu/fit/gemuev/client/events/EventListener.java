package ru.nsu.fit.gemuev.client.events;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.Socket;
import java.util.Optional;

public interface EventListener {

    Optional<Event> nextEvent(@NotNull Socket socket) throws IOException;
}
