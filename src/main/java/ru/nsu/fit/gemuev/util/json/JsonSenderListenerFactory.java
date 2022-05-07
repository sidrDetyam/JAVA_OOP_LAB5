package ru.nsu.fit.gemuev.util.json;

import ru.nsu.fit.gemuev.util.*;

public class JsonSenderListenerFactory implements AbstractSenderListenerFactory {

    private JsonSenderListenerFactory(){}

    private static class Holder{
        static final JsonSenderListenerFactory INSTANCE = new JsonSenderListenerFactory();
    }

    public static JsonSenderListenerFactory getInstance(){
        return Holder.INSTANCE;
    }

    @Override
    public RequestSender requestSenderInstance() {
        return JsonRequestSender.getInstance();
    }

    @Override
    public RequestListener requestListenerInstance() {
        return JsonRequestListener.getInstance();
    }

    @Override
    public EventSender eventSenderInstance() {
        return JsonEventSender.getInstance();
    }

    @Override
    public EventListener eventListenerInstance() {
        return JsonEventListener.getInstance();
    }
}
