package com.eliascanalesnieto.challenges.huffman;

import com.eliascanalesnieto.challenges.huffman.model.RowPrefixChar;
import com.eliascanalesnieto.challenges.huffman.model.RowPrefixCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.CharArrayReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class CompressFileTest {

    @Test
    void givenTextWhenCompressThenIsSmall() throws IOException {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final Writer writer = new OutputStreamWriter(byteArrayOutputStream, StandardCharsets.ISO_8859_1);
        final String text = "Hola me llamo Elias";
        CompressFile.compress(Map.of(
                        ' ', new RowPrefixCode(4L, "00"),
                        'a', new RowPrefixCode(37L, "110"),
                        's', new RowPrefixCode(42L, "11100"),
                        'E', new RowPrefixCode(42L, "10110"),
                        'e', new RowPrefixCode(42L, "11101"),
                        'H', new RowPrefixCode(42L, "10111"),
                        'i', new RowPrefixCode(42L, "1010"),
                        'l', new RowPrefixCode(42L, "01"),
                        'm', new RowPrefixCode(42L, "1111"),
                        'o', new RowPrefixCode(42L, "100")),
                new StringReader(text),
                writer
        );

        writer.close();
        final byte[] byteArray = byteArrayOutputStream.toByteArray();

        Assertions.assertTrue(text.length() > byteArray.length);
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
        final Writer writer = new OutputStreamWriter(byteArrayOutputStream, StandardCharsets.ISO_8859_1);
        CompressFile.compress(Map.of(
                        'U', new RowPrefixCode(120L, "0"),
                        'E', new RowPrefixCode(37L, "10"),
                        ' ', new RowPrefixCode(42L, "11")),
                new StringReader("EUEE UUUUU EU UUU"),
                writer
        );

        writer.close();
        final byte[] byteArray = byteArrayOutputStream.toByteArray();


        Assertions.assertEquals(List.of(
                "11001010",
                "11100000",
                "11110011",
                "1000"
        ), IntStream.range(0, byteArray.length)
                .mapToObj(i -> Integer.toBinaryString(byteArray[i] & 0xFF))
                .toList());
    }

    @Test
    void givenBinaryWhenDecompressThenIsText() throws IOException {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final Writer writer = new OutputStreamWriter(byteArrayOutputStream);
        CompressFile.decompress(Map.of(
                        "0", new RowPrefixChar(120L, 'U'),
                        "10", new RowPrefixChar(37L, 'E'),
                        "11", new RowPrefixChar(42L, ' ')),
                new CharArrayReader(new char[]{
                        (char) Integer.parseInt("11001010", 2),
                        (char) Integer.parseInt("11100000", 2),
                        (char) Integer.parseInt("11110011", 2),
                        (char) Integer.parseInt("1000", 2)
                }),
                writer
        );
        writer.close();
        Assertions.assertEquals("EUEE UUUUU EU UUU", byteArrayOutputStream.toString());
    }

    private static String leftPad(String binaryByte) {
        return String.format("%8s", binaryByte).replace(' ', '0');
    }

}
