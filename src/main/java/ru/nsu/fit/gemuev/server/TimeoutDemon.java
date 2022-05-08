package ru.nsu.fit.gemuev.server;


//maybe ScheduledExecutorService
public record TimeoutDemon(Server server) implements Runnable{

    @Override
    public void run() {

        while (!Thread.interrupted()) {
            server.deleteNonActivityUsers();
            try {
                Thread.sleep(server.getTimeout()/2);
            } catch (InterruptedException ignore){}
        }

    }
}
