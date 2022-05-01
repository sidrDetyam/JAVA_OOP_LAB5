

module OOP_LAB {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.annotations;

    opens ru.nsu.fit.gemuev.client to javafx.fxml;
    exports ru.nsu.fit.gemuev.client;
    exports ru.nsu.fit.gemuev.client.controllers;
    opens ru.nsu.fit.gemuev.client.controllers to javafx.fxml;
    exports ru.nsu.fit.gemuev.client.events;
    opens ru.nsu.fit.gemuev.client.events to javafx.fxml;
    exports ru.nsu.fit.gemuev.server.requests;
    exports ru.nsu.fit.gemuev.server;
    opens ru.nsu.fit.gemuev.server.requests to javafx.fxml;
}
