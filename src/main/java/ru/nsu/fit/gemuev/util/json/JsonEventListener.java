package ru.nsu.fit.gemuev.util.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.gemuev.util.Event;
import ru.nsu.fit.gemuev.util.EventListener;
import ru.nsu.fit.gemuev.util.exceptions.UnknownClassException;

import java.io.*;
import java.net.Socket;
import java.util.Optional;

public class JsonEventListener implements EventListener {

    private JsonEventListener(){}

    static private class Holder{
        static final JsonEventListener INSTANCE = new JsonEventListener();
    }

    public static JsonEventListener getInstance(){
        return Holder.INSTANCE;
    }

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public Event nextEvent(@NotNull Socket socket) throws IOException {
        return nextEvent(socket, 0);
    }

    @Override
    public Event nextEvent(@NotNull Socket socket, int timeout) throws IOException {

        socket.setSoTimeout(timeout);
        var bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String jsonStr = bufferedReader.readLine();
        if(jsonStr==null){
            throw new IOException("Disconnect by timeout");
        }

        String eventType = objectMapper.readValue(jsonStr, ObjectNode.class).get("eventType").asText();

        if(eventType==null){
            throw new UnknownClassException("Event type is absent");
        }

        Optional<Class<?>> eventClass = ClassByNameGetter.getInstance().getEventClass(eventType);

        if(eventClass.isEmpty()){
            throw new UnknownClassException("Unknown event: " + eventType);
        }

        return (Event)objectMapper.readValue(jsonStr, eventClass.get());
    }
}
