package me.lolico.rpc.server;

import me.lolico.rpc.api.Calculator;
import me.lolico.rpc.api.HelloWorld;
import me.lolico.rpc.vendor.CalculatorImpl;
import me.lolico.rpc.vendor.HelloWorldImpl;
import org.junit.Test;

public class RPCServerTest {

    @Test
    public void register() {
        new RPCServer(39000)
                .register(HelloWorld.class, new HelloWorldImpl())
                .register(Calculator.class, new CalculatorImpl())
                .start();
    }
}