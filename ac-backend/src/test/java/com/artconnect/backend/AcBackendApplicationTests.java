package com.artconnect.backend;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

//NEED TO BE CORRECTED

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AcBackendApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Test
    void contextLoads() {
        // Test if the application context loads successfully
        // Add additional assertions to verify the behavior of your application's context
        Assertions.assertNotNull(restTemplate);
        // Add more assertions as needed
    }

    @Test
    void mainMethodStartsApplication() {
        AcBackendApplication.main(new String[]{});
        // You can add additional assertions here to verify the behavior of your application's main method
    }

//    @Test
//    void getHelloEndpointReturnsHelloMessage() {
//        HttpHeaders headers = new HttpHeaders();
//        String credentials = "valid_username:valid_password";
//        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
//        headers.add("Authorization", "Basic " + encodedCredentials);
//
//        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
//        ResponseEntity<String> response = restTemplate.exchange(
//                "http://localhost:" + port + "/",
//                HttpMethod.GET,
//                requestEntity,
//                String.class
//        );
//
//        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode(), "Unexpected status code");
//        Assertions.assertEquals("Hello, World!", response.getBody(), "Unexpected response body");
//    }

    // Add more test methods to cover other functionality of the class

    private String getBase64Credentials(String username, String password) {
        String credentials = username + ":" + password;
        byte[] credentialsBytes = credentials.getBytes();
        byte[] base64CredentialsBytes = Base64.getEncoder().encode(credentialsBytes);
        return new String(base64CredentialsBytes, StandardCharsets.UTF_8);
    }
}




/*import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.artconnect.backend.config.jwt.JwtService;

// @SpringBootTest
@WebFluxTest(AcBackendApplication.class)
@Import(JwtService.class)
class AcBackendApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Autowired
    private WebTestClient webClient;

    @Test
    @WithMockUser
    public void testGetHelloWorld() {
        webClient
        	.get()
        	.uri("/")
            .accept(MediaType.TEXT_PLAIN)
            .exchange()
            .expectStatus().isOk()
            .expectBody(String.class)
            .isEqualTo("Hello from secured ArtConnect!");
    }

}*/
