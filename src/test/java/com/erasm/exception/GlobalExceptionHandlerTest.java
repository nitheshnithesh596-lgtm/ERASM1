package com.erasm.exception;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler =
            new GlobalExceptionHandler();

    @Test
    void testHandleResourceNotFoundException() {

        ResourceNotFoundException ex =
                new ResourceNotFoundException("Employee not found");

        ResponseEntity<ErrorResponse> response =
                handler.handleNotFound(ex);

        assertEquals(HttpStatus.NOT_FOUND,
                response.getStatusCode());

        assertNotNull(response.getBody());

        assertEquals(404,
                response.getBody().getStatus());

        assertEquals("NOT_FOUND",
                response.getBody().getError());

        assertEquals("Employee not found",
                response.getBody().getMessage());

        assertNotNull(response.getBody().getTimestamp());
    }

    @Test
    void testHandleGenericException() {

        Exception ex =
                new Exception("Something went wrong");

        ResponseEntity<ErrorResponse> response =
                handler.handleGeneric(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,
                response.getStatusCode());

        assertNotNull(response.getBody());

        assertEquals(500,
                response.getBody().getStatus());

        assertEquals("INTERNAL_ERROR",
                response.getBody().getError());

        assertEquals("Something went wrong",
                response.getBody().getMessage());

        assertNotNull(response.getBody().getTimestamp());
    }
}