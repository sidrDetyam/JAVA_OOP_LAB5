package ru.nsu.fit.gemuev.client.events;

import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.gemuev.server.Message;
import ru.nsu.fit.gemuev.util.Event;
import ru.nsu.fit.gemuev.client.Model;

public record MessageEvent(Message message, String eventType) implements Event {

    public MessageEvent(Message message){
        this(message, "Message");
    }

    @Override
    public void handleEvent(@NotNull Model model) {
        model.newMessage(message);
    }
}
