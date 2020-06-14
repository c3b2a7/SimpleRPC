package me.lolico.rpc.api;

/**
 * @author Lolico li
 */
public interface HelloWorld {
    void say(String msg);

    default void say() {
        say("Hello World");
    }
}
