package ru.nsu.fit.gemuev.client;

public interface LoginView {

    void openForm();

    void showLogoutCause(String cause);

    default void closeForm(){}
}
