package com.eliascanalesnieto.challenges.jsonparser.parser;

import com.eliascanalesnieto.challenges.jsonparser.exception.JsonParserException;

import java.io.IOException;

public class JsonValueParser {

    public static String read(final Reader reader) throws IOException, JsonParserException {
        final StringBuilder value = new StringBuilder();
        int character = reader.getCurrentCharacter();
        boolean isString = character == '"';
        boolean isScaped = false;
        while (character != -1) {
            value.append((char) character);
            character = isString ? reader.nextCharacterWithoutIgnore() : reader.nextCharacter();
            if(!isScaped && isString && character == '"') {
                reader.nextCharacter();
                break;
            }
            if(!isScaped && character == '\\') {
                isScaped = true;
            } else if(isScaped && reader.getLastCharacter() == '\\' && (EscapedChar.isEscapable((char) character))) {
                isScaped = false;
            }
            if(!isString && (character == ',' || character == '}' || character == ']')) {
                break;
            }
        }

        if(!value.toString().contains("\\n") && value.toString().contains("\n")) {
            throw new JsonParserException(STR."Breakline not valid \{(char) character}");
        }

        if(!value.toString().contains("\\t") && value.toString().contains("\t")) {
            throw new JsonParserException(STR."Tab not valid \{(char) character}");
        }

        final String parsedValue = value.toString();
        if(isString && !isScaped) {
            return parsedValue.substring(1);
        }

        if(parsedValue.equals("null")) {
            return parsedValue;
        }

        if(parsedValue.matches("-?\\d+(\\.\\d+)?(e-\\d+)?(e\\+\\d+)?(E\\+\\d+)?(e\\d+)?(E\\d+)?") && !parsedValue.matches("0+\\d+")) {
            return parsedValue;
        }

        if(parsedValue.equals("true") || parsedValue.equals("false")) {
            return parsedValue;
        }

        throw new JsonParserException(STR."Value not valid \{parsedValue}");
    }
}
