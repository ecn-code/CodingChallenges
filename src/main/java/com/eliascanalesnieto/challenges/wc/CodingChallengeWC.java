package com.eliascanalesnieto.challenges.wc;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CodingChallengeWC {
    public static void main(final String[] args) {
        final List<String> options;
        final List<String> fileNames;
        if (invalidArgs(args)) {
            notifyError();
            return;
        }

        if (args.length == 1) {
            options = List.of("-c", "-l", "-w");
            fileNames = Arrays.stream(args).toList();
        } else {
            Map<Boolean, List<String>> argsAndFiles = Arrays.stream(args)
                    .collect(Collectors.partitioningBy(command -> command.startsWith("-")));
            options = argsAndFiles.get(true);
            fileNames = argsAndFiles.get(false);
        }

        processOptions(options, fileNames);
    }

    private static boolean invalidArgs(final String[] args) {
        if (args == null || args.length == 0 || Arrays.stream(args).anyMatch(arg -> arg == null || arg.isBlank())) {
            return true;
        }

        if(isThereACommandAfterAFile(args)) {
            return true;
        }

        return false;
    }

    private static boolean isThereACommandAfterAFile(String[] args) {
        boolean isFile = false;
        for (String arg : args) {
            if(arg.startsWith("-") && isFile) {
                return true;
            } else {
                isFile = true;
            }
        }
        return false;
    }

    private static void processOptions(final List<String> options, final List<String> fileNames) {
        final String optionsProcessed = fileNames.stream()
                .map(CodingChallengeWC.processOption(options))
                .collect(Collectors.joining("\n"));
        System.out.println(optionsProcessed);
    }

    private static Function<String, String> processOption(final List<String> options) {
        return fileName -> {
            final Path path = Path.of(fileName);
            return STR."\{options.stream()
                    .map(option -> processOption(option, path))
                    .collect(Collectors.joining(" "))} \{path.getFileName()}";
        };
    }

    private static String processOption(final String option, final Path path) {
        return switch (option) {
            case "-c" -> optionC(path);
            case "-l" -> optionL(path);
            case "-w" -> optionW(path);
            case "-m" -> optionM(path);
            default -> notifyError();
        };
    }

    private static String optionM(final Path path) {
        try (var reader = Files.newBufferedReader(path)) {
            long charactersCount = 0;
            while (reader.read() != -1) {
                charactersCount++;
            }
            return STR."\{charactersCount}";

        } catch (IOException e) {
            return "File not found";
        }
    }

    private static String optionW(final Path path) {
        try (var lines = Files.lines(path)) {
            final long wordsCount = lines.flatMap(line -> Stream.of(line.split("\\s+")))
                    .filter(Predicate.not(String::isEmpty))
                    .count();
            return STR."\{wordsCount}";
        } catch (IOException e) {
            return "File not found";
        }
    }

    private static String optionL(final Path path) {
        try (var lines = Files.lines(path)) {
            long linesCount = lines.count();
            return STR."\{linesCount}";
        } catch (IOException e) {
            return "File not found";
        }
    }

    private static String optionC(final Path path) {
        try {
            final long fileSize = Files.size(path);
            return STR."\{fileSize}";
        } catch (IOException e) {
            return "File not found";
        }
    }

    private static String notifyError() {
        return """
                Command ccwc have the next options:
                    -c: to read the bytes of the file
                """;
    }
}