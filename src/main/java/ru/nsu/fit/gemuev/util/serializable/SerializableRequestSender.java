package ru.nsu.fit.gemuev.util.serializable;

import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.gemuev.util.Request;
import ru.nsu.fit.gemuev.util.RequestSender;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class SerializableRequestSender implements RequestSender {

    private SerializableRequestSender(){}

    private static class Holder{
        static final SerializableRequestSender INSTANCE = new SerializableRequestSender();
    }

    public static SerializableRequestSender getInstance(){
        return Holder.INSTANCE;
    }

    @Override
    public void sendRequest(@NotNull Socket socket, @NotNull Request request) throws IOException {

        var out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        out.writeObject(request);
        out.flush();
    }

}
