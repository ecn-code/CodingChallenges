package com.eliascanalesnieto.challenges.huffman.model;

public record Node(long count, Branch left, Branch right) implements Branch {

    public Node(final Branch left, final Branch right) {
        this(left.count() + right.count(), left, right);
    }
}
