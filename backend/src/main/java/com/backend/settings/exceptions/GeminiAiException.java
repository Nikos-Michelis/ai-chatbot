package com.backend.settings.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class GeminiAiException extends RuntimeException {
    private HttpStatusCode httpStatusCode;
    public GeminiAiException (String message, HttpStatusCode httpStatusCode) {
        super(message);
        this.httpStatusCode = httpStatusCode;
    }
    public GeminiAiException (String message) {
        super(message);
    }
}
