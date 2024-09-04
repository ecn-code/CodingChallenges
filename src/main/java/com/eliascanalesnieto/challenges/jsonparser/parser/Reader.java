package com.eliascanalesnieto.challenges.jsonparser.parser;

import java.io.BufferedReader;
import java.io.IOException;

public class Reader {
    private final BufferedReader reader;
    private int currentCharacter;
    private int lastCharacter;

    public Reader(final BufferedReader reader) {
        this.reader = reader;
        this.lastCharacter = -1;
    }

    public int nextCharacter() throws IOException {
        int character;
        while((character = reader.read()) != -1 && (character == ' ' || character == '\n'));
        lastCharacter = currentCharacter;
        currentCharacter = character;
        return character;
    }

    public int nextCharacterWithoutIgnore() throws IOException {
        lastCharacter = currentCharacter;
        currentCharacter = reader.read();
        return currentCharacter;
    }

    public int getCurrentCharacter() {
        return currentCharacter;
    }

    public int getLastCharacter() {
        return lastCharacter;
    }
}
