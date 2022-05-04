package ru.nsu.fit.gemuev.client.events;

import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.gemuev.util.Event;
import ru.nsu.fit.gemuev.client.Model;

public record SuccessLoginResponse(int sessionId) implements Event {

    @Override
    public void handleEvent(@NotNull Model model) {
        model.acceptLoginResponse();
    }
}
