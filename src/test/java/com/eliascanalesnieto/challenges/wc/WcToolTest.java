package com.eliascanalesnieto.challenges.wc;

import com.eliascanalesnieto.challenges.SystemOutTool;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.nio.file.Paths;

class WcToolTest extends SystemOutTool {

    private final String filePath = getFilePath(getClass(), "wc-tool/test.txt");

    WcToolTest() throws URISyntaxException {
        super();
    }

    @Test
    void givenBadOptionsThenErrorHelpMessage() {
        Assertions.assertDoesNotThrow(() -> WcTool.main(null));
        Assertions.assertDoesNotThrow(() -> WcTool.main(new String[10]));
        Assertions.assertDoesNotThrow(() -> WcTool.main(new String[]{}));
        Assertions.assertDoesNotThrow(() -> WcTool.main(new String[]{"", " ", " -c"}));
        Assertions.assertDoesNotThrow(() -> WcTool.main(new String[]{"   "}));
        Assertions.assertDoesNotThrow(() -> WcTool.main(new String[]{"   -c"}));
        Assertions.assertDoesNotThrow(() -> WcTool.main(new String[]{"   -c", "f"}));
        Assertions.assertDoesNotThrow(() -> WcTool.main(new String[]{"file"}));
        Assertions.assertDoesNotThrow(() -> WcTool.main(new String[]{"file", "-c"}));
    }

    /**
     * https://codingchallenges.fyi/challenges/challenge-wc/#step-one
     */
    @Test
    @DisplayName("Given -c and path file then it outputs file size and filename")
    void givenTestFileWhenCallCOptionThenOutputsFileSizeAndFileName() {
        WcTool.main(new String[]{"-c", filePath});

        Assertions.assertEquals("342190 test.txt\n", getOutput());
    }

    /**
     * https://codingchallenges.fyi/challenges/challenge-wc/#step-two
     */
    @Test
    @DisplayName("Given -l and path file then it outputs number of lines and filename")
    void givenTestFileWhenCallLOptionThenOutputsTheNumberOfLinesAndFileName() {
        WcTool.main(new String[]{"-l", filePath});

        Assertions.assertEquals("7145 test.txt\n", getOutput());
    }

    /**
     * https://codingchallenges.fyi/challenges/challenge-wc#step-three
     */
    @Test
    @DisplayName("Given -w and path file then it outputs number of words and filename")
    void givenTestFileWhenCallWOptionThenOutputsTheNumberOfWordsAndFileName() {
        WcTool.main(new String[]{"-w", filePath});

        Assertions.assertEquals("58164 test.txt\n", getOutput());
    }

    /**
     * https://codingchallenges.fyi/challenges/challenge-wc#step-four
     */
    @Test
    @DisplayName("Given -m and path file then it outputs number of characters and filename")
    void givenTestFileWhenCallMOptionThenOutputsTheNumberOfCharactersAndFileName() {
        WcTool.main(new String[]{"-m", filePath});

        Assertions.assertEquals("339292 test.txt\n", getOutput());
    }
}