package com.gestao.biblioteca.exceptions;

import com.brestrai.utils.commons.domain.response.ErrorResponse;
import com.brestrai.utils.commons.exception.AbstractException;

public class LivroException extends AbstractException {
    public LivroException(String msg) {

        super(msg);
    }

    public LivroException(String msg, ErrorResponse error) {

        super(msg, error);
    }
}
