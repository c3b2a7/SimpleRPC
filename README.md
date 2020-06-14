# Simple RPC server and client

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
// Output on the server's terminal
helloWorld.say("Hello Java!");
Calculator calculator = client.getRemoteProxy(Calculator.class);
// true
Assert.assertEquals(Math.multiplyExact(3, 3), (long) calculator.multiply(3, 3));
```

## License

MIT