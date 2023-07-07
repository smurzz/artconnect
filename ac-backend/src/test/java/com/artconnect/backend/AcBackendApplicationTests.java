package com.artconnect.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;

import com.artconnect.backend.config.jwt.JwtService;


@WebFluxTest(AcBackendApplication.class)
@Import(JwtService.class)
class AcBackendApplicationTests {

    @Test
    void contextLoads() {
    }

}