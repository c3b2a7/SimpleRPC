package me.lolico.rpc.client;

import me.lolico.rpc.api.Calculator;
import me.lolico.rpc.api.HelloWorld;
import org.junit.Test;

public class RPCClientTest {

    @Test
    public void getRemoteProxy() {
        RPCClient client = new RPCClient(39000);

        HelloWorld helloWorld = client.getRemoteProxy(HelloWorld.class);
        // Output in the server's terminal
        // helloWorld.say("Hello Java!");

        Calculator calculator = client.getRemoteProxy(Calculator.class);
        //true
        // Assert.assertEquals(Math.multiplyExact(3, 3), (long) calculator.multiply(3, 3));
    }
}