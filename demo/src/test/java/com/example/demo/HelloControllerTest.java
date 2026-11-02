package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HelloControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testHelloAPI() {

        String result = restTemplate.getForObject(
            "http://localhost:" + port + "/hello",
            String.class
        );

        assertEquals("Hello from Docker!", result);
    }

    @Test
    void testStatus() {

        var response = restTemplate.getForEntity(
            "http://localhost:" + port + "/hello",
            String.class
        );

        assertEquals(200, response.getStatusCode().value());
    }
}