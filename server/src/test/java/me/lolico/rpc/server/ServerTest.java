package me.lolico.rpc.server;

import org.junit.Assert;
import org.junit.Test;

public class ServerTest {

    @Test
    public void start() {
        Server server = new RPCServer(39000).start();
    }

    @Test
    public void stop() {
        Server server = new RPCServer(39001).start();
        Assert.assertTrue(server.isRunning());
        server.stop();
        Assert.assertFalse(server.isRunning());
    }

    @Test
    public void getPort() {
        Server server = new RPCServer(39002).start();
        Assert.assertEquals(39002, server.getPort());
    }

    @Test
    public void isRunning() {
        Server server = new RPCServer(39003).start();
        Assert.assertTrue(server.isRunning());
    }
}