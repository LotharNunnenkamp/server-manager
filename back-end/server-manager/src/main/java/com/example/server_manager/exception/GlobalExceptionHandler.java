package com.example.server_manager.exception;

import com.example.server_manager.model.Response;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IOException.class)
    public ResponseEntity<Response> handleIOException(IOException e) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(
                        Response.builder()
                                .timeStamp(LocalDateTime.now())
                                .statusCode(HttpStatus.BAD_GATEWAY.value())
                                .httpStatus(HttpStatus.BAD_GATEWAY)
                                .reason("Reason/cause: " + e.getCause())
                                .message("Failed to process request: " + e.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(URISyntaxException.class)
    public ResponseEntity<Response> handleURISyntaxException(URISyntaxException e){
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(
                        Response.builder()
                                .timeStamp(LocalDateTime.now())
                                .statusCode(HttpStatus.BAD_GATEWAY.value())
                                .httpStatus(HttpStatus.BAD_GATEWAY)
                                .reason("Reason/cause: " + e.getCause())
                                .message("Failed to process request: " + e.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<Response> handleFileNotFoundException(FileNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        Response.builder()
                                .timeStamp(LocalDateTime.now())
                                .statusCode(HttpStatus.NOT_FOUND.value())
                                .httpStatus(HttpStatus.NOT_FOUND)
                                .reason("Reason/cause: " + e.getCause())
                                .message("Failed to process request: " + e.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Response> handleEntityNotFoundException(EntityNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        Response.builder()
                                .timeStamp(LocalDateTime.now())
                                .statusCode(HttpStatus.NOT_FOUND.value())
                                .httpStatus(HttpStatus.NOT_FOUND)
                                .reason("Reason/cause: " + e.getCause())
                                .message("Failed to process request: " + e.getMessage())
                                .build()
                );
    }
}
