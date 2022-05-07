package ru.nsu.fit.gemuev.util;

import ru.nsu.fit.gemuev.util.json.JsonSenderListenerFactory;

public interface AbstractSenderListenerFactory {

    RequestSender requestSenderInstance();

    RequestListener requestListenerInstance();

    EventSender eventSenderInstance();

    EventListener eventListenerInstance();

    static AbstractSenderListenerFactory of(){
        return JsonSenderListenerFactory.getInstance();
    }
}
