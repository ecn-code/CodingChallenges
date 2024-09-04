package com.eliascanalesnieto.challenges.jsonparser.parser;

import com.eliascanalesnieto.challenges.jsonparser.exception.JsonParserException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Map;

class JsonArrayParserTest {

    @Test
    void givenEmptyArrayWhenReadThenReturnEmptyArray() throws JsonParserException, IOException {
        final Reader reader = new Reader(new BufferedReader(new StringReader("[]")));
        reader.nextCharacter();
        Assertions.assertEquals(List.of(), JsonArrayParser.read(reader, 0));
        Assertions.assertEquals(-1, reader.getCurrentCharacter());
    }

    @Test
    void givenArrayWithObjectWhenReadThenReturnArrayWithObject() throws JsonParserException, IOException {
        final Reader reader = new Reader(new BufferedReader(new StringReader("[{}]")));
        reader.nextCharacter();
        Assertions.assertEquals(List.of(Map.of()), JsonArrayParser.read(reader, 0));
        Assertions.assertEquals(-1, reader.getCurrentCharacter());
    }

    @Test
    void givenArrayWithObjectAndExtraCommaWhenReadThenThrowException() throws IOException {
        final Reader reader = new Reader(new BufferedReader(new StringReader("[{},]")));
        reader.nextCharacter();
        Assertions.assertThrows(JsonParserException.class, () -> JsonArrayParser.read(reader, 0));
    }

    @Test
    void givenArrayOfArraysWhenReadThenThrowException() throws IOException {
        final Reader reader = new Reader(new BufferedReader(new StringReader("[[[[[[[[[[[[[[[[[[[[\"Too deep\"]]]]]]]]]]]]]]]]]]]]")));
        reader.nextCharacter();
        Assertions.assertThrows(JsonParserException.class, () -> JsonArrayParser.read(reader, 0));
    }

    @Test
    void givenArrayColonInsteadOfCommaWhenReadThenException() throws IOException {
        final Reader reader = new Reader(new BufferedReader(new StringReader("[\"Colon instead of comma\": false]")));
        reader.nextCharacter();
        Assertions.assertThrows(JsonParserException.class, () -> JsonArrayParser.read(reader, 0));
    }

}