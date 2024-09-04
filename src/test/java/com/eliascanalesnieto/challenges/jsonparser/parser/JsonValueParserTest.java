package com.eliascanalesnieto.challenges.jsonparser.parser;

import com.eliascanalesnieto.challenges.jsonparser.exception.JsonParserException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.io.IOException;

class JsonValueParserTest {

    @Test
    void givenStringWithQuoteWhenReadThenReturnStringWithQuote() throws JsonParserException, IOException {
        final Reader reader = new Reader(new BufferedReader(new CharArrayReader(new char[]{'"', '\\', '"', '"'})));
        reader.nextCharacter();
        Assertions.assertEquals(String.valueOf(new char[]{'\\', '"'}), JsonValueParser.read(reader));
    }

    @Test
    void givenStringWithBackslashNWhenReadThenReturnStringWithBackslashN() throws JsonParserException, IOException {
        final Reader reader = new Reader(new BufferedReader(new CharArrayReader(new char[]{'"', '\\', 'n', '"'})));
        reader.nextCharacter();
        Assertions.assertEquals(String.valueOf(new char[]{'\\', 'n'}), JsonValueParser.read(reader));
    }

    @Test
    void givenUTFStringWhenReadThenReturnUTFString() throws JsonParserException, IOException {
        final Reader reader = new Reader(new BufferedReader(new CharArrayReader("\"\u0123\u4567\u89AB\uCDEF\uabcd\uef4A\",".toCharArray())));
        reader.nextCharacter();
        Assertions.assertEquals("\u0123\u4567\u89AB\uCDEF\uabcd\uef4A", JsonValueParser.read(reader));
    }

}