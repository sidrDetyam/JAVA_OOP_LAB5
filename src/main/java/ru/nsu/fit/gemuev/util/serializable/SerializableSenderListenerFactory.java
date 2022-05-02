package ru.nsu.fit.gemuev.util.serializable;

import ru.nsu.fit.gemuev.util.AbstractSenderListenerFactory;
import ru.nsu.fit.gemuev.util.EventListener;
import ru.nsu.fit.gemuev.util.EventSender;
import ru.nsu.fit.gemuev.util.RequestListener;
import ru.nsu.fit.gemuev.util.RequestSender;

public class SerializableSenderListenerFactory implements AbstractSenderListenerFactory {

    private SerializableSenderListenerFactory(){}

    private static class Holder{
        static final SerializableSenderListenerFactory INSTANCE = new SerializableSenderListenerFactory();
    }

    public static SerializableSenderListenerFactory getInstance(){
        return Holder.INSTANCE;
    }

    @Override
    public RequestSender requestSenderInstance() {
        return SerializableRequestSender.getInstance();
    }

    @Override
    public RequestListener requestListenerInstance() {
        return SerializableRequestListener.getInstance();
    }

    @Override
    public EventSender eventSenderInstance() {
        return SerializableEventSender.getInstance();
    }

    @Override
    public EventListener eventListenerInstance() {
        return SerializableEventListener.getInstance();
    }
}
