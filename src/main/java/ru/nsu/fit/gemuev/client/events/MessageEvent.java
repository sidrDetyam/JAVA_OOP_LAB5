package ru.nsu.fit.gemuev.client.events;

import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.gemuev.client.Model;

public record MessageEvent(String userName, String message) implements Event {

    @Override
    public void handleEvent(@NotNull Model model) {
        model.newMessage(userName, message);
    }
}
