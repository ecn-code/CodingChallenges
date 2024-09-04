package com.eliascanalesnieto.challenges.jsonparser.parser;

import com.eliascanalesnieto.challenges.jsonparser.exception.JsonParserException;
import com.eliascanalesnieto.challenges.jsonparser.model.ParserResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class JsonObjectParser {

    public static Map<String, Object> read(final Reader reader) throws IOException, JsonParserException {
        if (reader.getCurrentCharacter() != '{') {
            throw new JsonParserException(STR."Bad START \{(char) reader.getCurrentCharacter()}. Expected {");
        }

        final Map<String, Object> objectParsed = read(reader, new HashMap<>());

        reader.nextCharacter();

        return objectParsed;
    }

    private static Map<String, Object> read(final Reader reader, final Map<String, Object> jsonValueMap) throws IOException, JsonParserException {
        reader.nextCharacter();
        if (reader.getLastCharacter() == ',' && reader.getCurrentCharacter() != '"') {
            throw new JsonParserException(STR."Bad NEXT \{(char) reader.getLastCharacter()}. Expected \"");
        }

        Map.Entry<String, Object> keyValue = switch (reader.getCurrentCharacter()) {
            case '"' -> getKeyValue(reader);
            case '}' -> null;
            default ->
                    throw new JsonParserException(STR."Bad OBJECT \{(char) reader.getCurrentCharacter()}. Expected \" or }");
        };

        Optional.ofNullable(keyValue)
                .ifPresent(entry -> {
                    jsonValueMap.put(entry.getKey(), entry.getValue());
                });

        if (reader.getCurrentCharacter() == ',') {
            read(reader, jsonValueMap);
        }

        return jsonValueMap;
    }

    private static Map.Entry<String, Object> getKeyValue(final Reader reader) throws JsonParserException, IOException {
        if (reader.getCurrentCharacter() != '"') {
            throw new JsonParserException(STR."Bad key START \{(char) reader.getCurrentCharacter()}. Expected '\"'.");
        }

        int character;
        final String key = JsonValueParser.read(reader);
        if (reader.getLastCharacter() != '"') {
            throw new JsonParserException(STR."Bad key END \{(char) reader.getLastCharacter()}. Expected '\"'.");
        }

        if (reader.getCurrentCharacter() != ':') {
            throw new JsonParserException(STR."Bad key/value connector \{(char) reader.getCurrentCharacter()}. Expected ':'.");
        }

        character = reader.nextCharacter();
        return Map.entry(key, switch (character) {
            case '{' -> JsonObjectParser.read(reader);
            case '[' -> JsonArrayParser.read(reader, 0);
            default -> JsonValueParser.read(reader);
        });
    }
}
