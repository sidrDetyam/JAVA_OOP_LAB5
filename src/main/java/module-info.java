

module OOP_LAB {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.annotations;
    requires log4j;
    requires com.google.gson;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;

    opens ru.nsu.fit.gemuev.client to javafx.fxml;
    exports ru.nsu.fit.gemuev.client;
    exports ru.nsu.fit.gemuev.client.controllers;
    opens ru.nsu.fit.gemuev.client.controllers to javafx.fxml;
    exports ru.nsu.fit.gemuev.client.events;
    opens ru.nsu.fit.gemuev.client.events to javafx.fxml, com.google.gson, com.fasterxml.jackson.databind;
    exports ru.nsu.fit.gemuev.server.requests;
    exports ru.nsu.fit.gemuev.server;
    opens ru.nsu.fit.gemuev.server to com.google.gson, com.fasterxml.jackson.databind;
    opens ru.nsu.fit.gemuev.server.requests to javafx.fxml, com.google.gson, com.fasterxml.jackson.databind;
    exports ru.nsu.fit.gemuev.util;
    exports ru.nsu.fit.gemuev.util.serializable;
    opens ru.nsu.fit.gemuev.util.serializable to javafx.fxml;
    opens ru.nsu.fit.gemuev.util to com.fasterxml.jackson.databind, com.google.gson, javafx.fxml;

}

