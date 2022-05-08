package ru.nsu.fit.gemuev.util.exceptions;

import java.util.ArrayList;
import java.util.function.Consumer;

public class Observable<T extends Observer>{

    private final ArrayList<T> observers;

    public void subscribe(T observer){
        observers.add(observer);
    }

    public void unsubscribe(T observer){
        observers.remove(observer);
    }

    public void unsubscribeAll(){observers.clear();}

    public void notifyObservers(Consumer<? super T> consumer){
        for(var i : observers){
            consumer.accept(i);
        }
    }

    public Observable(){
        observers = new ArrayList<>();
    }
}
