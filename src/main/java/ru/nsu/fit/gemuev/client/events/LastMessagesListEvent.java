package ru.nsu.fit.gemuev.client.events;

import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.gemuev.client.Model;
import ru.nsu.fit.gemuev.server.Message;
import ru.nsu.fit.gemuev.util.Event;

import java.util.ArrayList;


public record LastMessagesListEvent(ArrayList<Message> messages, String eventType) implements Event {

    public LastMessagesListEvent(ArrayList<Message> messages){
        this(messages, "LastMessagesList");
    }

    @Override
    public void handleEvent(@NotNull Model model) {
        model.showListOfMessages(messages);
    }

    @Override
    public String toString(){
        return eventType;
    }
}
