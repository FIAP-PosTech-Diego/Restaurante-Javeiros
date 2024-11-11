package com.restaurante.javeiros.exception;

public class CustomException extends RuntimeException{

    private final HttpStatusProject customError;

    public CustomException(HttpStatusProject customError) {
        super(customError.description);
        this.customError = customError;
    }

    public CustomException(String message, HttpStatusProject customError) {
        super(message);
        this.customError = customError;
    }

    public CustomException(Throwable cause, HttpStatusProject customError) {
        super(cause.getMessage(), cause);
        this.customError = customError;
    }

    public CustomException(String message, Throwable cause, HttpStatusProject customError) {
        super(message, cause);
        this.customError = customError;
    }

    public HttpStatusProject getCustomError() {
        return customError;
    }

}
