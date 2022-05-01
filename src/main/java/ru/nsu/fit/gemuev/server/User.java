package ru.nsu.fit.gemuev.server;

import java.net.Socket;

public record User(String name, Socket socket) {}
