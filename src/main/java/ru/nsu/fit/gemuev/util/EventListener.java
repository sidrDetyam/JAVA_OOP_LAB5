package ru.nsu.fit.gemuev.util;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.Socket;


public interface EventListener {

    Event nextEvent(@NotNull Socket socket) throws IOException;

    Event nextEvent(@NotNull Socket socket, int timeout) throws IOException;
}
