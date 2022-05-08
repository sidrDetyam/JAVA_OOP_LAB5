package ru.nsu.fit.gemuev.server;

import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.gemuev.util.Request;
import ru.nsu.fit.gemuev.util.RequestListener;

import java.io.IOException;
import java.net.Socket;


public record RequestHandler(@NotNull Server server, @NotNull Socket socket,
                             @NotNull RequestListener requestListener) implements Runnable{

    @Override
    public void run() {

        try {
            while (!Thread.interrupted()) {
                Request request = requestListener.nextRequest(socket);
                server.getLogger().info("Receive request: " + request);
                request.handleRequest(server, socket);
            }

        }catch (IOException e) {
            server.userLogout(socket);
        }
    }
}
