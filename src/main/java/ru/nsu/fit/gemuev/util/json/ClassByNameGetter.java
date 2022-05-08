package ru.nsu.fit.gemuev.util.json;

import ru.nsu.fit.gemuev.util.Event;
import ru.nsu.fit.gemuev.util.exceptions.LoadPropertiesException;
import ru.nsu.fit.gemuev.util.Request;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

public class ClassByNameGetter {

    private final Map<String, Class<?>> eventsClasses;
    private final Map<String, Class<?>> requestsClasses;

    private ClassByNameGetter() {
        eventsClasses = new HashMap<>();
        requestsClasses = new HashMap<>();

        try(InputStream inputStreamEvent = Event.class.getResourceAsStream("/events_list.properties");
            InputStream inputStreamRequest = Request.class.getResourceAsStream("/requests_list.properties")){

            var events = new Properties();
            events.load(inputStreamEvent);

            for(var i : events.entrySet()){
                eventsClasses.put((String) i.getKey(), Class.forName((String)i.getValue()));
            }

            var requests = new Properties();
            requests.load(inputStreamRequest);

            for(var i : requests.entrySet()){
                requestsClasses.put((String) i.getKey(), Class.forName((String)i.getValue()));
            }

        }catch(IOException | ReflectiveOperationException e){
            throw new LoadPropertiesException("Exception while reading config", e);
        }
    }

    public Optional<Class<?>> getEventClass(String className){
        var ret = eventsClasses.get(className);
        return ret==null? Optional.empty() : Optional.of(ret);
    }

    public Optional<Class<?>> getRequestClass(String className){
        var ret = requestsClasses.get(className);
        return ret==null? Optional.empty() : Optional.of(ret);
    }


    private static class LazyHolder{
        static final ClassByNameGetter INSTANCE = new ClassByNameGetter();
    }

    public static ClassByNameGetter getInstance(){
        return LazyHolder.INSTANCE;
    }

}
