package com.eliascanalesnieto.challenges;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.nio.file.Paths;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SystemOutTool {

    private final PrintStream originalOut;
    private final ByteArrayOutputStream byteArrayOutputStream;

    public SystemOutTool() {
        byteArrayOutputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(byteArrayOutputStream));
    }

    @BeforeEach
    protected void beforeEach() {
        reset();
    }

    @AfterAll
    void afterAll() throws IOException {
        close();
    }

    private void reset() {
        byteArrayOutputStream.reset();
    }

    private void close() throws IOException {
        System.setOut(originalOut);
        byteArrayOutputStream.close();
    }

    protected String getOutput() {
        return byteArrayOutputStream.toString();
    }

    public static String getFilePath(final Class clazz, final String fileRelativePath) throws URISyntaxException {
        return Paths.get(clazz.getClassLoader().getResource(fileRelativePath).toURI())
                .toString();
    }
}
