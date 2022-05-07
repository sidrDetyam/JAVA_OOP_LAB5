package ru.nsu.fit.gemuev.util.json;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.gemuev.util.Event;
import ru.nsu.fit.gemuev.util.EventListener;

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
    public Optional<Event> nextEvent(@NotNull Socket socket) throws IOException {

        var bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String jsonStr = bufferedReader.readLine();
        if(jsonStr==null){
            throw new IOException("EOF");
        }

        String eventType = objectMapper.readValue(jsonStr, ObjectNode.class).get("eventType").asText();
        Optional<Class<?>> eventClass = ClassByNameGetter.getInstance().getEventClass(eventType);

        if(eventClass.isPresent()){
            try {
                return Optional.of((Event)objectMapper.readValue(jsonStr, eventClass.get()));
            }
            catch(JacksonException e){
                e.printStackTrace();
                System.exit(1);
            }
        }

        return Optional.empty();
    }
}
