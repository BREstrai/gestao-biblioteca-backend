package com.gestao.biblioteca.exceptions;

import com.brestrai.utils.commons.domain.response.ErrorResponse;
import com.brestrai.utils.commons.exception.AbstractException;

public class DevolucaoException extends AbstractException {

    public DevolucaoException(String msg) {

        super(msg);
    }

    public DevolucaoException(String msg, ErrorResponse error) {

        super(msg, error);
    }
}
