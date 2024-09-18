package com.eliascanalesnieto.challenges.huffman;

import com.eliascanalesnieto.challenges.SystemOutTool;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.util.Map;

class FileCharacterCounterTest extends SystemOutTool {

    private final String filePath = getFilePath(getClass(), "huffman/gutenberg.txt");

    FileCharacterCounterTest() throws URISyntaxException {
        super();
    }

    @Test
    void givenFileWhenCountThenResultIsOk() {
        Map<Integer, Long> count = FileCharacterCounter.countCharacters(filePath);
        Assertions.assertEquals(333, count.get((int) 'X'));
        Assertions.assertEquals(223000, count.get((int) 't'));
    }

}