package ru.nsu.fit.gemuev.client.events;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Optional;

public class SerializableEventListener implements EventListener {

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
