package com.eliascanalesnieto.challenges.jsonparser.parser;

import com.eliascanalesnieto.challenges.jsonparser.exception.JsonParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonArrayParser {

    public static List<Object> read(final Reader reader, int depth) throws IOException, JsonParserException {
        if(depth > 18) {
            throw new JsonParserException("Too deep");
        }

        final List<Object> list = new ArrayList<>();
        if(reader.getCurrentCharacter() != '[') {
            throw new JsonParserException(STR."Bad START \{reader.getCurrentCharacter()}. Expected [.");
        }

        int character;
        while (reader.getCurrentCharacter() != ']' && (character = reader.nextCharacter()) != -1 && character != ']') {
            final Object value = switch (character) {
                case '{' -> JsonObjectParser.read(reader);
                case '[' -> JsonArrayParser.read(reader, depth + 1);
                default -> JsonValueParser.read(reader);
            };
            list.add(value);

            if(reader.getCurrentCharacter() != ',' && reader.getCurrentCharacter() != ']') {
                throw new JsonParserException(STR."Bad array END \{(char) reader.getCurrentCharacter()}. Expected ].");
            }
        }

        if(reader.getLastCharacter() == ',' && reader.getCurrentCharacter() == ']') {
            throw new JsonParserException(STR."Bad array END \{(char) reader.getCurrentCharacter()}. Expected ].");
        }

        reader.nextCharacter();

        return list;
    }
}
