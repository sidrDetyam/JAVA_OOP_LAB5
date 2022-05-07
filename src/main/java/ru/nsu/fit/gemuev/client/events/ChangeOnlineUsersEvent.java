package ru.nsu.fit.gemuev.client.events;

import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.gemuev.util.Event;
import ru.nsu.fit.gemuev.client.Model;

import java.util.ArrayList;

public record ChangeOnlineUsersEvent(@NotNull ArrayList<String> usersNames,
                                     String eventType) implements Event {

    public ChangeOnlineUsersEvent(@NotNull ArrayList<String> usersNames){
        this(usersNames, "ChangeOnlineUsers");
    }

    @Override
    public void handleEvent(@NotNull Model model) {
        model.updateUsersOnline(usersNames);
    }
}
