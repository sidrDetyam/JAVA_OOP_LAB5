package ru.nsu.fit.gemuev.util.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.gemuev.util.Event;
import ru.nsu.fit.gemuev.util.EventSender;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class JsonEventSender implements EventSender {

    private static class LazyHolder{
        static final JsonEventSender INSTANCE = new JsonEventSender();
    }

    public static JsonEventSender getInstance(){
        return LazyHolder.INSTANCE;
    }

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public void sendEvent(@NotNull Event event, @NotNull Socket socket) throws IOException {

        var printWriter = new PrintWriter(
                new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

        printWriter.println(objectMapper.writeValueAsString(event));
    }
}
