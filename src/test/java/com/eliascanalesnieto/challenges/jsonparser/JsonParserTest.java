package com.eliascanalesnieto.challenges.jsonparser;

import com.eliascanalesnieto.challenges.SystemOutTool;
import com.eliascanalesnieto.challenges.jsonparser.parser.JsonParserResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;

class JsonParserTest extends SystemOutTool {

    /**
     * https://codingchallenges.fyi/challenges/challenge-json-parser#step-1
     */
    @Test
    void givenEmptyJsonWhenProcessThenReturnZero() throws URISyntaxException {
        JsonParser.main(new String[]{getFilePath(getClass(), "json-parser/step1/valid.json")});
        Assertions.assertEquals(expected(JsonParserResult.OK), getOutput());
    }

    /**
     * https://codingchallenges.fyi/challenges/challenge-json-parser#step-1
     */
    @Test
    void givenFileEmptyWhenProcessThenReturnThree() throws URISyntaxException {
        JsonParser.main(new String[]{getFilePath(getClass(), "json-parser/step1/invalid.json")});
        Assertions.assertEquals(expected(JsonParserResult.LEXICAL), getOutput());
    }

    /**
     * https://codingchallenges.fyi/challenges/challenge-json-parser#step-2
     */
    @Test
    void givenJsonWithOneElementWhenProcessThenReturnZero() throws URISyntaxException {
        JsonParser.main(new String[]{getFilePath(getClass(), "json-parser/step2/valid.json")});
        Assertions.assertEquals(expected(JsonParserResult.OK), getOutput());
    }

    /**
     * https://codingchallenges.fyi/challenges/challenge-json-parser#step-2
     */
    @Test
    void givenJsonWithTwoElementsWhenProcessThenReturnZero() throws URISyntaxException {
        JsonParser.main(new String[]{getFilePath(getClass(), "json-parser/step2/valid2.json")});
        Assertions.assertEquals(expected(JsonParserResult.OK), getOutput());
    }

    /**
     * https://codingchallenges.fyi/challenges/challenge-json-parser#step-2
     */
    @Test
    void givenJsonWithOneExtraCommaWhenProcessThenReturnThree() throws URISyntaxException {
        JsonParser.main(new String[]{getFilePath(getClass(), "json-parser/step2/invalid.json")});
        Assertions.assertEquals(expected(JsonParserResult.LEXICAL), getOutput());
    }

    /**
     * https://codingchallenges.fyi/challenges/challenge-json-parser#step-2
     */
    @Test
    void givenJsonWithoutTwoRequiredDoubleQuotesWhenProcessThenReturnThree() throws URISyntaxException {
        JsonParser.main(new String[]{getFilePath(getClass(), "json-parser/step2/invalid2.json")});
        Assertions.assertEquals(expected(JsonParserResult.LEXICAL), getOutput());
    }

    /**
     * https://codingchallenges.fyi/challenges/challenge-json-parser#step-3
     */
    @Test
    void givenJsonWithInvalidBooleanWhenProcessThenReturnThree() throws URISyntaxException {
        JsonParser.main(new String[]{getFilePath(getClass(), "json-parser/step3/invalid.json")});
        Assertions.assertEquals(expected(JsonParserResult.LEXICAL), getOutput());
    }

    /**
     * https://codingchallenges.fyi/challenges/challenge-json-parser#step-3
     */
    @Test
    void givenJsonWithValidValuesWhenProcessThenReturnZero() throws URISyntaxException {
        JsonParser.main(new String[]{getFilePath(getClass(), "json-parser/step3/valid.json")});
        Assertions.assertEquals(expected(JsonParserResult.OK), getOutput());
    }

    /**
     * https://codingchallenges.fyi/challenges/challenge-json-parser#step-4
     */
    @Test
    void givenJsonWithValidEmptyComplexValuesWhenProcessThenReturnZero() throws URISyntaxException {
        JsonParser.main(new String[]{getFilePath(getClass(), "json-parser/step4/valid.json")});
        Assertions.assertEquals(expected(JsonParserResult.OK), getOutput());
    }

    /**
     * https://codingchallenges.fyi/challenges/challenge-json-parser#step-4
     */
    @Test
    void givenJsonWithValidFilledComplexValuesWhenProcessThenReturnZero() throws URISyntaxException {
        JsonParser.main(new String[]{getFilePath(getClass(), "json-parser/step4/valid2.json")});
        Assertions.assertEquals(expected(JsonParserResult.OK), getOutput());
    }

    /**
     * https://codingchallenges.fyi/challenges/challenge-json-parser#step-4
     */
    @Test
    void givenJsonWithInvalidComplexValuesWhenProcessThenReturnZero() throws URISyntaxException {
        JsonParser.main(new String[]{getFilePath(getClass(), "json-parser/step4/invalid.json")});
        Assertions.assertEquals(expected(JsonParserResult.LEXICAL), getOutput());
    }

    /**
     * https://codingchallenges.fyi/challenges/challenge-json-parser#step-5
     */
    @Test
    void givenJsonValidThenReturnZero() throws URISyntaxException {
        for (int i = 1; i < 4; i++) {
            beforeEach();
            JsonParser.main(new String[]{getFilePath(getClass(), STR."json-parser/step5/pass\{i}.json")});
            Assertions.assertEquals(expected(JsonParserResult.OK), getOutput(), STR."Testing pass\{i}");
        }
    }

    /**
     * https://codingchallenges.fyi/challenges/challenge-json-parser#step-5
     */
    @Test
    void givenJsonInvalidThenReturnThree() throws URISyntaxException {
        for (int i = 1; i < 34; i++) {
            beforeEach();
            JsonParser.main(new String[]{getFilePath(getClass(), STR."json-parser/step5/fail\{i}.json")});
            Assertions.assertEquals(expected(JsonParserResult.LEXICAL), getOutput(), STR."File fail\{i}");
        }
    }

    private String expected(final JsonParserResult jsonParserResult) {
        return STR."\{jsonParserResult.getCode()}\n";
    }

}