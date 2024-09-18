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
                (int) 'C', new RowPrefixCode(12L, "0", 'C'),
                (int) 'j', new RowPrefixCode(9L, "11", 'j'),
                (int) 'X', new RowPrefixCode(1L, "100", 'X'),
                (int) 'I', new RowPrefixCode(5L, "101", 'I')
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
                (int) 'E', new RowPrefixCode(120L, "0", 'E'),
                (int) 'U', new RowPrefixCode(37L, "100", 'U'),
                (int) 'D', new RowPrefixCode(42L, "101", 'D'),
                (int) 'L', new RowPrefixCode(42L, "110", 'L'),
                (int) 'C', new RowPrefixCode(32L, "1110", 'C'),
                (int) 'M', new RowPrefixCode(24L, "11111", 'M'),
                (int) 'Z', new RowPrefixCode(2L, "111100", 'Z'),
                (int) 'K', new RowPrefixCode(7L, "111101", 'K')
        ), CharCodeTableBuilder.build(tree));
    }

}