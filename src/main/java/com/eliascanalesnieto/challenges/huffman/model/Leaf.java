package com.eliascanalesnieto.challenges.huffman.model;

public record Leaf(int character, long count) implements Branch {
}
