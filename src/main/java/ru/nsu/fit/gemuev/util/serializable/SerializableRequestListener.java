package ru.nsu.fit.gemuev.util.serializable;

import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.gemuev.util.Request;
import ru.nsu.fit.gemuev.util.RequestListener;
import ru.nsu.fit.gemuev.util.exceptions.UnknownClassException;

import java.io.*;
import java.net.Socket;


public class SerializableRequestListener implements RequestListener {

    private SerializableRequestListener(){}

    private static class Holder{
        static final SerializableRequestListener INSTANCE = new SerializableRequestListener();
    }

    public static SerializableRequestListener getInstance() {
        return SerializableRequestListener.Holder.INSTANCE;
    }

    @Override
    public Request nextRequest(@NotNull Socket socket) throws IOException{

        try {
            var in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            return  (Request) in.readObject();
        }
        catch(ClassNotFoundException e){
            throw new UnknownClassException("Unknown class", e);
        }
    }
}
