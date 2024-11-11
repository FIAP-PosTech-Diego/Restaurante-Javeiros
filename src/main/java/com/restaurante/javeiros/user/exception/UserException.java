package com.restaurante.javeiros.user.exception;

import com.restaurante.javeiros.exception.CustomException;
import com.restaurante.javeiros.exception.HttpStatusProject;

public class UserException extends CustomException {

    public UserException(String msg, HttpStatusProject status) {
        super(msg, status);
    }

}
