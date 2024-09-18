package com.eliascanalesnieto.challenges.huffman;

import com.eliascanalesnieto.challenges.huffman.model.RowPrefixChar;
import com.eliascanalesnieto.challenges.huffman.model.RowPrefixCode;

import java.io.BufferedReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class CompressionTool {

    public static void main(final String[] args) throws Exception {
        if(args == null || args.length == 0 || (args[0] != "-c" && args[0] != "-d")) {
            System.out.println("""
                    Options: 
                    -c <filename to compress> <filename output>
                    -d <filename to decompress> <filename output>
                    """);
            return;
        }

        if(args[0] == "-c") {
            compress(args);
        } else if(args[0] == "-d") {
            decompress(args);
        }
    }

    private static void decompress(final String[] args) throws Exception {
        if(args.length != 3) {
            System.out.println("""
                    -d <filename to decompress> <filename output>
                    """);
        }
        final BufferedReader reader = Files.newBufferedReader(Path.of(args[1]), StandardCharsets.ISO_8859_1);
        final Map<Integer, Long> characterCount = HeaderFileService.read(reader);
        final Map<String, RowPrefixChar> frequencyTable = CodeCharTableBuilder.build(
                TreeBuilder.build(characterCount)
        );
        CompressFile.decompress(frequencyTable, reader, Files.newOutputStream(Path.of(args[2])));
    }

    private static void compress(final String[] args) throws Exception {
        if(args.length != 3) {
            System.out.println("""
                    -c <filename to compress> <filename output>
                    """);
        }

        final Map<Integer, Long> characterCount = FileCharacterCounter.countCharacters(args[1]);
        final OutputStream outputStream = Files.newOutputStream(Path.of(args[2]));
        HeaderFileService.save(characterCount, outputStream);

        final Map<Integer, RowPrefixCode> frequencyTable = CharCodeTableBuilder.build(
                TreeBuilder.build(characterCount)
        );
        CompressFile.compress(
                frequencyTable,
                Files.newBufferedReader(Path.of(args[1]), StandardCharsets.UTF_8),
                outputStream
        );
    }
}