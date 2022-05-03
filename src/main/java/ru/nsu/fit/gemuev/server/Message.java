package ru.nsu.fit.gemuev.server;

import java.io.Serializable;

public record Message(String userName, String message, String date) implements Serializable {
}
