package com.gestao.biblioteca.exceptions;

import com.brestrai.utils.commons.domain.response.ErrorResponse;
import com.brestrai.utils.commons.exception.AbstractException;

public class UsuarioException extends AbstractException {
    public UsuarioException(String msg) {

        super(msg);
    }

    public UsuarioException(String msg, ErrorResponse error) {

        super(msg, error);
    }
}
