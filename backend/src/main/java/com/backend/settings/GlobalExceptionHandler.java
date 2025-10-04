package com.backend.settings;

import com.backend.settings.exceptions.GeminiAiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import static com.backend.settings.BusinessErrorCodes.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GeminiAiException.class)
    public ResponseEntity<ExceptionResponse> handleException(GeminiAiException exp) {
        return ResponseEntity
                .status(exp.getHttpStatusCode())
                .body(ExceptionResponse.builder()
                        .businessErrorCode(GEMINI_API_ERROR.getCode())
                        .businessErrorDescription(exp.getMessage())
                        .error(exp.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<ExceptionResponse> handleWebClientResponseException(WebClientResponseException exp) {
        return ResponseEntity
                .status(exp.getStatusCode())
                .body(ExceptionResponse.builder()
                        .businessErrorCode(ERROR_REMOTE_API.getCode())
                        .businessErrorDescription(ERROR_REMOTE_API.getDescription())
                        .error(exp.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(WebClientRequestException.class)
    public ResponseEntity<ExceptionResponse> handleWebClientRequestException(WebClientRequestException exp) {
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(ExceptionResponse.builder()
                        .businessErrorCode(UNAVAILABLE_REMOTE_API.getCode())
                        .businessErrorDescription(UNAVAILABLE_REMOTE_API.getDescription())
                        .error(exp.getMessage())
                        .build()
                );
    }


    /*@ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Set<Map<String, String>>> handleConstraintViolationException(ConstraintViolationException ex) {
        Set<Map<String, String>> errors = new HashSet<>();

        ex.getConstraintViolations().forEach(violation -> {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            errors.add(Map.of(fieldName, errorMessage));
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }*/
}
