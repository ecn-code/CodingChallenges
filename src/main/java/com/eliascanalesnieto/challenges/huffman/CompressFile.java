package com.eliascanalesnieto.challenges.huffman;

import com.eliascanalesnieto.challenges.huffman.model.RowPrefixChar;
import com.eliascanalesnieto.challenges.huffman.model.RowPrefixCode;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class CompressFile {

    public static void compress(final Map<Integer, RowPrefixCode> frequencyTable,
                                  final Reader reader, final OutputStream outputStream) throws IOException {
        int character;
        final StringBuilder binaryByte = new StringBuilder();
        while ((character = reader.read()) != -1) {
            final RowPrefixCode rowPrefixCode = frequencyTable.get(character);
            binaryByte.append(rowPrefixCode.code());
            if(binaryByte.length() > 7) {
                outputStream.write(Integer.parseInt(STR."1\{binaryByte.substring(0, 7)}", 2));
                //outputStream.write((byte)convertToSigned(Integer.parseInt(binaryByte.substring(0, 8), 2)));
                //outputStream.write(hexStringToByteArray(new BigInteger(binaryByte.substring(0, 8), 2).toString(16)));
                binaryByte.replace(0, 7, "");
            }
        }

        outputStream.write(Integer.parseInt(STR."1\{binaryByte}", 2));
        //outputStream.write((byte)Integer.parseInt(leftPad(binaryByte), 2));
        //outputStream.write(hexStringToByteArray(new BigInteger(binaryByte.toString(), 2).toString(16)));
    }

    public static void decompress(final Map<String, RowPrefixChar> frequencyTable,
                                  final Reader reader, final OutputStream outputStream) throws IOException {
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
                    outputStream.write(String.valueOf(rowPrefixChar.characterC()).getBytes(StandardCharsets.UTF_8));
                    possibleCode.replace(0, i, "");
                    i = -1;
                }
            }
        }
    }

    private static String leftPad(String binaryByte) {
        return String.format("%8s", binaryByte).replace(' ', '0');
    }

    public static int convertToSigned(int num) {
        if (num < 0 || num > 255) {
            throw new IllegalArgumentException("El número debe estar en el rango de 0 a 255");
        }

        // Si num es mayor que 127, restamos 256 para convertirlo al rango -127 a 127
        return (num <= 127) ? num : num - 256;
    }

    public static byte hexStringToByteArray(final String s) {
        if(s.length() == 1) {
            return (byte) Character.digit(s.charAt(0), 16);
        }
        return (byte) ((Character.digit(s.charAt(0), 16) << 4)
                    + Character.digit(s.charAt(1), 16));
    }

}
