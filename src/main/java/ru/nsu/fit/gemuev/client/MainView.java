package ru.nsu.fit.gemuev.client;

import org.jetbrains.annotations.NotNull;


import java.util.List;

public interface MainView{

    void addNewMessage(@NotNull String name, @NotNull String message);

    void updateUsersOnline(@NotNull List<String> userNames);

    void openForm();

    default void closeForm(){}
}
