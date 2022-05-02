package ru.nsu.fit.gemuev.util;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.Socket;

public interface EventSender {

    void sendEvent(@NotNull Event event, @NotNull Socket socket) throws IOException;
}
