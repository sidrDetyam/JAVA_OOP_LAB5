package ru.nsu.fit.gemuev.util.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.gemuev.util.Request;
import ru.nsu.fit.gemuev.util.RequestSender;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;


public class JsonRequestSender implements RequestSender {

    private static class LazyHolder{
        static final JsonRequestSender INSTANCE = new JsonRequestSender();
    }

    public static JsonRequestSender getInstance(){
        return JsonRequestSender.LazyHolder.INSTANCE;
    }

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public void sendRequest(@NotNull Socket socket, @NotNull Request request) throws IOException {

        var printWriter = new PrintWriter(
                new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

        System.out.println(objectMapper.writeValueAsString(request));
        printWriter.println(objectMapper.writeValueAsString(request));
    }
}
