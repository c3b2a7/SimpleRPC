package me.lolico.rpc.vendor;

import me.lolico.rpc.api.HelloWorld;

/**
 * @author Lolico li
 */
public class HelloWorldImpl implements HelloWorld {
    @Override
    public void say(String msg) {
        System.out.println(msg);
    }
}
