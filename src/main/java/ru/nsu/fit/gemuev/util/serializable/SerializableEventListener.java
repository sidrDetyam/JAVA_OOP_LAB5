package ru.nsu.fit.gemuev.util.serializable;

import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.gemuev.util.EventListener;
import ru.nsu.fit.gemuev.util.Event;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Optional;

public class SerializableEventListener implements EventListener {

    private SerializableEventListener(){}

    private static class Holder{
        static final SerializableEventListener INSTANCE = new SerializableEventListener();
    }

    public static SerializableEventListener getInstance() {
        return SerializableEventListener.Holder.INSTANCE;
    }

    @Override
    public Optional<Event> nextEvent(@NotNull Socket socket) throws IOException {

        try {
            var in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            return  Optional.of((Event) in.readObject());
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }

        return Optional.empty();
    }
}