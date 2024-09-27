package com.eliascanalesnieto.challenges.webserver.model;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public record HttpInfo(InputStream inputStream, String method, String path) {

    public HttpInfo(InputStream inputStream) throws IOException {
        final StringBuilder input = new StringBuilder();
        int character;
        while ((character = inputStream.read()) != -1 && input.indexOf("\r\n") == -1) {
            input.append((char) character);
        }

        final String[] firstLine = input.toString().split(" ");
        if(firstLine.length < 3 || !firstLine[2].startsWith("HTTP")) {
            throw new IOException("Bad request");
        }

        this(inputStream, firstLine[0], URLDecoder.decode(firstLine[1], StandardCharsets.UTF_8));
    }
}
