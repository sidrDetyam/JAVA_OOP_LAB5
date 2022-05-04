package ru.nsu.fit.gemuev.client.events;

import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.gemuev.util.Event;
import ru.nsu.fit.gemuev.client.Model;

public record FailLoginEvent(@NotNull String cause) implements Event {

    @Override
    public void handleEvent(@NotNull Model model) {
        model.logoutEvent(cause);
    }
}
