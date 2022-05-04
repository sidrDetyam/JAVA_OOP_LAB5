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

                var opt = requestListener.nextRequest(socket);
                if (opt.isEmpty()) {
                    throw new IOException("Something wrong with input object");
                }

                Request request = opt.get();
                server.getLogger().info("Receive request: " + request);
                request.handleRequest(server, socket);
            }

        }catch (IOException e) {
            server.userLogout(socket);
        }
    }
}
