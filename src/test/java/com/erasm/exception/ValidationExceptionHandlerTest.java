package com.erasm.exception;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

class ValidationExceptionHandlerTest {

    private ValidationExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new ValidationExceptionHandler();
    }

    @Test
    void testHandleValidationException() throws Exception {

        Object target = new Object();

        BeanPropertyBindingResult bindingResult =
                new BeanPropertyBindingResult(target, "request");

        bindingResult.addError(
                new FieldError(
                        "request",
                        "email",
                        "Email is required"));

        bindingResult.addError(
                new FieldError(
                        "request",
                        "password",
                        "Password is required"));

        Method method = ValidationExceptionHandlerTest.class
                .getDeclaredMethod("dummyMethod", String.class);

        MethodParameter parameter =
                new MethodParameter(method, 0);

        MethodArgumentNotValidException exception =
                new MethodArgumentNotValidException(
                        parameter,
                        bindingResult);

        ResponseEntity<Map<String, Object>> response =
                handler.handleValidationException(exception);

        assertEquals(
                HttpStatus.BAD_REQUEST,
                response.getStatusCode());

        assertNotNull(response.getBody());

        assertEquals(
                400,
                response.getBody().get("status"));

        assertEquals(
                "Validation Failed",
                response.getBody().get("message"));

        @SuppressWarnings("unchecked")
        Map<String, String> errors =
                (Map<String, String>) response.getBody().get("errors");

        assertEquals(
                "Email is required",
                errors.get("email"));

        assertEquals(
                "Password is required",
                errors.get("password"));

        assertNotNull(response.getBody().get("timestamp"));
    }

    private void dummyMethod(String value) {
        // Used only for creating MethodParameter
    }
}