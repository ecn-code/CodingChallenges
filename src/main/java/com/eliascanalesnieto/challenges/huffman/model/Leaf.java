package com.eliascanalesnieto.challenges.huffman.model;

public record Leaf(char character, long count) implements Branch {
}
