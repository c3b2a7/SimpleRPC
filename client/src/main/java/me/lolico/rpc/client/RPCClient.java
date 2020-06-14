package me.lolico.rpc.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Proxy;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author Lolico li
 */
public class RPCClient {

    private final InetSocketAddress address;

    public RPCClient(InetSocketAddress address) {
        this.address = address;
    }

    public RPCClient(int localPort) {
        this(new InetSocketAddress(localPort));
    }

    public RPCClient(String hostname, int port) {
        this(new InetSocketAddress(hostname, port));
    }

    public RPCClient(InetAddress inetAddress, int port) {
        this(new InetSocketAddress(inetAddress, port));
    }

    @SuppressWarnings("unchecked")
    public <T> T getRemoteProxy(final Class<T> serviceInterface) {
        return (T) Proxy.newProxyInstance(serviceInterface.getClassLoader(), new Class[]{serviceInterface},
                (proxy, method, args) -> {
                    Socket socket = new Socket();
                    socket.connect(address);
                    try (ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                         ObjectInputStream input = new ObjectInputStream(socket.getInputStream())) {
                        output.writeUTF(serviceInterface.getName());
                        output.writeUTF(method.getName());
                        output.writeObject(method.getParameterTypes());
                        output.writeObject(args);
                        Object o = input.readObject();
                        if (o instanceof Throwable) {
                            throw (Throwable) o;
                        }
                        return o;
                    }

                });
    }

}













