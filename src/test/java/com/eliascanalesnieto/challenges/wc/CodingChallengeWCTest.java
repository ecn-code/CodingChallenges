package com.eliascanalesnieto.challenges.wc;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.nio.file.Paths;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CodingChallengeWCTest {

    private PrintStream originalOut;
    private ByteArrayOutputStream byteArrayOutputStream;
    private String filePath;

    @BeforeAll
    void beforeAll() throws URISyntaxException {
        byteArrayOutputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(byteArrayOutputStream));

        filePath = Paths.get(getClass().getClassLoader().getResource("test.txt").toURI())
                .toString();
    }

    @BeforeEach
    void beforeEach() {
        byteArrayOutputStream.reset();
    }

    @AfterAll
    void afterAll() {
        System.setOut(originalOut);
    }

    @Test
    void givenBadOptionsThenErrorHelpMessage() {
        Assertions.assertDoesNotThrow(() -> CodingChallengeWC.main(null));
        Assertions.assertDoesNotThrow(() -> CodingChallengeWC.main(new String[10]));
        Assertions.assertDoesNotThrow(() -> CodingChallengeWC.main(new String[]{}));
        Assertions.assertDoesNotThrow(() -> CodingChallengeWC.main(new String[]{"", " ", " -c"}));
        Assertions.assertDoesNotThrow(() -> CodingChallengeWC.main(new String[]{"   "}));
        Assertions.assertDoesNotThrow(() -> CodingChallengeWC.main(new String[]{"   -c"}));
        Assertions.assertDoesNotThrow(() -> CodingChallengeWC.main(new String[]{"   -c", "f"}));
        Assertions.assertDoesNotThrow(() -> CodingChallengeWC.main(new String[]{"file"}));
        Assertions.assertDoesNotThrow(() -> CodingChallengeWC.main(new String[]{"file", "-c"}));
    }

    /**
     * https://codingchallenges.fyi/challenges/challenge-wc/#step-one
     */
    @Test
    @DisplayName("Given -c and path file then it outputs file size and filename")
    void givenTestFileWhenCallCOptionThenOutputsFileSizeAndFileName() {
        CodingChallengeWC.main(new String[]{"-c", filePath});

        Assertions.assertEquals("342190 test.txt\n", byteArrayOutputStream.toString());
    }

    /**
     * https://codingchallenges.fyi/challenges/challenge-wc/#step-two
     */
    @Test
    @DisplayName("Given -l and path file then it outputs number of lines and filename")
    void givenTestFileWhenCallLOptionThenOutputsTheNumberOfLinesAndFileName() {
        CodingChallengeWC.main(new String[]{"-l", filePath});

        Assertions.assertEquals("7145 test.txt\n", byteArrayOutputStream.toString());
    }

    /**
     * https://codingchallenges.fyi/challenges/challenge-wc#step-three
     */
    @Test
    @DisplayName("Given -w and path file then it outputs number of words and filename")
    void givenTestFileWhenCallWOptionThenOutputsTheNumberOfWordsAndFileName() {
        CodingChallengeWC.main(new String[]{"-w", filePath});

        Assertions.assertEquals("58164 test.txt\n", byteArrayOutputStream.toString());
    }

    /**
     * https://codingchallenges.fyi/challenges/challenge-wc#step-four
     */
    @Test
    @DisplayName("Given -m and path file then it outputs number of characters and filename")
    void givenTestFileWhenCallMOptionThenOutputsTheNumberOfCharactersAndFileName() {
        CodingChallengeWC.main(new String[]{"-m", filePath});

        Assertions.assertEquals("339292 test.txt\n", byteArrayOutputStream.toString());
    }
}