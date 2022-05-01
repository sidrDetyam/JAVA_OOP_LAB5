package ru.nsu.fit.gemuev.server;

import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.gemuev.server.requests.Request;
import ru.nsu.fit.gemuev.server.requests.RequestListener;

import java.io.IOException;
import java.net.Socket;


public record RequestHandler(@NotNull Server server, @NotNull Socket socket) implements Runnable{

    @Override
    public void run() {

        RequestListener requestListener = server.getRequestListener();

            try {
                while (!Thread.interrupted()) {

                    var opt = requestListener.nextRequest(socket);
                    if (opt.isEmpty()) {
                        throw new IOException("Something wrong with input object");
                    }

                    Request request = opt.get();
                    request.handleRequest(server, socket);
                }

            }catch (IOException e) {
                server.userLogout(socket);
            }
    }
}
