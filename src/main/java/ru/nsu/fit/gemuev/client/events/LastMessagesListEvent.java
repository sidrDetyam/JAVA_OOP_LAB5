package ru.nsu.fit.gemuev.client.events;

import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.gemuev.client.Model;
import ru.nsu.fit.gemuev.server.Message;
import ru.nsu.fit.gemuev.util.Event;

import java.util.ArrayList;

public record LastMessagesListEvent(ArrayList<Message> messages) implements Event {

    @Override
    public void handleEvent(@NotNull Model model) {
        model.showListOfMessages(messages);
    }
}
