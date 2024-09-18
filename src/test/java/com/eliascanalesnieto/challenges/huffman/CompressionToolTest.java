package com.eliascanalesnieto.challenges.huffman;

import com.eliascanalesnieto.challenges.SystemOutTool;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class CompressionToolTest extends SystemOutTool {

    private final String gutenberg = getFilePath(getClass(), "huffman/gutenberg.txt");
    private final String testEasyIn = getFilePath(getClass(), "huffman/in.txt");
    private final String testEasyOut = getFilePath(getClass(), "huffman/out.txt");
    private final String testEasyOutDecompressed = getFilePath(getClass(), "huffman/out-decompressed.txt");

    CompressionToolTest() throws URISyntaxException {}

    @Test
    void givenSmallFileWhenEncodeAndDecodeThenMessageIsSame() throws Exception {
        CompressionTool.main(new String[]{"-c", testEasyIn, testEasyOut});
        CompressionTool.main(new String[]{"-d", testEasyOut, testEasyOutDecompressed});
        Assertions.assertEquals("The Project Gutenberg eBook of Les Misérables, by Victor Hugo\n" +
                "\n" +
                "This eBook is for the use of anyone anywhere in the United States and\n" +
                "most other parts of the world at no cost and with almost no restrictions\n" +
                "whatsoever. You may copy it, give it away or re-use it under the terms\n" +
                "of the Project Gutenberg License included with this eBook or online at\n" +
                "www.gutenberg.org. If you are not located in the United States, you\n" +
                "will have to check the laws of the country where you are located before\n" +
                "using this eBook.\n" +
                "\n" +
                "Title: Les Misérables", Files.readString(Path.of(testEasyOutDecompressed)));
    }

    @Test
    void givenLargeTextWhenCompressAndDecompressThenIsSame() throws Exception {
        CompressionTool.main(new String[]{"-c", gutenberg, testEasyOut});
        CompressionTool.main(new String[]{"-d", testEasyOut, testEasyOutDecompressed});

        final BufferedReader bufferedReader = Files.newBufferedReader(Path.of(testEasyOutDecompressed));
        final BufferedReader guttenbergReader = Files.newBufferedReader(Path.of(gutenberg));
        int character;
        while ((character = guttenbergReader.read()) != -1) {
            Assertions.assertEquals(character, bufferedReader.read());
        }
    }

}