package com.eliascanalesnieto.challenges.webserver;

import com.eliascanalesnieto.challenges.webserver.exception.ServerException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

public record FileWriter(File file) {

    public void send(final Writer writer) throws ServerException {
        try {
            final Reader reader = new FileReader(file, StandardCharsets.UTF_8);
            int character;
            while ((character = reader.read()) != -1) {
                writer.write(character);
            }
        } catch (IOException e) {
            throw new ServerException();
        }
    }
}
