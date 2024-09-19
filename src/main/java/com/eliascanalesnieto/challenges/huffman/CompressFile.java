package com.eliascanalesnieto.challenges.huffman;

import com.eliascanalesnieto.challenges.huffman.model.RowPrefixChar;
import com.eliascanalesnieto.challenges.huffman.model.RowPrefixCode;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class CompressFile {

    public static void compress(final Map<Character, RowPrefixCode> frequencyTable,
                                  final Reader reader, final Writer outputStream) throws IOException {
        int character;
        final StringBuilder binaryByte = new StringBuilder();
        while ((character = reader.read()) != -1) {
            final RowPrefixCode rowPrefixCode = frequencyTable.get((char) character);
            binaryByte.append(rowPrefixCode.code());
            if(binaryByte.length() > 7) {
                outputStream.write(Integer.parseInt(STR."1\{binaryByte.substring(0, 7)}", 2));
                binaryByte.replace(0, 7, "");
            }
        }

        outputStream.write(Integer.parseInt(STR."1\{binaryByte}", 2));
    }

    public static void decompress(final Map<String, RowPrefixChar> frequencyTable,
                                  final Reader reader, final Writer writer) throws IOException {
        int character;
        final StringBuilder possibleCode = new StringBuilder();
        while ((character = reader.read()) != -1) {
            possibleCode.append(Integer.toBinaryString(character & 0xFF).substring(1));

            int i = -1;
            while(!possibleCode.isEmpty() && i < possibleCode.length()) {
                i++;
                final String code = possibleCode.substring(0, i);
                if(frequencyTable.containsKey(code)) {
                    final RowPrefixChar rowPrefixChar = frequencyTable.get(code);
                    writer.write(String.valueOf(rowPrefixChar.character()));
                    possibleCode.replace(0, i, "");
                    i = -1;
                }
            }
        }
    }
}
