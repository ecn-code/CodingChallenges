package com.eliascanalesnieto.challenges.huffman;

import com.eliascanalesnieto.challenges.huffman.model.Leaf;
import com.eliascanalesnieto.challenges.huffman.model.Node;
import com.eliascanalesnieto.challenges.huffman.model.RowPrefixCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

class CharCodeTableBuilderTest {

    @Test
    void givenTreeWhenBuildTableThenTableIsOk() throws Exception {
        final Node tree = new Node(
                27L,
                new Leaf('C', 12L),
                new Node(
                        15L,
                        new Node(
                                6L,
                                new Leaf('X', 1L),
                                new Leaf('I', 5L)
                        ),
                        new Leaf('j', 9L)
                )
        );

        Assertions.assertEquals(Map.of(
                'C', new RowPrefixCode(12L, "0"),
                'j', new RowPrefixCode(9L, "11"),
                'X', new RowPrefixCode(1L, "100"),
                'I', new RowPrefixCode(5L, "101")
        ), CharCodeTableBuilder.build(tree));
    }

    @Test
    void givenTreeComplexWhenBuildTableThenTableIsOk() throws Exception {
        final Node tree = new Node(
                306L,
                new Leaf('E', 120L),
                new Node(
                        186L,
                        new Node(
                                79L,
                                new Leaf('U', 37L),
                                new Leaf('D', 42L)
                        ),
                        new Node(
                                107L,
                                new Leaf('L', 42L),
                                new Node(
                                        65L,
                                        new Leaf('C', 32L),
                                        new Node(
                                                33L,
                                                new Node(
                                                        9L,
                                                        new Leaf('Z', 2L),
                                                        new Leaf('K', 7L)
                                                ),
                                                new Leaf('M', 24L)
                                        )
                                )
                        )
                )
        );

        Assertions.assertEquals(Map.of(
                'E', new RowPrefixCode(120L, "0"),
                'U', new RowPrefixCode(37L, "100"),
                'D', new RowPrefixCode(42L, "101"),
                'L', new RowPrefixCode(42L, "110"),
                'C', new RowPrefixCode(32L, "1110"),
                'M', new RowPrefixCode(24L, "11111"),
                'Z', new RowPrefixCode(2L, "111100"),
                'K', new RowPrefixCode(7L, "111101")
        ), CharCodeTableBuilder.build(tree));
    }

}