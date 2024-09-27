package com.eliascanalesnieto.challenges.webserver;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class WebServerTest {

    @ParameterizedTest
    @ValueSource(strings = {"/ch", "/../private.html", "%2f%2e%2e%2fprivate.html", "/private.txt"})
    void givenPathNotExistWhenServerProcessThenServerNotFoundResponse(final String path) throws Exception {
        testRequest(path, "HTTP/1.1 400 Not Found\r\n");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "/", "/index.html"})
    void givenExistingRouteThenServerIndexResponse(final String path) throws Exception {
        testRequest(path, "HTTP/1.1 200 OK\r\n" +
                "\r\n" +
                "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "    <head>\n" +
                "        <title>Simple Web Page</title>\n" +
                "    </head>\n" +
                "    <body>\n" +
                "        <h1>Test Web Page</h1>\n" +
                "        <p>My web server served this page!</p>\n" +
                "    </body>\n" +
                "</html>\r\n");
    }

    private static void testRequest(String path, String expected) throws Exception {
        final WebServer webServer = new WebServer(1);
        webServer.start();
        TimeUnit.SECONDS.sleep(1);

        final StringBuilder response = new StringBuilder();
        final Thread clientThread = getClient(response, path);
        clientThread.start();

        clientThread.join();

        Assertions.assertEquals(expected, response.toString());

        webServer.stop();
    }

    private static CompletableFuture<Void> request() {
        return CompletableFuture.supplyAsync(() -> {
            final Thread client = getClient(new StringBuilder(), "/index.html");
            client.start();
            try {
                client.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            return null;
        });
    }

    private static Thread getClient(final StringBuilder response, final String path) {
        final Thread clientThread = new Thread(() -> {
            try (final Socket socket = new Socket("127.0.0.1", 1000)) {
                final OutputStream outputStream = socket.getOutputStream();
                outputStream.write(STR."GET \{path} HTTP/1.1\r\nHost: localhost\r\n\r\n".getBytes());
                outputStream.flush();
                int character;
                while((character = socket.getInputStream().read()) != -1) {
                    response.append((char) character);
                }
            } catch (IOException e) {
                System.err.println(e);
            }
        });

        return clientThread;
    }

}
