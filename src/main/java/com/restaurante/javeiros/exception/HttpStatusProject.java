package com.restaurante.javeiros.exception;

import org.springframework.http.HttpStatus;

public enum HttpStatusProject {

    VALIDATION(460,HttpStatus.Series.CLIENT_ERROR, "Erro de validacao"),
    RESOURCE_NOT_FOUND(461, HttpStatus.Series.CLIENT_ERROR, "Recurso não encontrado"),
    FORBIDDEN(462, HttpStatus.Series.CLIENT_ERROR, "não permitido"),

    DATABASE(560, HttpStatus.Series.SERVER_ERROR, "Erro envolvendo banco mínimo"),
    LOGIC_ERROR(561, HttpStatus.Series.SERVER_ERROR, "Erro logico"),
    NOT_MODIFIED(304, HttpStatus.Series.REDIRECTION, "Entidade não modificada");

    public final int statuscode;
    public final HttpStatus.Series clientError;
    public final String description;

    HttpStatusProject(int statuscode, HttpStatus.Series clientError, String description) {
        this.statuscode = statuscode;
        this.clientError = clientError;
        this.description = description;
    }
}
