package ru.nsu.fit.gemuev.util;

public interface AbstractSenderListenerFactory {

    RequestSender requestSenderInstance();

    RequestListener requestListenerInstance();

    EventSender eventSenderInstance();

    EventListener eventListenerInstance();
}
