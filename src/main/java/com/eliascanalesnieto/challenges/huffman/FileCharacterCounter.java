package com.eliascanalesnieto.challenges.huffman;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class FileCharacterCounter {

    public static Map<Integer, Long> countCharacters(final String filename) {
        final Path path = Path.of(filename);
        final Map<Integer, Long> count = new HashMap<>();
        try (var reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            int character;
            while ((character = reader.read()) != -1) {
                if(!count.containsKey(character)) {
                    count.put(character, 0L);
                }
                count.compute(character, (k, l) -> l + 1);
            }
        } catch (final IOException ioException) {
            System.out.println("Error reading the file.");
        }

        return count;
    }

}
