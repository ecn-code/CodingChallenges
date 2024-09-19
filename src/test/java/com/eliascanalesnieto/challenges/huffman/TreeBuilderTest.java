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
                'X', 1L,
                'C', 12L,
                'I', 5L,
                'j', 9L
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
                'C', 32L,
                'D', 42L,
                'E', 120L,
                'K', 7L,
                'L', 42L,
                'M', 24L,
                'U', 37L,
                'Z', 2L
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