package ru.nsu.fit.gemuev.client.events;

import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.gemuev.client.Model;
import ru.nsu.fit.gemuev.util.Event;

public record ProbeEvent(String eventType) implements Event {

    public ProbeEvent(){
        this("Probe");
    }

    @Override
    public void handleEvent(@NotNull Model model) {
        model.sendProveRequest();
    }
}
