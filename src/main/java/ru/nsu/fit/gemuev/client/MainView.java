package ru.nsu.fit.gemuev.client;

import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.gemuev.server.Message;


import java.util.List;

public interface MainView{

    void addNewMessage(@NotNull Message message);

    void updateUsersOnline(@NotNull List<String> userNames);

    void openForm();

    default void closeForm(){}
}
