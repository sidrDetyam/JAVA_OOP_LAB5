package ru.nsu.fit.gemuev.util.serializable;

import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.gemuev.util.EventSender;
import ru.nsu.fit.gemuev.util.Event;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SerializableEventSender implements EventSender {

    private SerializableEventSender(){}

    private static class Holder{
        static final SerializableEventSender INSTANCE = new SerializableEventSender();
    }

    public static SerializableEventSender getInstance(){
        return SerializableEventSender.Holder.INSTANCE;
    }

    @Override
    public void sendEvent(@NotNull Event event, @NotNull Socket socket) throws IOException {

        var out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        out.writeObject(event);
        out.flush();
    }
}
