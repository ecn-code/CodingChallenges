package com.eliascanalesnieto.challenges.webserver;

import com.eliascanalesnieto.challenges.webserver.exception.NotFoundException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Router {

    private final Path home;

    public Router(final String home) {
        this.home = Path.of(getClass().getClassLoader().getResource(home).getPath());
    }

    public Path process(final String filePath) throws NotFoundException, IOException {
        if(filePath == null) {
            throw new NotFoundException();
        }

        if(filePath.isBlank() || "/".equals(filePath)) {
            return Path.of(STR."\{this.home}/index.html");
        }

        final Path path = Path.of(this.home.toAbsolutePath().toString(), filePath).normalize();
        if(!path.startsWith(this.home) || Files.isDirectory(path) || !path.toFile().exists()
                || !"text/html".equals(Files.probeContentType(path))) {
            throw new NotFoundException();
        }

        return path;
    }

}
