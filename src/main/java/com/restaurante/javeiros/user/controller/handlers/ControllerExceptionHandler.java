package com.restaurante.javeiros.user.controller.handlers;

import com.restaurante.javeiros.user.dto.ExceptionDTO;
import com.restaurante.javeiros.user.exception.LoginException;
import com.restaurante.javeiros.user.exception.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ExceptionDTO> handlerUserNotFoundException(UserException e){
        var status = HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status.value()).body(new ExceptionDTO(e.getMessage(), status.value()));
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<ExceptionDTO> handlerLoginException(LoginException e){
        var status = HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status.value()).body(new ExceptionDTO(e.getMessage(), status.value()));
    }
}
