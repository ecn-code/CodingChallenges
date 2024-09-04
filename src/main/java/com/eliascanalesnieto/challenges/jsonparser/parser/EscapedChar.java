package com.eliascanalesnieto.challenges.jsonparser.parser;

import java.util.Arrays;

public enum EscapedChar {
    N('n'),
    B('b'),
    T('t'),
    U('u'),
    BACKSLASH('\\'),
    SLASH('/'),
    QUOTE('"'),
    ;

    private final char character;

    EscapedChar(final char character) {
        this.character = character;
    }

    public static boolean isEscapable(char character) {
        return Arrays.stream(EscapedChar.values()).anyMatch(escapedChar -> escapedChar.character == character);
    }
}
