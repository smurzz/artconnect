package com.artconnect.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

// @SpringBootTest
@WebFluxTest(AcBackendApplication.class)
class AcBackendApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Autowired
    private WebTestClient webClient;

    @Test
    public void testGetHelloWorld() {
        webClient
        	.get()
        	.uri("/")
            .accept(MediaType.TEXT_PLAIN)
            .exchange()
            .expectStatus().isOk()
            .expectBody(String.class)
            .isEqualTo("Hello World!");
    }

}
