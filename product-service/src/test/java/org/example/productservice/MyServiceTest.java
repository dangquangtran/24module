package org.example.productservice;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
public class MyServiceTest {

    @Autowired
    private RestTemplate restTemplate;

    private ClientAndServer mockServer;

    @BeforeEach
    void setup() {
        mockServer = startClientAndServer(8089); // MockServer chạy cổng 8089
    }

    @AfterEach
    void teardown() {
        if (mockServer != null) {
            mockServer.stop();
        }
    }

    @Test
    public void testSuccessfulResponse() {
        // Tạo mock cho một endpoint /api/success trả về trạng thái 200
        new MockServerClient("localhost", 8089)
                .when(request().withMethod("GET").withPath("/api/success"))
                .respond(response().withStatusCode(200).withBody("{ \"message\": \"Success\" }"));

        String response = restTemplate.getForObject("http://localhost:8089/api/success", String.class);
        assertEquals("{ \"message\": \"Success\" }", response);
    }

    @Test
    public void testNotFoundResponse() {
        // Tạo mock cho một endpoint /api/notfound trả về trạng thái 404
        new MockServerClient("localhost", 8089)
                .when(request().withMethod("GET").withPath("/api/notfound"))
                .respond(response().withStatusCode(404).withBody("{ \"error\": \"Not Found\" }"));

        try {
            restTemplate.getForObject("http://localhost:8089/api/notfound", String.class);
            fail("Should have thrown HttpClientErrorException");
        } catch (HttpClientErrorException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
        }
    }

    @Test
    public void testServerErrorResponse() {
        // Tạo mock cho một endpoint /api/servererror trả về trạng thái 500
        new MockServerClient("localhost", 8089)
                .when(request().withMethod("GET").withPath("/api/servererror"))
                .respond(response().withStatusCode(500).withBody("{ \"error\": \"Internal Server Error\" }"));

        try {
            restTemplate.getForObject("http://localhost:8089/api/servererror", String.class);
            fail("Should have thrown HttpServerErrorException");
        } catch (HttpServerErrorException e) {
            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, e.getStatusCode());
        }
    }
}
