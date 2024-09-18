package com.eliascanalesnieto.challenges.huffman;

import com.eliascanalesnieto.challenges.huffman.model.RowPrefixChar;
import com.eliascanalesnieto.challenges.huffman.model.RowPrefixCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayReader;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.eliascanalesnieto.challenges.huffman.CompressFile.convertToSigned;

public class CompressFileTest {

    @Test
    void givenTextWhenCompressThenIsSmall() throws IOException {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        CompressFile.compress(Map.of(
                        (int) ' ', new RowPrefixCode(4L, "00", ' '),
                        (int) 'a', new RowPrefixCode(37L, "110", 'a'),
                        (int) 's', new RowPrefixCode(42L, "11100", 's'),
                        (int) 'E', new RowPrefixCode(42L, "10110", 'E'),
                        (int) 'e', new RowPrefixCode(42L, "11101", 'e'),
                        (int) 'H', new RowPrefixCode(42L, "10111", 'H'),
                        (int) 'i', new RowPrefixCode(42L, "1010", 'i'),
                        (int) 'l', new RowPrefixCode(42L, "01", 'l'),
                        (int) 'm', new RowPrefixCode(42L, "1111", 'm'),
                        (int) 'o', new RowPrefixCode(42L, "100", 'o')),
                new StringReader("Hola me llamo Elias"),
                byteArrayOutputStream
        );

        final byte[] byteArray = byteArrayOutputStream.toByteArray();


        Assertions.assertEquals(List.of(
                "11011110",
                "10011100",
                "10111111",
                "11010001",
                "10111011",
                "11110000",
                "11011001",
                "11010110",
                "111100"
        ), IntStream.range(0, byteArray.length)
                .mapToObj(i -> Integer.toBinaryString(byteArray[i] & 0xFF))
                .toList());
    }

    @Test
    void givenTextWhenCompressThenCorrect() throws IOException {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        CompressFile.compress(Map.of(
                        (int) 'U', new RowPrefixCode(120L, "0", 'U'),
                        (int) 'E', new RowPrefixCode(37L, "10", 'E'),
                        (int) ' ', new RowPrefixCode(42L, "11", ' ')),
                new StringReader("EUEE UUUUU EU UUU"),
                byteArrayOutputStream
        );

        final byte[] byteArray = byteArrayOutputStream.toByteArray();


        Assertions.assertEquals(List.of(
                "11001010",
                "11100000",
                "11110011",
                "1000"
        ), IntStream.range(0, byteArray.length)
                        .peek(i -> System.err.println((int)byteArray[i]))
                .mapToObj(i -> Integer.toBinaryString(byteArray[i] & 0xFF))
                .toList());
    }

    @Test
    void givenBinaryWhenDecompressThenIsText() throws IOException {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        CompressFile.decompress(Map.of(
                        "0", new RowPrefixChar(120L, 'U', 'U'),
                        "10", new RowPrefixChar(37L, 'E', 'E'),
                        "11", new RowPrefixChar(42L, ' ', ' ')),
                new CharArrayReader(new char[]{
                        (char) Integer.parseInt("11001010", 2),
                        (char) Integer.parseInt("11100000", 2),
                        (char) Integer.parseInt("11110011", 2),
                        (char) Integer.parseInt("1000", 2)
                }),
                byteArrayOutputStream
        );

        final byte[] byteArray = byteArrayOutputStream.toByteArray();

        Assertions.assertEquals("EUEE UUUUU EU UUU", new String(byteArray));
    }

    private static String leftPad(String binaryByte) {
        return String.format("%8s", binaryByte).replace(' ', '0');
    }

}
