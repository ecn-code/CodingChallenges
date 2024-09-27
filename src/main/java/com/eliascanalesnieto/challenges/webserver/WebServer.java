package com.eliascanalesnieto.challenges.webserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebServer {

    private static final int PORT = 1000;
    private final ExecutorService executorService;
    private boolean running;

    public WebServer(final int threads) {
        executorService = Executors.newFixedThreadPool(threads);
    }

    public void start() throws Exception {
        if (running) {
            throw new Exception("Server running");
        }

        running = true;
        new Thread(() ->
        {
            try (final ServerSocket serverSocket = new ServerSocket(PORT)) {
                System.out.println(STR."Listening at port \{PORT}");
                while (running) {
                    final Socket socket = serverSocket.accept();
                    executorService.execute(new ClientHandler(socket));
                }
            } catch (IOException e) {
                System.err.println(e);
            }
        }).start();
    }

    public void stop() {
        running = false;
    }

    public static void main(final String[] args) throws Exception {
        new WebServer(Integer.parseInt(args == null || args.length == 0 ? "1" : args[0])).start();
    }

}
