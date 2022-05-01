package ru.nsu.fit.gemuev.server.requests;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.Socket;
import java.util.Optional;

public class SerializableRequestListener implements RequestListener {


    @Override
    public Optional<Request> nextRequest(@NotNull Socket socket) throws IOException{

        try {
            var in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            return  Optional.of((Request) in.readObject());
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
