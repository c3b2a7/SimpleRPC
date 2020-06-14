package me.lolico.rpc.server;

/**
 * @author Lolico li
 */
public interface Server {

    Server start();

    void stop();

    int getPort();

    boolean isRunning();

    <T> Server register(Class<T> serviceInterface, Object impl);
}
