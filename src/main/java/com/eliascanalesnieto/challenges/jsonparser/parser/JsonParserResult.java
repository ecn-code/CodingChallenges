package com.eliascanalesnieto.challenges.jsonparser.parser;

public enum JsonParserResult {
    OK("0"),
    EMPTY_FILENAME("1"),
    IO("2"),
    LEXICAL("3");

    private final String code;

    JsonParserResult(final String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
