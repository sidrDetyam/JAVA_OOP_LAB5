package ru.nsu.fit.gemuev.client.events;

import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.gemuev.client.Model;

import java.util.ArrayList;

public record ChangeOnlineUsersEvent(@NotNull ArrayList<String> usersNames) implements Event{

    @Override
    public void handleEvent(@NotNull Model model) {
        model.updateUsersOnline(usersNames);
    }
}
