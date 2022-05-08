package ru.nsu.fit.gemuev.util.exceptions;

import java.io.IOException;

public class UnknownClassException extends IOException {

    public UnknownClassException(String message){
        super(message);
    }

    public UnknownClassException(String message, Throwable cause){
        super(message, cause);
    }

}
