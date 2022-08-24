package com.ffmoyano.urlshortener;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class UrlShortenerApplicationTests {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();



    @Test
    void contextLoads() {
        System.out.println(encoder.encode("fernando"));
    }

}
