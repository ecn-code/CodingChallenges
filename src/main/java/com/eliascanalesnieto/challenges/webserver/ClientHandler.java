package com.eliascanalesnieto.challenges.webserver;

import com.eliascanalesnieto.challenges.webserver.exception.NotFoundException;
import com.eliascanalesnieto.challenges.webserver.exception.ServerException;
import com.eliascanalesnieto.challenges.webserver.model.HttpInfo;
import com.eliascanalesnieto.challenges.webserver.model.HttpStatus;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Path;

public class ClientHandler implements Runnable {

    private static final String HTTP_1_1 = "HTTP/1.1";
    private static final String HEADER_OK = STR."\{HTTP_1_1} \{HttpStatus.OK.getCode()} \{HttpStatus.OK.getMessage()}";
    private static final String HEADER_NOT_FOUND = STR."\{HTTP_1_1} \{HttpStatus.NOT_FOUND.getCode()} \{HttpStatus.NOT_FOUND.getMessage()}";
    private static final String HEADER_SERVER_ERROR = STR."\{HTTP_1_1} \{HttpStatus.SERVER_ERROR.getCode()} \{HttpStatus.SERVER_ERROR.getMessage()}";
    private static final String CRLF = "\r\n";
    private static final String START_BODY_SEPARATOR = CRLF + CRLF;
    private final Socket client;

    public ClientHandler(Socket client) {
        this.client = client;
    }

    public void run() {
        System.out.println(STR."Client connected: \{client.getInetAddress()}");

        final PrintWriter writer;
        final HttpInfo httpInfo;
        try {
            final InputStream inputStream = client.getInputStream();
            writer = new PrintWriter(client.getOutputStream(), true);
            httpInfo = new HttpInfo(inputStream);
            System.out.println(STR."Request: \{httpInfo}");
        } catch(IOException e) {
            System.err.printf("Error handling input/output %s\n", e);
            return;
        }

        System.out.println("Building a response...");

        try {
            final Path filePath = new Router("www").process(httpInfo.path());
            writer.write(STR."\{HEADER_OK}\{START_BODY_SEPARATOR}");
            new FileWriter(filePath.toFile()).send(writer);
        } catch (NotFoundException e) {
            writer.write(STR."\{HEADER_NOT_FOUND}");
        } catch (ServerException | IOException e) {
            writer.write(STR."\{HEADER_SERVER_ERROR}");
        }

        writer.write(CRLF);
        writer.close();
        try {
            client.close();
        } catch (IOException e) {
            System.err.printf("Error closing connection %s\n", e);
        }
        System.out.println("Response sent!");
    }

}
