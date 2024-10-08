package com.eliascanalesnieto.challenges.huffman;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class HeaderFileService {

    private static final String END_HEADER = "<{END}>";

    public static void save(final Map<Character, Long> characterCount, final Writer outputStream) throws IOException {
        final String header = STR."\{stringify(characterCount)}\{END_HEADER} ";
        outputStream.write(header);
    }

    public static Map<Character, Long> read(final Reader reader) throws Exception {
        final StringBuilder header = new StringBuilder();
        int character;
        while((character = reader.read()) != -1 && header.indexOf(END_HEADER) == -1) {
            header.append((char) character);
        }

        if(header.indexOf(END_HEADER) == -1) {
            throw new Exception("Error reading file");
        }

        return Arrays.stream(header.toString().replace(END_HEADER, "").split(","))
                .map(entry -> entry.split("="))
                .map(entry -> Map.entry((char) Integer.parseInt(entry[0]), Long.parseLong(entry[1])))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static String stringify(final Map<Character, Long> characterCount) {
        return characterCount.entrySet().stream()
                .map(entry -> STR."\{(int) entry.getKey()}=\{entry.getValue()}")
                .collect(Collectors.joining(","));
    }

}
