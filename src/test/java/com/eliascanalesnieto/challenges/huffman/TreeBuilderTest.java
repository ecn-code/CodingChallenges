package com.eliascanalesnieto.challenges.huffman;

import com.eliascanalesnieto.challenges.huffman.model.Leaf;
import com.eliascanalesnieto.challenges.huffman.model.Node;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

class TreeBuilderTest {

    @Test
    void givenCountTableWhenBuildTreeThenTreeSimple() {
        final Node root = TreeBuilder.build(Map.of(
                (int) 'X', 1L,
                (int) 'C', 12L,
                (int) 'I', 5L,
                (int) 'j', 9L
        ));

        Assertions.assertEquals(new Node(
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
        ), root);
    }

    @Test
    void givenTableCountWhenBuildTreeComplex() {
        final Node root = TreeBuilder.build(Map.of(
                (int) 'C', 32L,
                (int) 'D', 42L,
                (int) 'E', 120L,
                (int) 'K', 7L,
                (int) 'L', 42L,
                (int) 'M', 24L,
                (int) 'U', 37L,
                (int) 'Z', 2L
        ));

        Assertions.assertEquals(new Node(
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
        ), root);
    }

}