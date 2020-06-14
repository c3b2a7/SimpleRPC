package me.lolico.rpc.server;

import me.lolico.rpc.api.Calculator;
import me.lolico.rpc.api.HelloWorld;
import me.lolico.rpc.vendor.CalculatorImpl;
import me.lolico.rpc.vendor.HelloWorldImpl;

/**
 * @author Lolico li
 */
public class App {
    public static void main(String[] args) {
        Server server = new RPCServer(Integer.parseInt(args[0])) // port
                .register(HelloWorld.class, new HelloWorldImpl())
                .register(Calculator.class, new CalculatorImpl())
                .start();
    }
}
