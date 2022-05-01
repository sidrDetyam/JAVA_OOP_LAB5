package ru.nsu.fit.gemuev.client.events;

import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.gemuev.client.Model;

import java.io.Serializable;

public interface Event extends Serializable {

    void handleEvent(@NotNull Model model);

}
