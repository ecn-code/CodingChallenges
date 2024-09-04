package com.eliascanalesnieto.challenges.jsonparser;

import com.eliascanalesnieto.challenges.jsonparser.exception.JsonParserException;
import com.eliascanalesnieto.challenges.jsonparser.parser.JsonArrayParser;
import com.eliascanalesnieto.challenges.jsonparser.parser.JsonObjectParser;
import com.eliascanalesnieto.challenges.jsonparser.parser.Reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static com.eliascanalesnieto.challenges.jsonparser.parser.JsonParserResult.EMPTY_FILENAME;
import static com.eliascanalesnieto.challenges.jsonparser.parser.JsonParserResult.IO;
import static com.eliascanalesnieto.challenges.jsonparser.parser.JsonParserResult.LEXICAL;
import static com.eliascanalesnieto.challenges.jsonparser.parser.JsonParserResult.OK;

public class JsonParser {

    public static void main(final String[] args) {
        if(args == null || args.length != 1) {
            System.out.println(EMPTY_FILENAME.getCode());
            return;
        }

        final Path path = Path.of(args[0]);
        try (var bufferedReader = Files.newBufferedReader(path)) {
            final Reader reader = new Reader(bufferedReader);
            
            final Object jsonParsed = switch (reader.nextCharacter()) {
                case '{' -> JsonObjectParser.read(reader);
                case '[' ->JsonArrayParser.read(reader, 0);
                default -> throw new JsonParserException("Invalid format");
            };

            if(reader.getCurrentCharacter() != -1) {
                throw new JsonParserException("Invalid format");
            }

            System.out.println(OK.getCode());
        } catch (final IOException e) {
            System.out.println(IO.getCode());
        } catch (final JsonParserException jsonParserException) {
            System.err.println(jsonParserException.getStackTrace()[0]);
            System.err.println(jsonParserException.getMessage());
            System.out.println(LEXICAL.getCode());
        }
    }

}