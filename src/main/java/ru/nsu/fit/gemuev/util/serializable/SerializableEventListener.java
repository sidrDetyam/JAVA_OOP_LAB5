package ru.nsu.fit.gemuev.util.serializable;

import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.gemuev.util.EventListener;
import ru.nsu.fit.gemuev.util.Event;
import ru.nsu.fit.gemuev.util.exceptions.UnknownClassException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;


public class SerializableEventListener implements EventListener {

    private SerializableEventListener(){}

    private static class Holder{
        static final SerializableEventListener INSTANCE = new SerializableEventListener();
    }

    public static SerializableEventListener getInstance() {
        return SerializableEventListener.Holder.INSTANCE;
    }

    @Override
    public Event nextEvent(@NotNull Socket socket) throws IOException {
        return nextEvent(socket, 0);
    }

    @Override
    public Event nextEvent(@NotNull Socket socket, int timeout) throws IOException {

        try {
            socket.setSoTimeout(timeout);
            var in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            return  (Event) in.readObject();
        }
        catch(ClassNotFoundException e){
            throw new UnknownClassException("Unknown class", e);
        }
    }
}
