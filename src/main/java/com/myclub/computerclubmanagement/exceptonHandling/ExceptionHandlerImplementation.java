package com.myclub.computerclubmanagement.exceptonHandling;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class ExceptionHandlerImplementation {

    @ExceptionHandler({IllegalStateException.class})
    public ResponseEntity<Object> illegalStateExHandler(
            Exception ex
    ) {
        return ResponseEntity.badRequest().
                body(Map.of("message" , ex.getMessage()));
    }
}
