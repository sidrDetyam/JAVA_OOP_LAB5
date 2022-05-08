package ru.nsu.fit.gemuev.util;

import java.io.IOException;
import java.net.Socket;


public interface RequestListener {

    Request nextRequest(Socket socket) throws IOException;
}
