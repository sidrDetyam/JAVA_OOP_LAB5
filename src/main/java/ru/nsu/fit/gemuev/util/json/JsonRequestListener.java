package ru.nsu.fit.gemuev.util.json;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ru.nsu.fit.gemuev.util.Request;
import ru.nsu.fit.gemuev.util.RequestListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Optional;

public class JsonRequestListener implements RequestListener {

    private JsonRequestListener(){}

    static private class Holder{
        static final JsonRequestListener INSTANCE = new JsonRequestListener();
    }

    public static JsonRequestListener getInstance(){
        return Holder.INSTANCE;
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Optional<Request> nextRequest(Socket socket) throws IOException {


        var bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String jsonStr = bufferedReader.readLine();
        if(jsonStr==null){
            throw new IOException("EOF");
        }


        String requestType = objectMapper.readValue(jsonStr, ObjectNode.class).get("requestType").asText();
        Optional<Class<?>> requestClass = ClassByNameGetter.getInstance().getRequestClass(requestType);

        if(requestClass.isPresent()){
            try {
                return Optional.of((Request) objectMapper.readValue(jsonStr, requestClass.get()));
            }
            catch(JacksonException e){
                e.printStackTrace();
                System.exit(1);
            }
        }

        return Optional.empty();

    }
}
