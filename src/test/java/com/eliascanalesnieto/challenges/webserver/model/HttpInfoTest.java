package com.eliascanalesnieto.challenges.webserver.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class HttpInfoTest {

    @Test
    void givenEmptyInputThenException() {
        Assertions.assertThrowsExactly(IOException.class, () -> new HttpInfo(new ByteArrayInputStream("".getBytes())));
    }

    @Test
    void givenNonHttpMessageThenException() {
        Assertions.assertThrowsExactly(IOException.class, () -> new HttpInfo(new ByteArrayInputStream("non http".getBytes())));
    }

    @Test
    void givenHttpMessageThenAttributesFilled() throws IOException {
        final String httpMessage = "GET /hi HTTP/1.1\r\n" +
                "Host: localhost:1000\r\n" +
                "Connection: keep-alive\r\n" +
                "Cache-Control: max-age=0\r\n" +
                "sec-ch-ua: \"Chromium\";v=\"128\", \"Not;A=Brand\";v=\"24\", \"Google Chrome\";v=\"128\"\r\n" +
                "sec-ch-ua-mobile: ?0\r\n" +
                "sec-ch-ua-platform: \"macOS\"\r\n" +
                "Upgrade-Insecure-Requests: 1\r\n" +
                "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/128.0.0.0 Safari/537.36\r\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7\r\n" +
                "Sec-Fetch-Site: none\r\n" +
                "Sec-Fetch-Mode: navigate\r\n" +
                "Sec-Fetch-User: ?1\r\n" +
                "Sec-Fetch-Dest: document\r\n" +
                "Accept-Encoding: gzip, deflate, br, zstd\r\n" +
                "Accept-Language: es,en;q=0.9,uk;q=0.8,bg;q=0.7\r\n" +
                "Cookie: connect.sid=s%3AbBU2qnyWG-FIn2_NOpjJ9PQEbZKIa2sZ.Q5UqbA6o9GgxwfJ%2FoHNXR5H8VzjWzwwq3bRIppd0zYQ\r\n";
        final HttpInfo httpInfo = new HttpInfo(new ByteArrayInputStream(httpMessage.getBytes()));
        Assertions.assertEquals("/hi", httpInfo.path());
        Assertions.assertEquals("GET", httpInfo.method());
    }

}
