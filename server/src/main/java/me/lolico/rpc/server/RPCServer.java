package me.lolico.rpc.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Lolico li
 */
public class RPCServer implements Server {
    private final Logger logger = Logger.getLogger(RPCServer.class.getName());

    private final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private final Map<Class<?>, Object> serviceRegistry = new HashMap<>();
    private boolean running;
    private final ServerSocket server;

    public RPCServer(int port) {
        try {
            this.server = new ServerSocket(port);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    public RPCServer(ServerSocket serverSocket) {
        this.server = serverSocket;
    }

    @Override
    public Server start() {
        String msg = "Server start at " + server.getInetAddress().getHostAddress() + ":" + getPort();
        logger.log(Level.INFO, msg);
        executor.execute(this::doStart);
        running = true;
        return this;
    }

    private void doStart() {
        try {
            //noinspection InfiniteLoopStatement
            while (true) {
                executor.submit(new ServiceTask(server.accept()));
            }
        } catch (Exception ex) {
            if (running) {
                logger.log(Level.SEVERE, ex.getMessage(), ex);
            }
        } finally {
            stop();
        }
    }

    @Override
    public void stop() {
        if (!running)
            return;
        executor.shutdown();
        try {
            server.close();
        } catch (IOException ex) {
            logger.log(Level.WARNING, ex.getMessage(), ex);
        }
        running = false;
    }

    @Override
    public int getPort() {
        return server.getLocalPort();
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    public <T> RPCServer register(Class<T> serviceInterface, Object impl) {
        if (serviceInterface.isAssignableFrom(impl.getClass())) {
            serviceRegistry.put(serviceInterface, impl);
        }
        return this;
    }

    private class ServiceTask implements Runnable {
        private final Socket socket;

        public ServiceTask(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {
                try (ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())) {
                    // 类名、（方法名、参数类型）、参数
                    String className = inputStream.readUTF();
                    String methodName = inputStream.readUTF();
                    Class<?>[] paramTypes = (Class<?>[]) inputStream.readObject();
                    Object[] args = (Object[]) inputStream.readObject();

                    try {
                        Object impl = serviceRegistry.get(Class.forName(className));
                        if (impl == null) {
                            throw new IllegalStateException(className + " do not register to service");
                        }
                        Method method = impl.getClass().getMethod(methodName, paramTypes);
                        Object o = method.invoke(impl, args);
                        outputStream.writeObject(o);
                    } catch (Throwable t) {
                        if (t instanceof InvocationTargetException) {
                            t = ((InvocationTargetException) t).getTargetException();
                        }
                        outputStream.writeObject(t);
                    }

                }
            } catch (Exception ex) {
                logger.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
    }
}
