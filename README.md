# Simple RPC server and client

[![Build Status](https://travis-ci.com/c3b2a7/SimpleRPC.svg?branch=master)](https://travis-ci.com/c3b2a7/SimpleRPC)
[![codecov](https://codecov.io/gh/LOLICOL1/rpc-demo/branch/master/graph/badge.svg)](https://codecov.io/gh/LOLICOL1/rpc-demo)
[![GitHub](https://img.shields.io/github/license/lolicol1/rpc-demo)](https://github.com/LOLICOL1/rpc-demo/blob/master/LICENSE)

> Demo with too many bugs 🙃

## Usage

This is an example, you can register your own interface and implementation.

### Server

The 'Main' method of the App class is a good example:

```java
public class App {
    public static void main(String[] args) {
        Server server = new RPCServer(Integer.parseInt(args[0])) // port
                .register(HelloWorld.class, new HelloWorldImpl())
                .register(Calculator.class, new CalculatorImpl())
                .start();
    }
}
```

You can run it in command line using java & javac, you can also use maven to package it into a jar package and then start or deploy it.


### Client

Using "RPCClient" to get the remote proxy.

```java
RPCClient client = new RPCClient("hostname", port); // hostname and port of RPC server.
HelloWorld helloWorld = client.getRemoteProxy(HelloWorld.class);
// Output in the server's terminal
helloWorld.say("Hello Java!");
Calculator calculator = client.getRemoteProxy(Calculator.class);
// true
Assert.assertEquals(Math.multiplyExact(3, 3), (long) calculator.multiply(3, 3));
```

## License

MIT
